package com.api.voterz.data.repositories;

import com.api.voterz.data.models.Details;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetailsRepository extends JpaRepository<Details, Long> {
}
