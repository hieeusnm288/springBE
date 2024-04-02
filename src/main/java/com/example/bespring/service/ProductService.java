package com.example.bespring.service;

import com.example.bespring.domain.Brand;
import com.example.bespring.domain.Category;
import com.example.bespring.domain.Product;
import com.example.bespring.dto.ProductDTO;
import com.example.bespring.exception.CategoryException;
import com.example.bespring.repository.BrandRepository;
import com.example.bespring.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    BrandService brandService;

    @Autowired
    CategoryService categoryService;
    Date today = new Date(System.currentTimeMillis());


    java.sql.Date sqlDate = new java.sql.Date(today.getTime());

    public Product insert(ProductDTO productDTO) {
        Product entity = new Product();
        Category category = categoryService.findById(productDTO.getCategory_id());
        Brand brand = brandService.findById(productDTO.getBrand_id());
        BeanUtils.copyProperties(productDTO, entity);
        entity.setBrand(brand);
        entity.setCategory(category);
        entity.setCreateDate(sqlDate);
//        System.out.println(entity);
        if (productDTO.getProductFile() != null){
            String fileName = fileStorageService.storeLogoFile(productDTO.getProductFile());
            entity.setImage(fileName);
            productDTO.setProductFile(null);
        }


        return productRepository.save(entity);
    }

    public Page<Product> getList(String name, Long idCate, Long idBrand, Pageable pageable){
        if (name != null && !name.isEmpty()) {
            System.out.println("Service " +name);
            return productRepository.findByNameContainsIgnoreCase(name,pageable);
        } else if (idCate != null && idBrand != null) {
            return productRepository.findByCategory_IdAndBrand_Id(idCate, idBrand,pageable);
        } else if (idCate != null) {
            return productRepository.findByCategory_Id(idCate,pageable);
        } else if (idBrand != null) {
            return productRepository.findByBrand_Id(idBrand,pageable);
        } else {
            return productRepository.findAll(pageable);
        }
    }


    public Product findById(int id){
        return productRepository.findById(id).get();
    }

    public void deleteById(int id){
        productRepository.deleteById(id);
    }

    public Product update(int id, ProductDTO productDTO){
        Product product = productRepository.findById(id).get();
        Category category = categoryService.findById(productDTO.getCategory_id());
        Brand brand = brandService.findById(productDTO.getBrand_id());
        if (product == null){
            throw new CategoryException("Product không tồ tại");
        }
        Product entity = new Product();
        entity.setCreateDate(product.getCreateDate());
        BeanUtils.copyProperties(productDTO, entity);
        entity.setBrand(brand);
        entity.setCategory(category);
        if (productDTO.getProductFile() != null){
            String fileName = fileStorageService.storeLogoFile(productDTO.getProductFile());
            if (fileName != null){
                entity.setImage(fileName);
            }
            productDTO.setProductFile(null);
        }else {
            entity.setImage(product.getImage());
        }
        return productRepository.save(entity);
    }
}
