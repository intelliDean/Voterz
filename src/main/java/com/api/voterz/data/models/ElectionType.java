package com.api.voterz.data.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ElectionType {
    PRESIDENT("President"),
    SENATOR("Senator"),
    NATIONAL_LAWMAKER("National Lawmaker"),
    GOVERNOR("Governor"),
    STATE_LAWMAKER("State Lawmaker"),
    LG_CHAIRMAN("LG Chairman"),
    COUNCILOR("Councilor");

    private final String value;
}
