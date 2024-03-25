package com.example.bespring.controller;

import com.example.bespring.domain.Category;
import com.example.bespring.dto.AccountDTO;
import com.example.bespring.service.AccountService;
import com.example.bespring.service.MapValidationErrorService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {
    @Autowired
    AccountService accountService;
    @Autowired
    MapValidationErrorService mapValidationErrorService;

    @PostMapping()
    public ResponseEntity<?> createAccount(@Valid @RequestBody AccountDTO accountDTO, BindingResult result){
        ResponseEntity<?> responseEntity = mapValidationErrorService.mapValidationFields(result);
        if (responseEntity != null){
            return responseEntity;
        }
        System.out.println("accountDTO : "+ accountDTO);
            accountService.insert(accountDTO);
//        Category entity = new Category();
//        BeanUtils.copyProperties(accountDTO, entity);
//        entity = accountService.insert(accountDTO);
//        catagoryDTO.setId(entity.getId());
        return ResponseEntity.ok().body("Đăng ký thành công");
    }
}
