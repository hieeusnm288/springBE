package com.example.bespring.service;

import com.example.bespring.domain.Account;
import com.example.bespring.domain.Role;
import com.example.bespring.dto.AccountDTO;
import com.example.bespring.exception.CategoryException;
import com.example.bespring.repository.AcountRepository;
import com.example.bespring.repository.RoleRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    AcountRepository acountRepository;

    @Autowired
    RoleRepository roleRepository;

    public Account insert(AccountDTO accountDTO){
        List<?> foundedListEmail =  acountRepository.findByEmailContainsIgnoreCase(accountDTO.getEmail());
        List<?> foundedListPhone =  acountRepository.findByPhoneContainsIgnoreCase(accountDTO.getPhone());
        if (foundedListEmail.size() >0 ) {
            throw new CategoryException("Email đã được sử dụng");
        }
        if (foundedListPhone.size() >0 ) {
            throw new CategoryException("Số điện thoại đã được sử dụng");
        }
        Account entity = new Account();
        Role role = roleRepository.findById(2L).get();
        BeanUtils.copyProperties(accountDTO, entity);
        entity.setRole(role);
        return acountRepository.save(entity);
    }
}
