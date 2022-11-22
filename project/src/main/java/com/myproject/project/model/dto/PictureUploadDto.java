package com.myproject.project.model.dto;

import org.springframework.web.multipart.MultipartFile;

public class PictureUploadDto {
    private String title;

    private MultipartFile picture;

    public PictureUploadDto() {
    }

    public String getTitle() {
        return title;
    }

    public PictureUploadDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public MultipartFile getPicture() {
        return picture;
    }

    public PictureUploadDto setPicture(MultipartFile picture) {
        this.picture = picture;
        return this;
    }
}
