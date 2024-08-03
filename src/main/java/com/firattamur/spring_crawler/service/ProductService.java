package com.firattamur.spring_crawler.service;

import com.firattamur.spring_crawler.domain.entity.ProductEntity;
import com.firattamur.spring_crawler.domain.entity.ProductStatus;
import com.firattamur.spring_crawler.domain.model.ParsedProductData;
import com.firattamur.spring_crawler.repository.ProductRepository;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductEntity save(ProductEntity productEntity) {
        try {

            return productRepository.save(productEntity);

        } catch (DataIntegrityViolationException e) {

            if (e.getCause() instanceof ConstraintViolationException) {
                throw new ConstraintViolationException("Product with the same URL already exists", null);
            } else {
                throw e;
            }

        }

    }

    public Optional<ProductEntity> findById(Long id) {
        return productRepository.findById(id);
    }

    public Optional<ProductEntity> findByUrl(String url) {
        return productRepository.findByUrl(url);
    }

    public void update(ParsedProductData parsedProductData, String url) {
        ProductEntity productEntity = productRepository.findByUrl(url).orElse(null);
        if (productEntity == null) {
            productEntity = new ProductEntity();
            productEntity.setUrl(url);
        }

        productEntity.setTitle(parsedProductData.getTitle());
        productEntity.setPrice(parsedProductData.getPrice());
        productEntity.setDescription(parsedProductData.getDescription().substring(0, 3000));
        productEntity.setMerchantName(parsedProductData.getMerchantName());
        productEntity.setCrawledAt(parsedProductData.getCrawledAt());

        productRepository.save(productEntity);
    }

    public void setProductStatus(String url, ProductStatus status) {
        ProductEntity productEntity = productRepository.findByUrl(url).orElse(null);
        if (productEntity != null) {
            productEntity.setProductStatus(status);
            productRepository.save(productEntity);
        }
    }

}
