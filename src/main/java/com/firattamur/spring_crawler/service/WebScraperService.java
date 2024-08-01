package com.firattamur.spring_crawler.service;

import com.firattamur.spring_crawler.domain.entity.ProductEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Slf4j
public class WebScraperService {

    public Optional<ProductEntity> scrape(String url) {
        log.info("Scraping: {}", url);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

}
