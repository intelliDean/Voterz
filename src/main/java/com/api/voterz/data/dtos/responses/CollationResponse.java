package com.api.voterz.data.dtos.responses;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CollationResponse {
    private int statusCode;
    private String message;
    private boolean collated;
}
