package com.api.voterz.services.vote_services;

import com.api.voterz.data.dtos.requests.VoteRequest;
import com.api.voterz.data.dtos.responses.CastedVoteResponse;
import com.api.voterz.data.models.Candidate;
import com.api.voterz.data.models.Details;
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
        // Validation
        validateCandidate(candidate);
        validateVoter(voter);
        validateVoteStatus(voter);
        // Make the vote
        Details candidateDetails = makeVote(voteRequest, candidate, voter);
        String fullName = String.format("%s %s", candidateDetails.getFirstName(), candidateDetails.getLastName());

        return CastedVoteResponse.builder()
                .message(String.format("You have casted your vote for candidate %s successfully", fullName))
                .successful(true)
                .statusCode(HttpStatus.CREATED.value())
                .build();
    }

    private void validateCandidate(Candidate candidate) {
        if (!candidate.getDetails().getRegistered()) {
            throw new VoterzException(String.format("Candidate with id %d is not qualified", candidate.getId()));
        }
    }

    private void validateVoter(Voter voter) {
        if (!voter.getDetails().getRegistered()) {
            throw new VoterzException("You are not qualified to vote");
        }
    }

    private void validateVoteStatus(Voter voter) {
        if (voter.getVoted()) {
            throw new VoterzException("You have voted already");
        }
    }

    private Details makeVote(VoteRequest voteRequest, Candidate candidate, Voter voter) {
        Details voterDetails = voter.getDetails();
        Details candidateDetails = candidate.getDetails();

        boolean eligible = false;
        switch (candidate.getElectionType()) {
            case PRESIDENT -> voteCandidate(voteRequest, candidate, voter);
            case GOVERNOR -> eligible = voterDetails.getState().equals(candidateDetails.getState());
            case SENATOR, NATIONAL_LAWMAKER, STATE_LAWMAKER ->
                    eligible = voterDetails.getConstituency().equals(candidateDetails.getConstituency());
            case LG_CHAIRMAN, COUNCILOR -> eligible = voterDetails.getLg().equals(candidateDetails.getLg());
        }

        if (eligible) {
            voteCandidate(voteRequest, candidate, voter);
        }
        return candidateDetails;
    }
    private void voteCandidate(VoteRequest voteRequest, Candidate candidate, Voter voter) {
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
        voteRepository.save(vote);
        voterService.saveVoter(voter);
    }

    @Override
    public Vote getVoteById(Long voteId) {
        return voteRepository.findById(voteId)
                .orElseThrow(() -> new VoterzException("Vote could not be found"));
    }

    @Override
    public Vote getVoteByVoterId(Long voterId) {
        return voteRepository.findVoteByVoter_Id(voterId)
                .orElseThrow(() -> new VoterzException("Vote could not be found"));
    }

    @Override
    public List<Vote> getVotesByCandidateId(Long candidateId) {
        return voteRepository.findAllByCandidate_Id(candidateId)
                .orElseThrow(() -> new VoterzException("votes could not be found"));
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
