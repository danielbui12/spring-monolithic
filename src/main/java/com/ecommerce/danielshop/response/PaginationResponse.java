package com.ecommerce.danielshop.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PaginationResponse<T> {
    private T data;
    private int total;
}

