package com.ecommerce.danielshop.request.product;

import com.ecommerce.danielshop.model.Category;
import com.ecommerce.danielshop.model.Image;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class AddProductRequest {
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
}
