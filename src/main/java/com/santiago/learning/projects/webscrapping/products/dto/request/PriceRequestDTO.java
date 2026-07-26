package com.santiago.learning.projects.webscrapping.products.dto.request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceRequestDTO {
    private String productId;
    private String price;
}
