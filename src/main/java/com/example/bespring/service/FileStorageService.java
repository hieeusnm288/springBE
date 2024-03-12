package com.example.bespring.service;

import com.example.bespring.config.FileStorageProperties;
import com.example.bespring.exception.FileNotFoundExeption;
import com.example.bespring.exception.FileStrorageExceptionException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {
    private final Path fileLogoStorageLocation;

    public FileStorageService(FileStorageProperties fileStorageProperties){
        this.fileLogoStorageLocation = Paths.get(fileStorageProperties.getUploadLogoDir()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(fileLogoStorageLocation);
        }catch (Exception ex){
            throw new FileStrorageExceptionException("Counld not create the directory where the uploaded file will be strored", ex);
        }
    }

    public String storeLogoFile(MultipartFile file){
        return storeFile(fileLogoStorageLocation, file);
    }

    private String storeFile(Path location, MultipartFile file){
        UUID uuid = UUID.randomUUID();
        String ext = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String filename = uuid.toString()+"."+ext;
        try {
            if (filename.contains("..")){
                throw new FileStrorageExceptionException("Sorry! Filename contains invalid path sequence: " +filename);
            }
            Path targetLocation = location.resolve(filename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return filename;
        }catch (Exception ex){
            throw  new FileStrorageExceptionException("Could not store File " +filename+". Please try again", ex);
        }
    }

    public Resource loadLogoFileAsResource(String filename){
        return loadFileAsResource(fileLogoStorageLocation, filename);
    }

    private Resource loadFileAsResource(Path location, String filename){
        try{
            Path filePath = location.resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()){
                return resource;
            }else {
                throw new FileNotFoundExeption("File Not Found");
            }
        }catch (Exception ex){
            throw new FileNotFoundExeption("File Not Found",ex);
        }
    }

    private void deleteFile(Path location, String filename){
        try {
            Path filePath = location.resolve(filename).normalize();
            if (!Files.exists(filePath)){
                throw new FileNotFoundExeption("File not found");
            }
            Files.delete(filePath);
        }catch (Exception ex){
            throw new FileNotFoundExeption("File not found" , ex);
        }
    }

}
