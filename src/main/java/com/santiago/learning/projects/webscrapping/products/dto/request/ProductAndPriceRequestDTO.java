package com.santiago.learning.projects.webscrapping.products.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductAndPriceRequestDTO {
    @NotEmpty
    private String id;
    @NotBlank
    private String name;
    @NotBlank
    private String url;
    @NotBlank
    private String price;
}
