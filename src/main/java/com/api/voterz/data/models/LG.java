package com.api.voterz.data.models;

import lombok.*;

import static com.api.voterz.data.models.Constituency.LAGOS_EAST;
import static com.api.voterz.data.models.State.LAGOS;

@Getter
@AllArgsConstructor
public enum LG {
    SURULERE("SU-025",LAGOS_EAST, LAGOS);

    private final String lgId;
    private final Constituency constituency;
    private final State state;
}
