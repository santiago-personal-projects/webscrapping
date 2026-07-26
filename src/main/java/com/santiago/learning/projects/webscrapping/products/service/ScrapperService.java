package com.santiago.learning.projects.webscrapping.products.service;

import com.santiago.learning.projects.webscrapping.products.dto.request.PriceRequestDTO;
import com.santiago.learning.projects.webscrapping.products.dto.request.ProductAndPriceRequestDTO;
import com.santiago.learning.projects.webscrapping.products.dto.request.ProductRequestDTO;

public interface ScrapperService {
    void saveProduct(ProductRequestDTO request);
    void saveProductAndPrice(ProductAndPriceRequestDTO request);
    void savePrice(PriceRequestDTO entity);
}
