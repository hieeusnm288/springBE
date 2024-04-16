package com.example.bespring.controller;

import com.example.bespring.domain.Category;
import com.example.bespring.dto.AccountDTO;
import com.example.bespring.exception.CategoryException;
import com.example.bespring.security.JwtResponse;
import com.example.bespring.security.LoginRequest;
import com.example.bespring.service.AccountService;
import com.example.bespring.service.JWTService;
import com.example.bespring.service.MapValidationErrorService;
import com.example.bespring.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {
    @Autowired
    AccountService accountService;
    @Autowired
    MapValidationErrorService mapValidationErrorService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private UserService userService;

    @Autowired
    private JWTService jwtService;


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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        // Xac thuc nguoi dung bang username va password
        try {
            Authentication authentication = manager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
            // Neu xac thuc thah cong tao jwt
            if (authentication.isAuthenticated()){
                final String jwt = jwtService.generateToken(loginRequest.getUsername());
                return ResponseEntity.ok().body(new JwtResponse(jwt));
            }
        }catch (AuthenticationException e){
            return ResponseEntity.badRequest().body("Login Fail, Username or Password Incorrect");
        }
        return ResponseEntity.badRequest().body("Login Fail");
    }

 }
