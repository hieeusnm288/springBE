package com.example.bespring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FileStrorageExceptionException extends RuntimeException{
    public FileStrorageExceptionException(String message) {
        super(message);
    }

    public FileStrorageExceptionException(String message, Throwable cause) {
        super(message, cause);
    }
}
