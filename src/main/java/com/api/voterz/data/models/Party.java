package com.api.voterz.data.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Party {
    APC("All Progressive Congress"),
    PDP("People Democratic Party"),
    LP("Labour Party");
       private final String fullName;
}
