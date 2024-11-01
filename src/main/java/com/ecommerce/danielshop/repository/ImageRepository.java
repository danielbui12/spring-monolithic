package com.ecommerce.danielshop.repository;

import com.ecommerce.danielshop.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
