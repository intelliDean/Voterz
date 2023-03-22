package com.api.voterz.data.dtos.responses;

import com.api.voterz.data.models.ElectionType;
import com.api.voterz.data.models.Party;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CandidateResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private Party party;
    private int age;
    private String candidateImage;
    private boolean registered;
    private ElectionType ElectionType;
}
