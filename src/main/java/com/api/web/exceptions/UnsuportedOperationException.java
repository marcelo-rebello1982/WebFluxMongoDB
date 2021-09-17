package com.api.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnsuportedOperationException extends  RuntimeException{

    public UnsuportedOperationException() {
    }

    public UnsuportedOperationException(String message) {
        super(message);
    }
}
