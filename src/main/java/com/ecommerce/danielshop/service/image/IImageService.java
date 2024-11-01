package com.ecommerce.danielshop.service.image;

import com.ecommerce.danielshop.dto.image.ImageDto;
import com.ecommerce.danielshop.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImages(Long productId, List<MultipartFile> files);
    void updateImage(MultipartFile file,  Long imageId);
}