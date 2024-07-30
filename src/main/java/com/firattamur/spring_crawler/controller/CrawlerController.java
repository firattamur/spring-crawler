package com.firattamur.spring_crawler.controller;

import org.springframework.web.bind.annotation.RestController;

import com.firattamur.spring_crawler.domain.dto.CrawlerInputDto;
import com.firattamur.spring_crawler.domain.dto.CrawlerResponseDto;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
public class CrawlerController {
    
    @PostMapping("/submit")
    public CrawlerResponseDto postMethodName(@RequestBody CrawlerInputDto dto) {
        return null;
    }
    

}
