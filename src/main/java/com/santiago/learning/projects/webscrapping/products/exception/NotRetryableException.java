package com.santiago.learning.projects.webscrapping.products.exception;

public class NotRetryableException extends RuntimeException {

    public NotRetryableException(Throwable cause) {
        super(cause);
    }
    
}
