package com.example.shop_service.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface ProductImageService {

    void uploadImage(UUID productId, MultipartFile file);

    void downloadImagesAsZip(UUID productId, HttpServletResponse response);
}
