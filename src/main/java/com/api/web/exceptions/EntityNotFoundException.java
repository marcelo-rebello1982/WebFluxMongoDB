package com.api.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public class EntityNotFoundException extends ResponseStatusException {

    public EntityNotFoundException(HttpStatus status, String reason) {
        super(status, reason);
    }

    public EntityNotFoundException(String message) {
        this(HttpStatus.NOT_FOUND, message);
    }

    public EntityNotFoundException(HttpStatus status, String reason, Throwable cause) {
        super(status, reason, cause);
    }

    public EntityNotFoundException(int rawStatusCode, String reason, Throwable cause) {
        super(rawStatusCode, reason, cause);
    }
}






//public class EntityNotFoundException extends AppResponse {
//    public EntityNotFoundException(String errorMessage) {
//        super(false);
//        addFullMessage(errorMessage);
//    }
//}
