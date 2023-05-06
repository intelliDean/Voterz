package com.api.voterz.data.dtos.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GlobalApiResponse {
    private String message;
    private String imageUrl;
    private String completedAt;
    private Boolean completed;
}
