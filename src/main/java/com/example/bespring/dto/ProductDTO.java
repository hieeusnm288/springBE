package com.example.bespring.dto;

import com.example.bespring.domain.Brand;
import com.example.bespring.domain.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductDTO {
    private Long id;

    private String name;

    private Long price;

    private int quantity;

    private int status;

    private String image;

    @JsonIgnore
    private MultipartFile productFile;

    private String specifications;

    private String description;

    private Long category_id;

    private Long brand_id;

//    private BrandDTO brandDTO;
//
//    private CatagoryDTO catagoryDTO;
}
