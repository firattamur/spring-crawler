package com.firattamur.spring_crawler.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.firattamur.spring_crawler.domain.entity.ProductEntity;
import com.firattamur.spring_crawler.domain.entity.ProductStatus;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;


@Service
@Slf4j
public class WebScraperService {

    private final Map<String, String> headersMap = Map.ofEntries(
            Map.entry("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8"),
            Map.entry("accept-language", "en-US,en;q=0.7"),
            Map.entry("cache-control", "max-age=0"),
            Map.entry("priority", "u=0, i"),
            Map.entry("sec-fetch-dest", "document"),
            Map.entry("sec-fetch-mode", "navigate"),
            Map.entry("sec-fetch-site", "same-origin"),
            Map.entry("sec-fetch-user", "?1"),
            Map.entry("sec-gpc", "1"),
            Map.entry("upgrade-insecure-requests", "1"),
            Map.entry("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36")
    );

    private final ObjectMapper objectMapper;

    @Value("${product.info.xpath}")
    private String PRODUCT_INFO_XPATH;

    @Value("${product.desc.xpath}")
    private String PRODUCT_DESC_XPATH;

    @Autowired
    public WebScraperService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Optional<ProductEntity> scrape(String url) {
        try {

            Document doc = Jsoup.connect(url)
                    .headers(headersMap)
                    .ignoreContentType(true)
                    .get();

            Map<String, Object> productInfoMap = parseProductInfo(doc);
            Map<String, Object> productDescMap = parseProductDesc(doc);

            return Optional.of(ProductEntity.builder()
                    .price(getNestedValue(productInfoMap, "@graph", "offers", "price")
                            .map(Object::toString)
                            .map(Double::parseDouble)
                            .orElse(null))
                    .title(getNestedValue(productInfoMap, "name")
                            .map(Object::toString)
                            .orElse(null))
                    .merchantName(getNestedValue(productInfoMap, "@graph", "offers", "seller", "name")
                            .map(Object::toString)
                            .orElse(null))
                    .description(getNestedValue(productDescMap, "product", "description")
                            .map(Object::toString)
                            .orElse(null))
                    .productStatus(ProductStatus.DONE)
                    .build());

        } catch (Exception e) {
            log.error("Error while scraping: {}", url, e);
        }

        return Optional.empty();
    }

    private Map<String, Object> parseProductInfo(Document document) {
        Elements productJson = document.selectXpath(PRODUCT_INFO_XPATH);
        String productJsonStr = Objects.requireNonNull(productJson.first()).data();

        return parseJson(productJsonStr);
    }

    private Map<String, Object> parseProductDesc(Document document) {
        Elements descriptionJson = document.selectXpath(PRODUCT_DESC_XPATH);
        String descriptionJsonStr = Objects.requireNonNull(descriptionJson.first()).data().split("productModel = ")[1];

        return parseJson(descriptionJsonStr);
    }

    private Map<String, Object> parseJson(String jsonStr) {
        try {
            return this.objectMapper.readValue(jsonStr, new TypeReference<>() {});
        }catch (Exception e) {
            log.error("Error while parsing JSON: {}", jsonStr, e);
        }

        return Map.of();
    }

    private Optional<Object> getNestedValue(Map<String, Object> map, String... keys) {
        Optional<Object> current = Optional.ofNullable(map);

        for (String key : keys) {
            current = current.map(obj -> {
                if (obj instanceof Map) {
                    return ((Map<?, ?>) obj).get(key);
                }
                return null;
            });
        }

        return (Optional<Object>) current;
    }

}
