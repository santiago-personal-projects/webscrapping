package com.santiago.learning.projects.webscrapping.products.service;

import org.springframework.stereotype.Service;

import com.santiago.learning.projects.webscrapping.products.enums.ErrorCodeEnum;
import com.santiago.learning.projects.webscrapping.products.exception.ProductsException;
import com.santiago.learning.projects.webscrapping.products.model.Price;
import com.santiago.learning.projects.webscrapping.products.model.Product;
import com.santiago.learning.projects.webscrapping.products.dto.request.PriceRequestDTO;
import com.santiago.learning.projects.webscrapping.products.dto.request.ProductAndPriceRequestDTO;
import com.santiago.learning.projects.webscrapping.products.dto.request.ProductRequestDTO;
import com.santiago.learning.projects.webscrapping.products.repository.PriceRepository;
import com.santiago.learning.projects.webscrapping.products.repository.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScraperServiceImpl implements ScrapperService {

    private final ProductRepository prodRepo;
    private final PriceRepository priceRepo;
    

    @Override
    @Transactional
    public void saveProduct(ProductRequestDTO request) {
        String id = request.getId();
        prodRepo.findById(id)
                .ifPresentOrElse(
                        product -> {log.warn("Product with id {}, has been previously created", id);},
                        () -> prodRepo.save(new Product(id, request.getName(), request.getUrl(), null)));

    }
    @Override
    @Transactional
    public void savePrice(PriceRequestDTO entity) {
        Product product = prodRepo.findById(entity.getProductId()).orElseThrow(() -> new ProductsException(ErrorCodeEnum.PRODUCT_NOT_FOUND));
        priceRepo.save(new Price(entity.getPrice(), product));
  
    }

    @Override
    @Transactional
    public void saveProductAndPrice(ProductAndPriceRequestDTO request) {
        String id = request.getId();

        Product product = prodRepo.findById(id)
            .orElseGet(() -> prodRepo.save(new Product(id, request.getName(), request.getUrl(), null)));
        priceRepo.save(new Price(request.getPrice(), product));
    }

}