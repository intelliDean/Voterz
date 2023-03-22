package com.api.voterz.services.vote_services;

import com.api.voterz.data.dtos.requests.VoteRequest;
import com.api.voterz.data.dtos.responses.CastedVoteResponse;
import com.api.voterz.data.models.Vote;

import java.util.List;

public interface VoteService {
    CastedVoteResponse castVote(VoteRequest voteRequest);
    Vote getVoteById(Long voteId);
    Vote getVoteByVoterId(Long voterId);
    List<Vote> getVotesByCandidateId(Long candidateId);
    Long numberOfVotesByCandidateId(Long candidateId);
    Long numberOfAllVotes();

}
