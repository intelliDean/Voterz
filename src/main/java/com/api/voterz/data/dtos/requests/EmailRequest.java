package com.api.voterz.data.dtos.requests;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailRequest {
    private Email sender;
    private Email recipient;
    private String subject;
    private String content;
}