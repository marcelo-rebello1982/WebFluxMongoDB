package com.api.web.response;

import com.api.web.enums.ErrorCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
// @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AppResponse {

    private String message;
    private int status;
    private String code;
    private Boolean success;
    private List<FieldError> errors;
    private List<String> fullMessages;

    public AppResponse() {
        System.out.println("Created AppResponse");
    }

    public AppResponse(boolean success, String message) {
        this.success = success;
        addFullMessage(message);
    }

    private AppResponse (final ErrorCode code, final List<FieldError> errors) {
        this.message = code.getMessage();
        this.status = code.getStatus();
        this.errors = errors;
        this.code = code.getCode();
    }

    private AppResponse(final ErrorCode code) {
        this.message = code.getMessage();
        this.status = code.getStatus();
        this.code = code.getCode();
        this.errors = new ArrayList<>();
    }


    public Boolean getSuccess() {
        return success;
    }
    public List<String> getFullMessages() {
        return fullMessages;
    }
    public void setFullMessages(List<String> fullMessages) {
        this.fullMessages = fullMessages;
    }

    public AppResponse(boolean success) {
        this.success = success;
        fullMessages = new ArrayList<>();
    }

    public boolean isSuccess() {
        return success;
    }

    protected void addFullMessage(String message) {
        if (message == null)
            return;
        if (fullMessages == null)
            fullMessages = new ArrayList<>();
        fullMessages.add(message);
    }

    public static AppResponse of(final ErrorCode code, final BindingResult bindingResult) {
        return new AppResponse(code, FieldError.of(bindingResult));
    }

    public static AppResponse of(final ErrorCode code) {
        return new AppResponse(code);
    }

    public static AppResponse of(final ErrorCode code, final List<FieldError> errors) {
        return new AppResponse(code, errors);
    }

    public static AppResponse of(MethodArgumentTypeMismatchException e) {
        final String value = e.getValue() == null ? "" : e.getValue().toString();
        final List<AppResponse.FieldError> errors = AppResponse.FieldError.of(e.getName(), value, e.getErrorCode());
        return new AppResponse(ErrorCode.INVALID_TYPE_VALUE, errors);
    }


    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FieldError {
        private String field;
        private String value;
        private String reason;

        private FieldError(final String field, final String value, final String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        public static List<FieldError> of(final String field, final String value, final String reason) {
            List<FieldError> fieldErrors = new ArrayList<>();
            fieldErrors.add(new FieldError(field, value, reason));
            return fieldErrors;
        }

        private static List<FieldError> of(final BindingResult bindingResult) {
            final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(error -> new FieldError(
                            error.getField(),
                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()))
                    .collect(Collectors.toList());
        }
    }
}