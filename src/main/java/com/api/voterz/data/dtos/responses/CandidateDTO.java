package com.api.voterz.data.dtos.responses;

import com.api.voterz.data.models.Details;
import com.api.voterz.data.models.ElectionType;
import com.api.voterz.data.models.Party;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CandidateDTO {
    @JsonUnwrapped
    private Details details;
    private Party party;
    private ElectionType ElectionType;
}
