package com.example.bespring.dto;

import com.example.bespring.domain.Product;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class OrderDTO {
    private int id;
    @NotEmpty(message = "Addres name is requried")
    private String address;
    @NotEmpty(message = "Addres name is requried")
    private String username;

    private List<Product> list;
}
