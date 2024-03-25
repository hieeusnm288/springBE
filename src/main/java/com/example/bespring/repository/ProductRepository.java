package com.example.bespring.repository;

import com.example.bespring.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Page<Product> findByNameContainsIgnoreCase(String name, Pageable pageable);

    Page<Product> findByCategory_Id(Long id, Pageable pageable);

    Page<Product> findByBrand_Id(Long id, Pageable pageable);

    Page<Product> findByCategory_IdAndBrand_Id(Long id, Long id1, Pageable pageable);
    
    

}
