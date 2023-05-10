package com.api.voterz.data.dtos.requests;

import com.api.voterz.data.models.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@NotNull
public class CandidateRegisterRequest {
    private String firstName;
    private String lastName;
    private Party party;
    private String email;
    private String password;
    private String dateOfBirth;
    private ElectionType electionType;
    private LG lg;
    private Constituency constituency;
    private State state;
    private MultipartFile image;
}
