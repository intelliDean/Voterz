package com.api.voterz.utilities.config;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MailConfig {
    private String mailApiKey;
    private String mailUrl;
}
