package com.example.bespring.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AccountDTO {
    private Long id;
    @NotEmpty(message = "Name is requried")
    private String name;
    @NotEmpty(message = "Email is requried")
    private String email;
    @NotEmpty(message = "Phone is requried")
    private String phone;
    @NotEmpty(message = "Password is requried")
    private String password;
    @NotEmpty(message = "Username is requried")
    private String username;

}
