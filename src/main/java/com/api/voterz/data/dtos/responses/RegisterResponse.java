package com.api.voterz.data.dtos.responses;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterResponse {
    private Long id;
    private String message;
    private int statusCode;
    private boolean registered;
}
