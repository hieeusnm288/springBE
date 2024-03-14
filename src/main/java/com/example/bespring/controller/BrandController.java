package com.example.bespring.controller;

import com.example.bespring.domain.Brand;
import com.example.bespring.dto.BrandDTO;
import com.example.bespring.exception.FileNotFoundExeption;
import com.example.bespring.exception.FileStrorageExceptionException;
import com.example.bespring.service.BrandService;
import com.example.bespring.service.FileStorageService;
import com.example.bespring.service.MapValidationErrorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    MapValidationErrorService mapValidationErrorService;

    @PostMapping("")
    public ResponseEntity<?> insertBrand(@Valid @ModelAttribute BrandDTO dto, BindingResult bindingResult){
        ResponseEntity<?> responseEntity = mapValidationErrorService.mapValidationFields(bindingResult);
        if (responseEntity != null) {
            return responseEntity;
        }
        Brand entity = brandService.insertBrand(dto);
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setLogo(entity.getLogo());
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/logo/{fileName:.+}")
    public ResponseEntity<?> loadFile(@PathVariable String fileName, HttpServletRequest httpServletRequest){
        Resource resource = fileStorageService.loadLogoFileAsResource(fileName);
        String contentTye = null;
        try {
            contentTye = httpServletRequest.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        }catch (Exception ex){
            throw new FileStrorageExceptionException("FIle Not Fould", ex);
        }
        if (contentTye == null){
            contentTye = "application/octet-stream";
        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentTye)).header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\""
                +resource.getFilename()+ "\"" ).body(resource);
    }

}
