package com.example.bespring.repository;

import com.example.bespring.domain.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    List<Brand> findByNameContainsIgnoreCase(String name);
    List<Brand> findByIdNotAndNameContainsIgnoreCase(Long id, String name);

}