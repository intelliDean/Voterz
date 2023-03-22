package com.api.voterz.data.models;

public enum ElectionType {
    PRESIDENT("President"),
    SENATOR("Senator"),
    NATIONAL_LAWMAKER("National Lawmaker"),
    GOVERNOR("Governor"),
    STATE_LAWMAKER("State Lawmaker"),
    LG_CHAIRMAN("LG Chairman"),
    COUNCILOR("Councilor");

    private final String value;

    ElectionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
