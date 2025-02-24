package com.example.shop_service.service.impl;

import com.example.shop_service.persistence.models.Product;
import com.example.shop_service.persistence.models.ProductImage;
import com.example.shop_service.persistence.repository.ProductImageRepository;
import com.example.shop_service.persistence.repository.ProductRepository;
import com.example.shop_service.service.ProductImageService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductImageRepository productImageRepository;

    private final ProductRepository productRepository;

    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Override
    public void uploadImage(UUID productId, MultipartFile file) {

        String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();

        try {
            s3Client.putObject(PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .contentType(file.getContentType())
                    .build(),
                    RequestBody.fromBytes(file.getBytes()));

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            ProductImage productImage = ProductImage.builder()
                    .product(product)
                    .s3FileId(fileName)
                    .build();
            productImageRepository.save(productImage);

        } catch (IOException e) {
            throw new RuntimeException("Error upload", e);
        }

    }
    public List<String> getImagesByProductId(UUID productId) {
        return productImageRepository.findByProductId(productId)
                .stream()
                .map(ProductImage::getS3FileId)
                .collect(Collectors.toList());
    }

    @Override
    public void downloadImagesAsZip(UUID productId, HttpServletResponse response) {
        String productName = productRepository.findProductNameById(productId);

        if (productName == null) {
            throw new RuntimeException("Продукт с ID " + productId + " не найден");
        }

        String safeProductName = productName.replaceAll("\\s+", "_");

        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader("Content-Disposition", "attachment; filename=\"" + safeProductName + ".zip\"");

        List<String> imageKeys = getImagesByProductId(productId);

        try (ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream())) {
            for (String key : imageKeys) {
                ResponseInputStream<GetObjectResponse> object = s3Client.getObject(GetObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .build());
                zipOut.putNextEntry(new ZipEntry(key));
                zipOut.write(object.readAllBytes());
                zipOut.closeEntry();
            }
            zipOut.finish();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка загрузки архива", e);
        }
    }
}
