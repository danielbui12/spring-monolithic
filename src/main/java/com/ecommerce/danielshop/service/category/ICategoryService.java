package com.ecommerce.danielshop.service.category;

import com.ecommerce.danielshop.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.Optional;

public interface ICategoryService {
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    Page<Category> getAllCategories(int offset, int limit, Optional<String> sortBy, Optional<Sort.Direction> sortDir);
    Category addCategory(Category category);
    Category updateCategory(Category category, Long id);
    void deleteCategoryById(Long id);
}
