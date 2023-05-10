package com.api.voterz.data.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.api.voterz.data.models.State.LAGOS;

@AllArgsConstructor
@Getter
public enum Constituency {
    LAGOS_WEST("LW-021", LAGOS),
    LAGOS_EAST("LE-052", LAGOS);




    private final String constituencyId;
    private final State state;
}
