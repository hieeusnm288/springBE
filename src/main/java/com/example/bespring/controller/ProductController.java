package com.example.bespring.controller;

import com.example.bespring.domain.Product;
import com.example.bespring.dto.BrandDTO;
import com.example.bespring.dto.ProductDTO;
import com.example.bespring.exception.FileStrorageExceptionException;
import com.example.bespring.service.FileStorageService;
import com.example.bespring.service.MapValidationErrorService;
import com.example.bespring.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    MapValidationErrorService mapValidationErrorService;

    @GetMapping("/search")
    public ResponseEntity<?> getListProduct(@RequestParam(required = false) String name , @RequestParam(required = false) Long categoryId,@RequestParam(required = false) Long brandId,
                                          @PageableDefault(size = 10, sort = "name", direction = Sort.Direction.ASC)
                                          Pageable pageable)
    {
        return new ResponseEntity<>(productService.getList(name,categoryId,brandId, pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDetailProduct(@PathVariable("id") int id){
        return ResponseEntity.ok(productService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") int id){
        productService.deleteById(id);
        return ResponseEntity.ok("Xóa thành công");
    }

    @PostMapping()
    public ResponseEntity<?> insertProduct(@Valid @ModelAttribute ProductDTO productDTO, BindingResult result){
        ResponseEntity<?> respon = mapValidationErrorService.mapValidationFields(result);
        if (respon != null) {
            return respon;
        }
        Product product = productService.insert(productDTO);
        productDTO.setId(product.getId());
        return ResponseEntity.ok(product);
    }

    @GetMapping("/image/{fileName:.+}")
    public ResponseEntity<?> loadFile(@PathVariable String fileName, HttpServletRequest httpServletRequest) {
        Resource resource = fileStorageService.loadLogoFileAsResource(fileName);
        String contentTye = null;
        try {
            contentTye = httpServletRequest.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (Exception ex) {
            throw new FileStrorageExceptionException("FIle Not Fould", ex);
        }
        if (contentTye == null) {
            contentTye = "application/octet-stream";
        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentTye)).header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\""
                + resource.getFilename() + "\"").body(resource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") int id, @ModelAttribute ProductDTO productDTO, BindingResult result){
        ResponseEntity<?> response = mapValidationErrorService.mapValidationFields(result);
        if (response != null) {
            return response;
        }
        productService.update(id,productDTO);
        return ResponseEntity.ok("Update thành công");
    }

}
