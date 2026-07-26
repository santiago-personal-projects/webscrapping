package com.santiago.learning.projects.webscrapping.products.handler;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import com.santiago.learning.projects.webscrapping.products.dto.request.ProductAndPriceRequestDTO;
import com.santiago.learning.projects.webscrapping.products.exception.NotRetryableException;
import com.santiago.learning.projects.webscrapping.products.service.ScrapperService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProductCreatedEventHandler {
    
    private final ScrapperService scrapperService;

    @Transactional
    @KafkaListener(topics = "${webscrapping.kafka.topic}")
    public void handle(@Payload ProductAndPriceRequestDTO event) {
        log.info("Received a new event: {} with product ID: {}", event.getName(), event.getId());
        try {
            scrapperService.saveProductAndPrice(event);
        } catch (DataIntegrityViolationException e) {
            throw new NotRetryableException(e);
        }
    }
}
