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
    Page<Product> findByNameContainsIgnoreCaseAndCategory_IdNotAndBrand_IdNot(String name, @NonNull Long id, @NonNull Long id1, Pageable pageable);

//    Page<Product> findByNameContainsIgnoreCaseAndCategory_IdNotAndBrand_IdNot(String name, Long idCate, Long idBrand, Pageable pageable);

//    Page<Product> findByNameContainsIgnoreCaseAndCategory_IdAndBrand_Id(String name, Long idCate, Long idBrand, Pageable pageable);

}
