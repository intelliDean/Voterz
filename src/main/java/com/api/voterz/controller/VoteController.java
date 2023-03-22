package com.api.voterz.controller;

import com.api.voterz.data.dtos.requests.VoteRequest;
import com.api.voterz.data.dtos.responses.CastedVoteResponse;
import com.api.voterz.data.models.Vote;
import com.api.voterz.services.vote_services.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/vote")
public class VoteController {
    private final VoteService voteService;

    @PostMapping("castVote")
    public ResponseEntity<?> castVote(@RequestBody VoteRequest voteRequest) {
        CastedVoteResponse response = voteService.castVote(voteRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("{voteId}")
    public ResponseEntity<?> getVoterById(@PathVariable Long voteId) {
        Vote vote = voteService.getVoteById(voteId);
        return ResponseEntity.ok(vote);
    }

    @GetMapping("/result/{candidateId}")
    public ResponseEntity<?> getCandidateVotes(@PathVariable Long candidateId) {
        List<Vote> candidateVotes = voteService.getVotesByCandidateId(candidateId);
        return ResponseEntity.ok(candidateVotes);
    }

    @GetMapping("/voter/{voterId}")
    public ResponseEntity<?> getVoteByVoterId(@PathVariable Long voterId) {
        Vote vote = voteService.getVoteByVoterId(voterId);
        return ResponseEntity.ok(vote);
    }

    @GetMapping("/numberOfVotes/{candidateId}")
    public ResponseEntity<?> getNumberOfCandidateVotes(@PathVariable Long candidateId) {
        Long numberOfVotes = voteService.numberOfVotesByCandidateId(candidateId);
        return ResponseEntity.ok(numberOfVotes);
    }
}
