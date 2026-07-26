package com.santiago.learning.projects.webscrapping.products.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.santiago.learning.projects.webscrapping.products.model.Product;

public interface ProductRepository extends JpaRepository<Product, String> {

}
