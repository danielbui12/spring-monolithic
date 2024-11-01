package com.ecommerce.danielshop.service.product;

import com.ecommerce.danielshop.dto.product.ProductDto;
import com.ecommerce.danielshop.model.product.Product;
import com.ecommerce.danielshop.model.product.ProductPage;
import com.ecommerce.danielshop.model.product.ProductPageCriteria;
import com.ecommerce.danielshop.request.product.AddProductRequest;
import com.ecommerce.danielshop.request.product.UpdateProductRequest;
import com.ecommerce.danielshop.response.PaginationResponse;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest product);
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(UpdateProductRequest product, Long productId);
    PaginationResponse<List<ProductDto>> getAllProducts(ProductPage productPage,
                                                  ProductPageCriteria productPageCriteria);
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String branch, String name);
    Long countProductsByBrandAndName(String brand, String name);

    List<ProductDto> getConvertedProducts(List<Product> products);

    ProductDto convertToDto(Product product);
}
