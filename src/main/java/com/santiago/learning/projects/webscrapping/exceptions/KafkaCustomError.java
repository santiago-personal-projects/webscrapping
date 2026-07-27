package com.santiago.learning.projects.webscrapping.exceptions;


import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KafkaCustomError extends RuntimeException {
    private String message;
    private Instant timestamp;
}
