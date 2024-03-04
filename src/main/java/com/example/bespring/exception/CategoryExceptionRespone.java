package com.example.bespring.exception;

import lombok.Data;

@Data
public class CategoryExceptionRespone {
    public String message;

    public CategoryExceptionRespone(String message) {
        this.message = message;
    }
}
