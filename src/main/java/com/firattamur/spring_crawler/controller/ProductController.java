package com.firattamur.spring_crawler.controller;

import com.firattamur.spring_crawler.domain.dto.ProductCreateDto;
import com.firattamur.spring_crawler.domain.dto.ProductResponseDto;
import com.firattamur.spring_crawler.domain.entity.ProductEntity;
import com.firattamur.spring_crawler.domain.entity.CrawlingStatus;
import com.firattamur.spring_crawler.mapper.Mapper;
import com.firattamur.spring_crawler.mapper.impl.ProductCreateDtoMapperImpl;
import com.firattamur.spring_crawler.service.JobQueueService;
import com.firattamur.spring_crawler.service.ProductService;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Slf4j
@RestController
public class ProductController {

    private final ProductService productService;

    private final Mapper<ProductEntity, ProductCreateDto> productCreateDtoMapper;
    private final Mapper<ProductEntity, ProductResponseDto> productResponseDtoMapper;

    private final JobQueueService jobQueueService;

    @Autowired
    public ProductController(
            ProductService productService, ProductCreateDtoMapperImpl productCreateDtoMapper, Mapper<ProductEntity, ProductResponseDto> productResponseDtoMapper, JobQueueService jobQueueService) {
        this.productService = productService;
        this.productCreateDtoMapper = productCreateDtoMapper;
        this.productResponseDtoMapper = productResponseDtoMapper;
        this.jobQueueService = jobQueueService;
    }
    
    @PostMapping("/submit")
    public ProductResponseDto postMethodName(@RequestBody ProductCreateDto dto) {
        ProductEntity productEntity = productService.findByUrl(dto.getUrl()).orElse(null);
        if (productEntity != null) {

            if (productEntity.getCrawlingStatus() != CrawlingStatus.DONE) {
                jobQueueService.enqueueJob(productEntity.getUrl());
                log.info("Product submitted to queue with url: {}", productEntity.getUrl());

                productEntity.setCrawlingStatus(CrawlingStatus.IN_PROGRESS);
                productService.save(productEntity);
            }

            return productResponseDtoMapper.mapTo(productEntity);
        }

        ProductEntity newProductEntity = productCreateDtoMapper.mapFrom(dto);
        ProductEntity savedProductEntity = productService.save(newProductEntity);
        log.info("Product saved with id: {}", savedProductEntity.getId());

        jobQueueService.enqueueJob(savedProductEntity.getUrl());
        log.info("Product submitted to queue with url: {}", savedProductEntity.getUrl());

        return productResponseDtoMapper.mapTo(savedProductEntity);
    }

    @GetMapping("/product")
    public ProductResponseDto getMethodName(@RequestParam(name="id") Long id) {
        Optional<ProductEntity> productEntity = productService.findById(id);
        if (productEntity.isEmpty()) {
            throw new ConstraintViolationException("Product not found", null);
        }

        return productResponseDtoMapper.mapTo(productEntity.get());
    }

    @GetMapping("/products")
    public Iterable<ProductResponseDto> getMethodName() {
        return List.of(productService.findAll().stream()
                .map(productResponseDtoMapper::mapTo)
                .toArray(ProductResponseDto[]::new));
    }

}
