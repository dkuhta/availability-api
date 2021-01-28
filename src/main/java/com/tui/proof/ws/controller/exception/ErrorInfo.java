package com.tui.proof.ws.controller.exception;

import com.tui.proof.ws.exception.LogicalException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;

import java.io.Serializable;

@Getter
@Setter
@Slf4j
public class ErrorInfo implements Serializable {

    private int statusCode;
    private Object message;

    private static ErrorInfo of(int statusCode, Object msg) {
        log.error(null == msg ? null : msg.toString());
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.statusCode = statusCode;
        errorInfo.message = msg;
        return errorInfo;
    }

    static ErrorInfo of(RuntimeException e) {
        return of(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error");
    }

    static ErrorInfo of(LogicalException e) {
        return of(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    static ErrorInfo of(MissingServletRequestParameterException e) {
        return of(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    static ErrorInfo of(AuthenticationException e) {
        return of(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
    }

    static ErrorInfo of(MethodArgumentNotValidException e) {
        return of(ServerError.INPUT_INVALID, e.getBindingResult().toString());
    }

    private static ErrorInfo of(ServerError serverError, Object msg) {
        return of(serverError.getStatusCode(), msg);
    }

}
