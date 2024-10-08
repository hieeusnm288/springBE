package com.example.bespring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FileNotFoundExeption extends RuntimeException{
    public FileNotFoundExeption(String message) {
        super(message);
    }

    public FileNotFoundExeption(String message, Throwable cause) {
        super(message, cause);
    }
}
