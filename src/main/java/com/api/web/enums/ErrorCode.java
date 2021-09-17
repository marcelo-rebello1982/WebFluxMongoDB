package com.api.web.enums;

import com.api.web.exceptions.BaseEnumCode;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode implements BaseEnumCode<String> {


    INVALID_INPUT_VALUE(400, "C001", " Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "C002", " Invalid Input Value"),
    ENTITY_NOT_FOUND(400, "C003", " Entity Not Found"),
    INTERNAL_SERVER_ERROR(500, "C004", "Server Error"),
    INVALID_TYPE_VALUE(400, "C005", " Invalid Type Value"),
    HANDLE_ACCESS_DENIED(403, "C006", "Access is Denied"),

    EMAIL_DUPLICATION(400, "M001", "Email is Duplication"),
    LOGIN_INPUT_INVALID(400, "M002", "Login input is invalid"),

    COUPON_ALREADY_USE(400, "CO001", "Coupon was already used"),
    COUPON_EXPIRE(400, "CO002", "Coupon was already expired"),

    BASE_CODE("Error.Base"),
    CODE2("Error.Code2"),
    CODE3("Error.Code3");
    private String value;
    private String code;
    private String message;
    private int status;

    ErrorCode(String value) {
        this.value = value;
    }

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public String getValue() {
        return value;
    }
}