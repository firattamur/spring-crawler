package com.firattamur.spring_crawler.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.firattamur.spring_crawler.domain.dto.ProductInpDto;
import com.firattamur.spring_crawler.domain.dto.ProductOutDto;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

import com.firattamur.spring_crawler.service.ProductService;



@Slf4j
@RestController
public class ProductController {

    private final ProductService productService;

    @Autowired
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
