package com.example.msimage.service;

import com.example.msimage.model.ImageData;
import com.example.msimage.repository.StorageRepository;

import com.example.msimage.util.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StorageService {

    private final StorageRepository repository;

    public Long uploadImage(MultipartFile file) throws IOException {

        ImageData imageData = repository.save(ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes())).build());
        if (imageData != null) {
            return imageData.getId();
        }
        return null;
    }

    public List<ImageData> getAllImages(){
        return repository.findAll();
    }
    public ImageData downloadImage(Long fileId){
        ImageData dbImageData = repository.findById(fileId).orElseThrow(()->new RuntimeException("image not found"));
        dbImageData.setImageData(ImageUtils.decompressImage(dbImageData.getImageData()));

        return dbImageData;
    }
}