package com.example.bespring.repository;

import com.example.bespring.domain.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    List<Brand> findByNameContainsIgnoreCase(String name);
    List<Brand> findByIdNotAndNameContainsIgnoreCase(Long id, String name);

}