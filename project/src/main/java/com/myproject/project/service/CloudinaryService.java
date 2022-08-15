package com.myproject.project.service;

import com.cloudinary.Cloudinary;
import com.myproject.project.model.dto.CloudinaryImageDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    private static final String TEMP_FILE = "temp-file";
    private static final String URL = "url";
    private static final String PUBLIC_ID = "public_id";
    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public CloudinaryImageDto upload(MultipartFile multipartFile) throws IOException {

        File tempFile = File.createTempFile(TEMP_FILE, multipartFile.getOriginalFilename());
        multipartFile.transferTo(tempFile);

        CloudinaryImageDto result;
        try {
            @SuppressWarnings("unchecked")
            Map<String, String> uploadResult = this.cloudinary
                    .uploader()
                    .upload(tempFile, Map.of());

            String url = uploadResult.getOrDefault(URL,
                    "https://previews.123rf.com/images/olejio/olejio1802/olejio180200041/95585020-funny-design-404-page-not-found-vector-illustration-geek-with-metal-detector-searching-the-big-data.jpg");
            String publicId = uploadResult.getOrDefault(PUBLIC_ID, "no public id");
            result = new CloudinaryImageDto()
                    .setPublicId(publicId)
                    .setUrl(url);
        } finally {
            tempFile.delete();
        }

        return result;
    }

    public boolean delete(String publicId) {
        try {
            this.cloudinary.uploader().destroy(publicId, Map.of());
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
