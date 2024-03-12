package com.example.bespring.exception;

import lombok.Data;

@Data
public class ExceptionRespone {
    public String message;

    public ExceptionRespone(String message) {
        this.message = message;
    }
}
