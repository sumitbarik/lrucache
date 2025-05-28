package com.cvt.lrucache.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice(annotations = RestController.class)
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
//
//    @ExceptionHandler(value = Exception.class)
//    protected ResponseEntity<ErrorResponse> handleExceptionNotFound(Exception ex){
//        ErrorResponse errorResponse = new ErrorResponse();
//        errorResponse.setTime(LocalDateTime.now());
//        errorResponse.setMessage(ex.getMessage());
//        errorResponse.setType("not found");
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
//    }
//
//    @ExceptionHandler(value = Exception.class)
//    protected ResponseEntity<ErrorResponse> handleException(Exception ex){
//        ErrorResponse errorResponse = new ErrorResponse();
//        errorResponse.setTime(LocalDateTime.now());
//        errorResponse.setMessage(ex.getMessage());
//        errorResponse.setType("not found");
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
//    }

}
