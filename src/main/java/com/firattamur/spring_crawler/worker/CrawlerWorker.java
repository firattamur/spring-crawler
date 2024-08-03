package com.firattamur.spring_crawler.worker;

import com.firattamur.spring_crawler.domain.entity.CrawlingStatus;
import com.firattamur.spring_crawler.domain.model.ParsedProductData;
import com.firattamur.spring_crawler.service.JobQueueService;
import com.firattamur.spring_crawler.service.ProductService;
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
    private final ProductService productService;
    private final WebScraperService webScraperService;
    private boolean running = true;

    public CrawlerWorker(int workerId, JobQueueService jobQueueService,
                         ProductService productService,
                         WebScraperService webScraperService) {
        this.workerId = workerId;
        this.jobQueueService = jobQueueService;
        this.productService = productService;
        this.webScraperService = webScraperService;
    }

    @Override
    public void run() {

        while (running) {
            try {

                String url = jobQueueService.dequeueJob(30, TimeUnit.SECONDS);
                if (url != null) {
                    productService.setCrawlingStatus(url, CrawlingStatus.IN_PROGRESS);
                    processJob(url);
                }

            } catch (Exception e) {
                log.error("Error in worker loop");
            }
        }

    }

    private void processJob(String url) {
        log.info("Worker Thread: {}, Scraping Url: {}", Thread.currentThread().getName(), url);

        try {

            Optional<ParsedProductData> product = webScraperService.scrape(url);

            if (product.isEmpty()) {
                productService.setCrawlingStatus(url, CrawlingStatus.FAILED);
                return;
            }

            productService.update(product.get(), url);
            productService.setCrawlingStatus(url, CrawlingStatus.DONE);

        } catch (Exception e) {

            log.error("Error while scraping: {}, error: {}", url, e.getMessage());
            productService.setCrawlingStatus(url, CrawlingStatus.FAILED);

        }

    }

    public void stop() {
        this.running = false;
    }

}
