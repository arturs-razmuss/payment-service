package com.arpc.sotnim.config;

import com.arpc.sotnim.core.boundary.ErrorCodes;
import com.arpc.sotnim.core.boundary.RequestProcessingException;
import com.arpc.sotnim.core.boundary.dto.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.money.UnknownCurrencyException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RequestProcessingException.class)
    public ResponseEntity<RestResponse<?>> handleRequestProcessingException(RequestProcessingException ex) {
        var httpStatus = ex.getErrorCode().isRequestIssue() ? HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(RestResponse.error(ex.getErrorCode()), httpStatus);
    }
    @ExceptionHandler(UnknownCurrencyException.class)
    public ResponseEntity<RestResponse<?>> handleRequestProcessingException(UnknownCurrencyException ex) {
        return new ResponseEntity<>(RestResponse.error(ErrorCodes.BAD_CURRENCY), HttpStatus.BAD_REQUEST);
    }

}
