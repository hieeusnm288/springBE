package com.example.bespring.controller;


import com.example.bespring.domain.Category;
import com.example.bespring.dto.CatagoryDTO;
import com.example.bespring.service.CategoryService;
import com.example.bespring.service.MapValidationErrorService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @Autowired
    MapValidationErrorService mapValidationErrorService;

    @PostMapping
    public ResponseEntity<?>  createCategory(@Valid @RequestBody CatagoryDTO catagoryDTO, BindingResult result){
        ResponseEntity<?> responseEntity = mapValidationErrorService.mapValidationFields(result);
        if (responseEntity != null){
            return responseEntity;
        }
        Category entity = new Category();
        BeanUtils.copyProperties(catagoryDTO, entity);
        entity = categoryService.save(entity);
        catagoryDTO.setId(entity.getId());
        return new ResponseEntity<>(catagoryDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?>  updateCategory(@PathVariable("id") Long id, @RequestBody CatagoryDTO catagoryDTO){
        Category entity = new Category();
        BeanUtils.copyProperties(catagoryDTO, entity);
        entity = categoryService.update(id,entity);
        catagoryDTO.setId(entity.getId());
        return new ResponseEntity<>(catagoryDTO, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<?> getListCategory(){
        return new ResponseEntity<>(categoryService.findAll(),HttpStatus.OK) ;
    }

    @GetMapping("/page")
    public ResponseEntity<?> getListCategory(
            @PageableDefault(size = 5, sort = "name", direction = Sort.Direction.ASC)
            Pageable pageable){

        return new ResponseEntity<>(categoryService.findAllPage(pageable),HttpStatus.OK) ;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id){
        return new ResponseEntity<>(categoryService.findById(id),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> Ddlete(@PathVariable("id") Long id){
        categoryService.deleteById(id);
        return new ResponseEntity<>("Deleted category id = " +id,HttpStatus.OK);
    }
}
