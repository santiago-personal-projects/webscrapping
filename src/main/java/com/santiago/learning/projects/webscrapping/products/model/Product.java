package com.santiago.learning.projects.webscrapping.products.model;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@ToString
@Table(schema = "webscrapping", name = "product")
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @Column(name = "product_id")
    private String id;
    @Column(name = "product_name", nullable = false)
    private String name;
    @Column(nullable = false)
    private String url;
    @OneToMany(mappedBy = "product")
    private Set<Price> price;
}