package com.api.voterz.data.dtos.requests;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class EmailRequest {
    private final Email sender = new Email("INEC", "info@inec_ng.org");
    private Email recipient;
    private String subject;
    private String content;
}