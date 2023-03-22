package com.api.voterz.data.dtos.requests;

import com.api.voterz.data.models.ElectionType;
import com.api.voterz.data.models.Party;
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
    @NotNull(message = "First Name cannot be null")
    @NotEmpty(message = "First Name cannot be empty")
    @NotBlank(message = "First Name cannot be blank")
    @Size(min = 3, max = 255)
    private String firstName;
    @NotNull(message = "Last Name cannot be null")
    @NotEmpty(message = "Last Name cannot be empty")
    @NotBlank(message = "Last Name cannot be blank")
    @Size(min = 3, max = 255)
    private String lastName;
    @NotNull(message = "Party cannot be null")
    @NotEmpty(message = "Party cannot be empty")
    @NotBlank(message = "Party cannot be blank")
    private Party party;
    @Positive
    private int age;
    @NotEmpty(message = "Election cannot be empty")
    @NotNull(message = "Election cannot be nul")
    private ElectionType electionType;
    @NotNull(message = "Image uploading is very important to complete registration")
    MultipartFile candidateImage;
}
