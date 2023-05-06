package com.api.voterz.data.dtos.requests;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Email {
    private String name;
    private String email;
}
