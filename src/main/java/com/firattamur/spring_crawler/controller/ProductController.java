package com.firattamur.spring_crawler.controller;

import com.firattamur.spring_crawler.domain.entity.ProductEntity;
import com.firattamur.spring_crawler.mapper.Mapper;
import com.firattamur.spring_crawler.mapper.impl.ProductCreateDtoMapperImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.firattamur.spring_crawler.domain.dto.ProductCreateDto;
import com.firattamur.spring_crawler.domain.dto.ProductResponseDto;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

import com.firattamur.spring_crawler.service.ProductService;



@Slf4j
@RestController
public class ProductController {

    private final ProductService productService;

    private final Mapper<ProductEntity, ProductCreateDto> productCreateDtoMapper;
    private final Mapper<ProductEntity, ProductResponseDto> productResponseDtoMapper;

    @Autowired
    public ProductController(ProductService productService, ProductCreateDtoMapperImpl productCreateDtoMapper, Mapper<ProductEntity, ProductResponseDto> productResponseDtoMapper) {
        this.productService = productService;
        this.productCreateDtoMapper = productCreateDtoMapper;
        this.productResponseDtoMapper = productResponseDtoMapper;
    }
    
    @PostMapping("/submit")
    public ProductResponseDto postMethodName(@RequestBody ProductCreateDto dto) {
        ProductEntity productEntity = productCreateDtoMapper.mapFrom(dto);
        ProductEntity savedProductEntity = productService.save(productEntity);
        return productResponseDtoMapper.mapTo(savedProductEntity);
    }

    @GetMapping("/product")
    public ProductResponseDto getMethodName(@RequestParam(name="id") Long id) {
        ProductEntity productEntity = productService.findById(id);
        return productResponseDtoMapper.mapTo(productEntity);
    }

}
