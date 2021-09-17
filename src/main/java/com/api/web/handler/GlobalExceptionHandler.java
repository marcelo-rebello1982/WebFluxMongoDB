package com.api.web.handler;


import com.api.web.enums.ErrorCode;
import com.api.web.exceptions.BusinessException;
import com.api.web.exceptions.CheckException;
import com.api.web.exceptions.CustomBaseException;
import com.api.web.response.AppErrorResponse;
import com.api.web.response.AppResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.nio.file.AccessDeniedException;
import java.util.Locale;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private MessageSource messageSource;

    @ExceptionHandler({CustomBaseException.class})
    public ResponseEntity<AppErrorResponse> handleAppBaseException(
            CustomBaseException ex, Locale locale, ServerWebExchange exchange) {

        if (ex.getCause() != null) {
            log.error(ex.getLocalizedMessage(), ex);
        }

        HttpStatus status = ex.getStatus() != null
                ? ex.getStatus()
                : HttpStatus.BAD_REQUEST;

        String errorMessage = ex.getErrorCode() != null
                ? messageSource.getMessage(ex.getErrorCode()
                .getValue(), ex.getArgs(), locale)
                : null;

        return new ResponseEntity<>(
                AppErrorResponse.builder()
                        .timestamp(System.currentTimeMillis())
                        .path(exchange.getRequest().getPath().value())
                        .status(status.value())
                        .error(status.getReasonPhrase())
                        .message(errorMessage != null ? errorMessage : ex.getLocalizedMessage())
                        .requestId(exchange.getRequest().getId())
                        .build(), status
        );
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<String> handlerBindException(WebExchangeBindException ex) {
        return new ResponseEntity<>(toString(ex), HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(CheckException.class)
    public ResponseEntity<String> handlerCheckException(CheckException ex) {
        return new ResponseEntity<>(toString(ex), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<AppResponse> handleAccessDeniedException(AccessDeniedException e) {
        log.error("handleAccessDeniedException", e);
        final AppResponse response = AppResponse.of(ErrorCode.HANDLE_ACCESS_DENIED);
        return new ResponseEntity<>(response, HttpStatus.valueOf(ErrorCode.HANDLE_ACCESS_DENIED.getStatus()));
    }

    @ExceptionHandler(BindException.class)
    protected ResponseEntity<AppResponse> handleBindException(BindException e) {
        log.error("handleBindException", e);
        final AppResponse response = AppResponse.of(ErrorCode.INVALID_INPUT_VALUE, e.getBindingResult());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    protected ResponseEntity<AppResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
//        log.error("handleMethodArgumentNotValidException", e);
//        final AppResponse response = AppResponse.of(ErrorCode.INVALID_INPUT_VALUE, e.getBindingResult());
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }

//    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
//    protected ResponseEntity<AppResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
//        log.error("handleMethodArgumentTypeMismatchException", e);
//        final AppResponse response = AppResponse.of(e);
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }

//    @ExceptionHandler(BusinessException.class)
//    protected ResponseEntity<AppResponse> handleBusinessException(final BusinessException e) {
//        log.error("handleEntityNotFoundException", e);
//        final ErrorCode errorCode = e.getErrorCode();
//        final AppResponse response = AppResponse.of(errorCode);
//        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
//    }

//    @ExceptionHandler(Exception.class)
//    protected ResponseEntity<AppResponse> handleException(Exception e) {
//        log.error("handleEntityNotFoundException", e);
//        final AppResponse response = AppResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
//        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//    }


//    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
//    protected ResponseEntity<AppResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
//        log.error("handleHttpRequestMethodNotSupportedException", e);
//        final AppResponse response = AppResponse.of(ErrorCode.METHOD_NOT_ALLOWED);
//        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
//    }

    private String toString(CheckException ex) {
        return ex.getFieldName() + "：" + ex.getFieldValue();
    }

    private String toString(WebExchangeBindException ex) {
        return ex.getFieldErrors().stream()
                .map(e -> e.getField() + ":" +
                        e.getDefaultMessage())
                .reduce("", (s1, s2) -> s1 + "\n" + s2);
    }
}