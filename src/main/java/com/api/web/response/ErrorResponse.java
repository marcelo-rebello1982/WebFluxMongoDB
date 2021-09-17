package com.api.web.response;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Entity not found")
public class ErrorResponse extends AppResponse {
    public ErrorResponse(String errorMessage) {
        super(false);
    }
}