package com.api.voterz.data.dtos.responses;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CastedVoteResponse {
    private Long id;
    private String message;
    private int statusCode;
    private boolean successful;
}
