package com.ecommerce.danielshop.dto.image;

public record ImageDto(
    Long id,
    String fileName,
    String fileType,
    String url
) { }