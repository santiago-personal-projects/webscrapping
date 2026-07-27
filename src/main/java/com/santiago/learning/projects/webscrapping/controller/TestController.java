package com.santiago.learning.projects.webscrapping.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.santiago.learning.projects.webscrapping.service.WebScrappingService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final WebScrappingService service;

    @GetMapping("/test-kafka")
    public String getMethodName() {
        service.getPhoneScrapper();
        return new String();
    }
    
}
