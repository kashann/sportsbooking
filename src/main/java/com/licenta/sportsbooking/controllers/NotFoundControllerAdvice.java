package com.licenta.sportsbooking.controllers;

import com.licenta.sportsbooking.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class NotFoundControllerAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public String handleNotFound(Exception exception) {
        log.error("Handling 404 not found exception");
        log.error(exception.getMessage());

        return "404 NOT FOUND\n" + exception.getMessage();
    }
}
