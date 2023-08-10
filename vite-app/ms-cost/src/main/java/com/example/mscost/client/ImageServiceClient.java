package com.example.mscost.client;

import com.example.mscost.client.imagedata.ImageData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(name = "ms-image", url = "http://localhost:8083/images") // Replace "your-api-base-url" with the actual base URL of your API
public interface ImageServiceClient {

    @PutMapping
    Long uploadImage( @RequestPart("file") MultipartFile file);

    @GetMapping("/{fileId}")
    ImageData downloadImage(@PathVariable Long fileId);

    @GetMapping("/all")
    List<ImageData> getAllImages();
}