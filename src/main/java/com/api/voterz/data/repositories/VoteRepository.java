package com.api.voterz.data.repositories;

import com.api.voterz.data.models.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<List<Vote>> findAllByCandidate_Id(Long candidateId);
    Optional<Vote> findVoteByVoter_VoterId(Long voterId);
    Long countAllByCandidate_Id(Long candidateId);
}
