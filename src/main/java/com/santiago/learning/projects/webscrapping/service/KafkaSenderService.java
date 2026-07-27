package com.santiago.learning.projects.webscrapping.service;

import java.time.Instant;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.santiago.learning.projects.webscrapping.core.dto.request.ProductAndPriceRequestDTO;
import com.santiago.learning.projects.webscrapping.exceptions.KafkaCustomError;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaSenderService {
    
    @Value("${webscrapping.kafka.topic}")
    private String topic;

    // Messages are key value pair
    private final KafkaTemplate<String, ProductAndPriceRequestDTO> kafkaTemplate;

    
    public void createProduct(ProductAndPriceRequestDTO request) {

        log.info("######## Before sending ########");

        CompletableFuture<SendResult<String, ProductAndPriceRequestDTO>> future = kafkaTemplate.send(topic, request);
        future.whenComplete((result, exception) -> {
            if (exception != null) {
                String errorMessage = exception.getMessage();
                log.error("######## Failed to send message: {} ########", errorMessage);
                throw new KafkaCustomError(errorMessage, Instant.now());
            } else {
                log.info("######## Message sent successfully: {} ########", result.getRecordMetadata());
                log.info("Partition: {}", result.getRecordMetadata().partition());
                log.info("Topic: {}", result.getRecordMetadata().topic());
                log.info("Offset: {}", result.getRecordMetadata().offset());

            }
        });
    }

}
