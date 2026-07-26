package com.santiago.learning.projects.webscrapping.products.exception;

public class RetryableException extends RuntimeException {

    public RetryableException(Throwable cause) {
        super(cause);
    }
    
}
