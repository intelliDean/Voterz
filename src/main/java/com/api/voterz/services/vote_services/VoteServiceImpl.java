package com.api.voterz.services.vote_services;

import com.api.voterz.data.dtos.requests.VoteRequest;
import com.api.voterz.data.dtos.responses.CastedVoteResponse;
import com.api.voterz.data.models.Candidate;
import com.api.voterz.data.models.Vote;
import com.api.voterz.data.models.Voter;
import com.api.voterz.data.repositories.VoteRepository;
import com.api.voterz.exceptions.VoterzException;
import com.api.voterz.services.candidate_services.CandidateService;
import com.api.voterz.services.voter_services.VoterService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class VoteServiceImpl implements VoteService {
    private final VoteRepository voteRepository;
    private final VoterService voterService;
    private final CandidateService candidateService;


    @Override
    public CastedVoteResponse castVote(VoteRequest voteRequest) {
        Candidate candidate = candidateService.getCandidate(voteRequest.getCandidateId());
        Voter voter = voterService.getVoterById(voteRequest.getVoterId());

        //validation
        if (!candidate.getDetails().getRegistered()) {
            throw new VoterzException(String.format("Candidate with id %d is not qualified", candidate.getId()));
        }
        if (!voter.getDetails().getRegistered()) {
            throw new VoterzException("You are not qualified to vote");
        }
        if (voter.getVoted()) throw new VoterzException("You have voted already");

        //vote casting
        Vote vote = Vote.builder()
                .candidate(candidate)
                .voter(voter)
                .timeCasted(LocalDateTime.now().toString())
                .casted(true)
                .type(voteRequest.getElectionType())
                .build();

        voter.setVoted(true);

        //save vote
        Vote castedVoted = voteRepository.save(vote);
        voterService.saveVoter(voter);
        return CastedVoteResponse.builder()
                .id(castedVoted.getId())
                .message(String.format("You have casted your voted for candidate %s successfully", candidate.getDetails().getFirstName()+ " " +candidate.getDetails().getLastName()))
                .successful(true)
                .statusCode(HttpStatus.CREATED.value())
                .build();
    }

    @Override
    public Vote getVoteById(Long voteId) {
        return voteRepository.findById(voteId)
                .orElseThrow(()-> new VoterzException("Vote could not be found"));
    }

    @Override
    public Vote getVoteByVoterId(Long voterId) {
        return voteRepository.findVoteByVoter_Id(voterId)
                .orElseThrow(()-> new VoterzException("Vote could not be found"));
    }

    @Override
    public List<Vote> getVotesByCandidateId(Long candidateId) {
        return voteRepository.findAllByCandidate_Id(candidateId)
                .orElseThrow(()-> new VoterzException("votes could not be found"));
    }

    @Override
    public Long numberOfVotesByCandidateId(Long candidateId) {
       return voteRepository.countAllByCandidate_Id(candidateId);
    }

    @Override
    public Long numberOfAllVotes() {
        return voteRepository.count();
    }
}
