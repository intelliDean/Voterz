package com.api.voterz.data.dtos.requests;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoterRegisterRequest {
    private String name;
    private int age;
}
