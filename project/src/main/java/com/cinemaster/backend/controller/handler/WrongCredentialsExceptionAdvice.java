package com.cinemaster.backend.controller.handler;

import com.cinemaster.backend.core.exception.WrongCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class WrongCredentialsExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(WrongCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String wrongCredentialsExceptionHandler(WrongCredentialsException e) {
        return e.getMessage();
    }
}
