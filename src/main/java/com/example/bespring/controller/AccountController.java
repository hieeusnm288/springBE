package com.example.bespring.controller;

import com.example.bespring.domain.Category;
import com.example.bespring.dto.AccountDTO;
import com.example.bespring.service.AccountService;
import com.example.bespring.service.MapValidationErrorService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok().body("Đăng ký thành công");
    }

    @GetMapping("/search")
    public ResponseEntity<?> getListAccount(@RequestParam(required = false) String name,
                                            @PageableDefault(size = 10, sort = "name", direction = Sort.Direction.ASC)
                                            Pageable pageable)
    {
        return new ResponseEntity<>(accountService.findByUserName(name, pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDetail(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(accountService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAccount(@PathVariable("id") Long id, @RequestBody AccountDTO accountDTO,BindingResult result){
        ResponseEntity<?> responseEntity = mapValidationErrorService.mapValidationFields(result);
        if (responseEntity != null){
            return responseEntity;
        }
        System.out.println("accountDTO : "+ accountDTO);
        accountService.updateAccount(id,accountDTO);
        return ResponseEntity.ok().body("Sửa thành công");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable("id") Long id){
        accountService.deleteAccount(id);
        return ResponseEntity.ok().body("Xóa thành công");
    }
 }
