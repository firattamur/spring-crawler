package com.firattamur.spring_crawler.worker;

import com.firattamur.spring_crawler.domain.entity.ProductEntity;
import com.firattamur.spring_crawler.repository.ProductRepository;
import com.firattamur.spring_crawler.service.JobQueueService;
import com.firattamur.spring_crawler.service.WebScraperService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Getter
public class CrawlerWorker implements Runnable {

    private final int workerId;
    private final JobQueueService jobQueueService;
    private final ProductRepository productRepository;
    private final WebScraperService webScraperService;
    private boolean running = true;

    public CrawlerWorker(int workerId, JobQueueService jobQueueService,
                         ProductRepository productRepository,
                         WebScraperService webScraperService) {
        this.workerId = workerId;
        this.jobQueueService = jobQueueService;
        this.productRepository = productRepository;
        this.webScraperService = webScraperService;
    }

    @Override
    public void run() {

        while (running) {
            try {

                String url = jobQueueService.dequeueJob(30, TimeUnit.SECONDS);
                if (url != null) {
                    processJob(url);
                }

            } catch (Exception e) {
                log.error("Error in worker loop", e);
            }
        }

    }

    private void processJob(String url) {

        try {
            log.info("Worker Thread: {}, Scraping Url: {}", Thread.currentThread().getName(), url);

            Optional<ProductEntity> product = webScraperService.scrape(url);
            product.ifPresent(productRepository::save);

        } catch (Exception e) {
            log.error("Error while scraping: {}", url, e);
        }

    }

    public void stop() {
        this.running = false;
    }

}
