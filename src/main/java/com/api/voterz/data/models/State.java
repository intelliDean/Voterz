package com.api.voterz.data.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum State {
    LAGOS("LG-05"),
    ABIA("AB-01");
    private final String stateId;
}
