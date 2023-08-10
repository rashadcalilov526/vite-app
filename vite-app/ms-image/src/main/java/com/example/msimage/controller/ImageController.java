package com.example.msimage.controller;

import com.example.msimage.model.ImageData;
import com.example.msimage.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

    private final StorageService service;
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Long uploadImage( @RequestPart("file") MultipartFile file) throws IOException {
        return service.uploadImage(file);
    }

    @GetMapping("/{fileId}")
    @ResponseStatus(HttpStatus.OK)
    public ImageData downloadImage(@PathVariable Long fileId) {
        return service.downloadImage(fileId);
    }
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<ImageData> getAllImages() {
        return service.getAllImages();
    }
}
