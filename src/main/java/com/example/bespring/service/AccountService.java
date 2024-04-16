package com.example.bespring.service;

import com.example.bespring.domain.Account;
import com.example.bespring.domain.Brand;
import com.example.bespring.domain.Role;
import com.example.bespring.dto.AccountDTO;
import com.example.bespring.exception.CategoryException;
import com.example.bespring.repository.AcountRepository;
import com.example.bespring.repository.RoleRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    AcountRepository acountRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Account insert(AccountDTO accountDTO){
        List<?> foundedListEmail =  acountRepository.findByEmailContainsIgnoreCase(accountDTO.getEmail());
        List<?> foundedListPhone =  acountRepository.findByPhoneContainsIgnoreCase(accountDTO.getPhone());
        List<?> foundedListUsername =  acountRepository.findByUsernameIgnoreCase(accountDTO.getUsername());
        if (foundedListEmail.size() >0 ) {
            throw new CategoryException("Email đã được sử dụng");
        }
        if (foundedListPhone.size() >0 ) {
            throw new CategoryException("Số điện thoại đã được sử dụng");
        }
        if (foundedListUsername.size() >0 ) {
            throw new CategoryException("Username đã được sử dụng");
        }
        Account entity = new Account();
        Role role = roleRepository.findById(2L).get();
        BeanUtils.copyProperties(accountDTO, entity);
        entity.setRole(role);
        // Ma hoa mat Khau
        String encryptPassword = passwordEncoder.encode(accountDTO.getPassword());
        entity.setPassword(encryptPassword);
        return acountRepository.save(entity);
    }

    public Page<Account> findByUserName(String username, Pageable pageable){
        return acountRepository.findByUsernameContainsIgnoreCase(username, pageable);
    }

    public Account findById (Long id){
       Account account = acountRepository.findById(id).get();
       if (account == null){
           throw new CategoryException("Khong tim thay account");
       }
       return account;
    }

    public Account updateAccount(Long id, AccountDTO accountDTO){
        Account account = acountRepository.findById(id).get();
        if (account == null){
            throw new CategoryException("Khong tim thay account");
        }
        try {
            account.setUsername(account.getUsername());
            account.setPhone(accountDTO.getPhone());
            account.setName(accountDTO.getName());
            account.setEmail(accountDTO.getEmail());
            account.setPassword(accountDTO.getPassword());
            return acountRepository.save(account);
        }catch (Exception e){
            throw new CategoryException("Loi");
        }
    }

    public void deleteAccount(Long id){
        Account account = acountRepository.findById(id).get();
        if (account == null){
            throw new CategoryException("Khong tim thay account");
        }
        acountRepository.deleteById(id);
    }

}
