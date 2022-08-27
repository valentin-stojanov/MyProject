package com.myproject.project.model.dto;

import com.myproject.project.model.enums.RouteCategoryEnum;
import com.myproject.project.model.enums.LevelEnum;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

public class RouteAddDto {
    @Size(min = 3, max = 30, message = "Route name must be between 3 and 30 characters!")
    private String name;
    private String description;
    private MultipartFile gpxCoordinates;
    @NotNull
    private LevelEnum level;
    private String videoUrl;

    //TODO: add custom message for validation! (at least one category mus be selected)
    @Size(min = 1)
    private List<RouteCategoryEnum> categories;

    public String getName() {
        return name;
    }

    public RouteAddDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public RouteAddDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public MultipartFile getGpxCoordinates() {
        return gpxCoordinates;
    }

    public RouteAddDto setGpxCoordinates(MultipartFile gpxCoordinates) {
        this.gpxCoordinates = gpxCoordinates;
        return this;
    }

    public LevelEnum getLevel() {
        return level;
    }

    public RouteAddDto setLevel(LevelEnum level) {
        this.level = level;
        return this;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public RouteAddDto setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        return this;
    }

    public List<RouteCategoryEnum> getCategories() {
        return categories;
    }

    public RouteAddDto setCategories(List<RouteCategoryEnum> categories) {
        this.categories = categories;
        return this;
    }
}
