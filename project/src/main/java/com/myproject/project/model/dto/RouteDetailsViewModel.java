package com.myproject.project.model.dto;

import com.myproject.project.model.entity.PictureEntity;
import com.myproject.project.model.enums.LevelEnum;

import java.util.List;

public class RouteDetailsViewModel {
    private Long id;
    private String gpxCoordinates;
    private String description;
    private LevelEnum level;
    private String name;
    private String videoUrl;
    private List<PictureEntity> pictures;
    private String authorFullName;

    private String authorEmail;

    public RouteDetailsViewModel() {
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public RouteDetailsViewModel setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
        return this;
    }

    public Long getId() {
        return id;
    }

    public String getAuthorFullName() {
        return authorFullName;
    }

    public RouteDetailsViewModel setAuthorFullName(String authorFullName) {
        this.authorFullName = authorFullName;
        return this;
    }

    public RouteDetailsViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getGpxCoordinates() {
        return gpxCoordinates;
    }

    public RouteDetailsViewModel setGpxCoordinates(String gpxCoordinates) {
        this.gpxCoordinates = gpxCoordinates;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public RouteDetailsViewModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public LevelEnum getLevel() {
        return level;
    }

    public RouteDetailsViewModel setLevel(LevelEnum level) {
        this.level = level;
        return this;
    }

    public String getName() {
        return name;
    }

    public RouteDetailsViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public RouteDetailsViewModel setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        return this;
    }

    public List<PictureEntity> getPictures() {
        return pictures;
    }

    public RouteDetailsViewModel setPictures(List<PictureEntity> pictures) {
        this.pictures = pictures;
        return this;
    }
}
