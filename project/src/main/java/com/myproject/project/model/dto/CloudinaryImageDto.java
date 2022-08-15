package com.myproject.project.model.dto;

public class CloudinaryImageDto {
    private String url;
    private String publicId;

    public CloudinaryImageDto() {
    }

    public String getUrl() {
        return url;
    }

    public CloudinaryImageDto setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getPublicId() {
        return publicId;
    }

    public CloudinaryImageDto setPublicId(String publicId) {
        this.publicId = publicId;
        return this;
    }
}
