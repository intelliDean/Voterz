package com.api.voterz.data.dtos.requests;

import com.api.voterz.data.models.ElectionType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoteRequest {
    private Long voterId;
    private Long candidateId;
    private ElectionType electionType;
}
