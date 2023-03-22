package com.api.voterz.data.dtos.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateResponse {
    private String message;
    private String imageUrl;
    private String updateTime;
}
