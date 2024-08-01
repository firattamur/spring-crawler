package com.firattamur.spring_crawler.service;

import com.firattamur.spring_crawler.domain.entity.ProductEntity;
import com.firattamur.spring_crawler.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductEntity save(ProductEntity productEntity) {
        return productRepository.save(productEntity);
    }

    public ProductEntity findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

}
