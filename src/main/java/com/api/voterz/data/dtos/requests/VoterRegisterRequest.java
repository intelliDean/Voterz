package com.api.voterz.data.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoterRegisterRequest {
    @NotEmpty
    @NotNull
    @NotBlank
    private String firstName;
    @NotEmpty
    @NotNull
    @NotBlank
    private String lastName;
    private MultipartFile image;

    @Positive
    @NotEmpty
    @NotNull
    @NotBlank
    private String dateOfBirth;
    private String email;
    private String password;
}
