package com.api.voterz.data.repositories;

import com.api.voterz.data.models.Voter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoterRepository extends JpaRepository<Voter, Long> {

}
