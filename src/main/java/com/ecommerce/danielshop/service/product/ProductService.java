package com.ecommerce.danielshop.service.product;

import com.ecommerce.danielshop.dto.image.ImageDto;
import com.ecommerce.danielshop.dto.product.ProductDto;
import com.ecommerce.danielshop.exception.product.ProductNotFoundException;
import com.ecommerce.danielshop.model.Category;
import com.ecommerce.danielshop.model.product.Product;
import com.ecommerce.danielshop.model.product.ProductPage;
import com.ecommerce.danielshop.model.product.ProductPageCriteria;
import com.ecommerce.danielshop.repository.CategoryRepository;
import com.ecommerce.danielshop.repository.ProductCriteriaRepository;
import com.ecommerce.danielshop.repository.ProductRepository;
import com.ecommerce.danielshop.request.product.AddProductRequest;
import com.ecommerce.danielshop.request.product.UpdateProductRequest;
import com.ecommerce.danielshop.response.PaginationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final ProductCriteriaRepository productCriteriaRepository;

    private final CategoryRepository categoryRepository;


    @Override
    public Product addProduct(AddProductRequest product) {
        Category category = Optional.ofNullable(
                categoryRepository.findByName(product.getCategory().getName())
        ).orElseGet(() -> {
            Category newCategory = new Category(product.getCategory().getName());
            return categoryRepository.save(newCategory);
        });
        product.setCategory(category);
        return productRepository.save(
                createProduct(product)
        );
    }

    private Product createProduct(AddProductRequest request) {
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getDescription(),
                request.getPrice(),
                request.getInventory(),
                request.getCategory()
        );
    }


    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found!"));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id)
                .ifPresentOrElse(
                        productRepository::delete,
                        () -> {
                            throw new ProductNotFoundException("Product not found!");
                        });
    }

    @Override
    public Product updateProduct(UpdateProductRequest product, Long productId) {
        return productRepository.findById(productId)
                .map(existingProduct -> updateExistingProduct(existingProduct, product))
                .map(productRepository::save)
                .orElseThrow(() -> new ProductNotFoundException("Product not found!"));
    }

    private Product updateExistingProduct(Product existingProduct, UpdateProductRequest request) {
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setDescription(request.getDescription());
        existingProduct.setInventory(request.getInventory());

        Category category = categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory(request.getCategory());

        return existingProduct;
    }

    @Override
    public PaginationResponse<List<ProductDto>> getAllProducts(
            ProductPage productPage,
            ProductPageCriteria productPageCriteria
    ) {
        Page<Product> productPages = productCriteriaRepository.findAllWithFilters(productPage, productPageCriteria);
        return new PaginationResponse<>(
                productPages.getContent().stream().map(this::convertToDto).toList(),
                productPages.getTotalPages()
        );
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String branch, String name) {
        return productRepository.findByBrandAndName(branch, name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }

    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products) {
        return products.stream().map(this::convertToDto).toList();
    }

    @Override
    public ProductDto convertToDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getBrand(),
                product.getInventory(),
                product.getDescription(),
                product.getCategory(),
                List.of()
//                product.getImages().stream().map(image ->
//                    new ImageDto(
//                        image.getId(),
//                        image.getFileName(),
//                        image.getFileType(),
//                        image.getUrl()
//                    )
//                ).toList()
        );
    }
}
