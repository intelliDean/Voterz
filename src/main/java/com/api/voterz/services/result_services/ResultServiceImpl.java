package com.api.voterz.services.result_services;

import com.api.voterz.data.dtos.responses.CollationResponse;
import com.api.voterz.data.models.Candidate;
import com.api.voterz.services.candidate_services.CandidateService;
import com.api.voterz.services.vote_services.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ResultServiceImpl implements ResultService {
    private final VoteService voteService;
    private final CandidateService candidateService;


    @Override
    public CollationResponse collateResult () {
        List<Candidate> allCandidates = candidateService.getCandidates();
        List<Long> candidateId = new ArrayList<>();
        for (Candidate allCandidate : allCandidates) {
            candidateId.add(allCandidate.getId());
        }

        for (int i = 0; i < candidateService.numberOfCandidates(); i++) {
            candidateService.getCandidate(candidateId.get(i))
                    .setNumberOfVotes(voteService
                            .numberOfVotesByCandidateId(
                                    candidateId.get(i)));

        }
        return CollationResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Result collated successfully")
                .collated(true)
                .build();
    }

    @Override
    public Map<String, Long> result() {
        List<Candidate> allCandidates = candidateService.getCandidates();

        Map<String, Long> electionResult = new HashMap<>();
        for (int i = 0; i < candidateService.numberOfCandidates(); i++) {
            electionResult.put(allCandidates.get(i).getLastName(),
                    allCandidates.get(i).getNumberOfVotes());
        }
        return electionResult;
    }
}
