package com.example.shop_service.controller;

import com.example.shop_service.service.ProductImageService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
public class ProductImageController {

    private final ProductImageService productImageService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam UUID productId,
                                              @RequestParam MultipartFile file) {
        productImageService.uploadImage(productId, file);
        return ResponseEntity.ok("Изображение загружено");
    }

    @PostMapping("/download/{productId}")
    public void downloadImagesAsZip (@PathVariable UUID productId, HttpServletResponse response) {
        productImageService.downloadImagesAsZip(productId, response);
    }

}
