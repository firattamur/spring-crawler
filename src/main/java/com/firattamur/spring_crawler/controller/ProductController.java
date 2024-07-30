package com.firattamur.spring_crawler.controller;

import org.springframework.web.bind.annotation.RestController;

import com.firattamur.spring_crawler.domain.dto.ProductInpDto;
import com.firattamur.spring_crawler.domain.dto.ProductOutDto;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

import com.firattamur.spring_crawler.service.ProductService;



@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    
    @PostMapping("/submit")
    public ProductOutDto postMethodName(@RequestBody ProductInpDto dto) {
        return null;
    }

    @GetMapping("/product")
    public ProductOutDto getMethodName(@RequestParam(name="id") String id) {
        return null;
    }

}
