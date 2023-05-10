package com.api.voterz.data.dtos.requests;

import com.api.voterz.data.models.Constituency;
import com.api.voterz.data.models.LG;
import com.api.voterz.data.models.State;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoterRegisterRequest {
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String email;
    private String password;
    private LG lg;
    private Constituency constituency;
    private State state;
}
