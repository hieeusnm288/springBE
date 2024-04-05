package com.example.bespring.repository;

import com.example.bespring.domain.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AcountRepository extends JpaRepository<Account, Long> {
    List<Account> findByEmailContainsIgnoreCase(String email);

    List<Account> findByPhoneContainsIgnoreCase(String phone);

    List<Account> findByUsernameIgnoreCase(String username);

    Page<Account> findByUsernameContainsIgnoreCase(String username, Pageable pageable);

}
