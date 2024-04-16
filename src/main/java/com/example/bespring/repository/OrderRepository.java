package com.example.bespring.repository;

import com.example.bespring.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Page<Order> findByAccount_UsernameContainsIgnoreCase(String username, Pageable pageable);

}
