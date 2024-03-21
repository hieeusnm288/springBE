package com.example.bespring.service;

import com.example.bespring.domain.Category;
import com.example.bespring.exception.CategoryException;
import com.example.bespring.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Category save(Category entity) {
        return categoryRepository.save(entity);
    }

    public Category update(Long id,Category entity) {
        Category category = categoryRepository.findById(id).get();
        if (category == null){
            throw new CategoryException("Category id " + id + " do not exist");
        }
        try {
//            category.setId(id);
            category.setName(entity.getName());
            category.setStatus(entity.getStatus());
            return categoryRepository.save(category);
        }catch (Exception ex){
            throw new CategoryException("Category is updated fail");
        }
    }

    public List<Category> findAll(){
        return categoryRepository.findAll();
    }

    public Page<Category> findAllPage(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    public Category findById(Long id){
       Category category = categoryRepository.findById(id).get();
        if (category == null){
            throw new CategoryException("Category width id :" +id+"dose not exits");
        }
        return category;
    }

    public void deleteById(Long id) {
        Category category = categoryRepository.findById(id).get();
        if (category == null){
            throw new CategoryException("Category width id :" +id+"dose not exits");
        }
       categoryRepository.delete(category);
    }
}
