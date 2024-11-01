package com.ecommerce.danielshop.dto.product;

import com.ecommerce.danielshop.dto.image.ImageDto;
import com.ecommerce.danielshop.model.Category;

import java.math.BigDecimal;
import java.util.List;

public record ProductDto(
    Long id,
    String name,
    BigDecimal price,
    String brand,
    int inventory,
    String description,
    Category category,
    List<ImageDto> images
) { }
