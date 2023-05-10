package com.api.voterz.services.result_services;

import com.api.voterz.data.dtos.responses.CollationResponse;
import com.api.voterz.data.models.Candidate;
import com.api.voterz.services.candidate_services.CandidateService;
import com.api.voterz.services.vote_services.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ResultServiceImpl implements ResultService {
    private final VoteService voteService;
    private final CandidateService candidateService;


    @Override
    public CollationResponse collateResult() {
        List<Candidate> allCandidates = candidateService.getCandidates();
        allCandidates.forEach(candidate -> {
            candidate.setNumberOfVotes(
                    voteService.numberOfVotesByCandidateId(
                            candidate.getId()));
        });

        return CollationResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Result collated successfully")
                .collated(true)
                .build();
    }
    @Override
    public Map<String, Long> result() {
        return candidateService.getCandidates().stream()
                .collect(Collectors.toMap(
                        candidate -> candidate.getDetails().getLastName(),
                        Candidate::getNumberOfVotes
                ));
    }
}