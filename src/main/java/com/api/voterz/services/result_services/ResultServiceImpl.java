package com.api.voterz.services.result_services;

import com.api.voterz.data.dtos.responses.CollationResponse;
import com.api.voterz.data.models.Candidate;
import com.api.voterz.services.candidate_services.CandidateService;
import com.api.voterz.services.vote_services.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
public class ResultServiceImpl implements ResultService {
    private final VoteService voteService;
    private final CandidateService candidateService;


    @Override
    public CollationResponse collateResult() {
        List<Candidate> allCandidates = candidateService.getCandidates();
        List<Long> candidateId = new ArrayList<>();
        allCandidates.forEach(candidate -> candidateId.add(candidate.getId()));
        IntStream.range(0, candidateService.numberOfCandidates().intValue()).forEach(i -> {
            Long id = candidateId.get(i);
            Candidate candidate = candidateService.getCandidate(id);
            candidate.setNumberOfVotes(voteService.numberOfVotesByCandidateId(id));
        });
        return CollationResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Result collated successfully")
                .collated(true)
                .build();
    }

    @Override
    public Map<String, Long> result() {
        List<Candidate> allCandidates = candidateService.getCandidates();
        return IntStream.range(0, candidateService.numberOfCandidates().intValue())
                .boxed()
                .collect(Collectors.toMap(
                        i -> allCandidates.get(i).getDetails().getLastName(),
                        i -> allCandidates.get(i).getNumberOfVotes()
                ));
    }
}
