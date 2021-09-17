package com.api.web.exceptions;

import com.api.web.model.EmailModel;

public class EmailDuplicatedException extends RuntimeException {

    private EmailModel email;
    private String field;

    public EmailDuplicatedException(EmailModel email) {
        this.field = "email";
        this.email = email;
    }
}
