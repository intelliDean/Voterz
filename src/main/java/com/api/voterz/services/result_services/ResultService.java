package com.api.voterz.services.result_services;

import com.api.voterz.data.dtos.responses.CollationResponse;
import com.api.voterz.data.models.Candidate;
import com.api.voterz.data.models.Vote;

import java.util.List;
import java.util.Map;

public interface ResultService {

    CollationResponse collateResult();
    Map<String, Long> result();
}
