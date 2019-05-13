package com.payment.demo.controllers;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@Component
@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler({MissingServletRequestParameterException.class})
    public ResponseEntity<Object> HandleError(){
     //   return "handling errors";
        return  ResponseEntity.status(400).body("handling error");
    }
}
