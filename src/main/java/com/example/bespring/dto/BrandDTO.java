package com.example.bespring.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BrandDTO {
    private Long id;
    private String name;
    private String logo;

    @JsonIgnore
    private MultipartFile logoFile;
}
