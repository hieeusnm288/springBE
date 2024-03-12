package com.example.bespring.controller;

import com.example.bespring.domain.Brand;
import com.example.bespring.dto.BrandDTO;
import com.example.bespring.service.BrandService;
import com.example.bespring.service.FileStorageService;
import com.example.bespring.service.MapValidationErrorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    MapValidationErrorService mapValidationErrorService;

    @PostMapping("")
    public ResponseEntity<?> insertBrand(@Valid @ModelAttribute BrandDTO dto, BindingResult bindingResult){
        ResponseEntity<?> responseEntity = mapValidationErrorService.mapValidationFields(bindingResult);
        if (responseEntity != null) {
            return responseEntity;
        }
        Brand entity = brandService.insertBrand(dto);
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setLogo(entity.getLogo());
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

}
