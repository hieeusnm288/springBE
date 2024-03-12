package com.example.bespring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomResponEntity extends ResponseEntityExceptionHandler {
    @ExceptionHandler(CategoryException.class)
    public final ResponseEntity<Object> hanldCategoryException(CategoryException ex, WebRequest request){
        CategoryExceptionRespone exceptionRespone = new CategoryExceptionRespone(ex.getMessage());
        return new ResponseEntity<>(exceptionRespone, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileNotFoundExeption.class)
    public final ResponseEntity<Object> hanldFileNotFoundExeption(FileNotFoundExeption ex, WebRequest request){
        ExceptionRespone exceptionRespone = new ExceptionRespone(ex.getMessage());
        return new ResponseEntity<>(exceptionRespone, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileStrorageExceptionException.class)
    public final ResponseEntity<Object> hanldFileStrorageException(FileStrorageExceptionException ex, WebRequest request){
        ExceptionRespone exceptionRespone = new ExceptionRespone(ex.getMessage());
        return new ResponseEntity<>(exceptionRespone, HttpStatus.BAD_REQUEST);
    }
}
