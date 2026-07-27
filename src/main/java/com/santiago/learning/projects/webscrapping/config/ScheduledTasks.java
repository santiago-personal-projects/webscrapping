package com.santiago.learning.projects.webscrapping.config;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.santiago.learning.projects.webscrapping.service.WebScrappingService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {

    private final WebScrappingService service;
    
    @Scheduled(cron = "${webscrapping.scheduler}") 
    public void webScrapping() {
        service.getPhoneScrapper();
    }
}