package com.api.voterz.exceptions;

import java.io.Serial;

public class ImageUploadException extends VoterzException {
    @Serial
    private static final long serialVersionUID = 1;
    public ImageUploadException(String message) {
        super(message);
    }
}
