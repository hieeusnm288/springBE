package com.example.bespring.service;

import com.example.bespring.domain.Brand;
import com.example.bespring.dto.BrandDTO;
import com.example.bespring.exception.CategoryException;
import com.example.bespring.exception.ExceptionRespone;
import com.example.bespring.repository.BrandRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BrandService {

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    private FileStorageService fileStorageService;

    public Brand insertBrand(BrandDTO dto){
        List<?> foundedList =  brandRepository.findByNameContainsIgnoreCase(dto.getName());
        if (foundedList.size() >0){
            throw new CategoryException("Đã tồn tại brand");
        }
        Brand entity = new Brand();
        BeanUtils.copyProperties(dto, entity);
        if (dto.getLogoFile() != null) {
            String filename = fileStorageService.storeLogoFile(dto.getLogoFile());
            entity.setLogo(filename);
            dto.setLogoFile(null);
        }
        return brandRepository.save(entity);
    }
}
