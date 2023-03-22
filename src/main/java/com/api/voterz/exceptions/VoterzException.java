package com.api.voterz.exceptions;

import java.io.Serial;

public class VoterzException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1;

    public VoterzException(String message) {
        super(message);
    }
}
