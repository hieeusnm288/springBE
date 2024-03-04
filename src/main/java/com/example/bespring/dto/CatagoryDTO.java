package com.example.bespring.dto;

import com.example.bespring.domain.CategoryStatus;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.io.Serializable;

@Data
public class CatagoryDTO implements Serializable {
    private Long id;
    @NotEmpty(message = "Category name is requried")
    private String name;
    private CategoryStatus status;
}
