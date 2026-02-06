package org.example.infinite_scroll_project_back.modules.user.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;

    // Uploads a file to Cloudinary with a specific public ID (e.g., "profile_username")
    public String uploadFile(MultipartFile file, String publicId) {
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                    "public_id", publicId,   // Use the specific name we pass (e.g., "profile_username")
                    "overwrite", true,       // Explicitly tell Cloudinary to replace the old one
                    "invalidate", true,      // Tell CDN to flush the cached version
                    "resource_type", "image"
            ));
            return uploadResult.get("secure_url").toString();
        } catch (IOException e) {
            throw new RuntimeException("Image upload failed", e);
        }
    }
}