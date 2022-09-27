package com.myproject.project.model.entity;

import com.myproject.project.model.enums.LevelEnum;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "routes")
public class RouteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Lob
    private String gpxCoordinates;

    @Enumerated(EnumType.STRING)
    private LevelEnum level;

    private String videoUrl;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<CategoryEntity> categories;

    @ManyToOne
    private UserEntity author;

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL)
    private List<CommentEntity> comments;

    @OneToMany(mappedBy = "route", fetch = FetchType.LAZY)
    private List<PictureEntity> pictures;


    public RouteEntity() {
        this.comments = new ArrayList<>();
        this.categories = new ArrayList<>();
        this.pictures = new ArrayList<>();
    }


    public Long getId() {
        return id;
    }

    public RouteEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getGpxCoordinates() {
        return gpxCoordinates;
    }

    public RouteEntity setGpxCoordinates(String gpxCoordinates) {
        this.gpxCoordinates = gpxCoordinates;
        return this;
    }

    public LevelEnum getLevel() {
        return level;
    }

    public RouteEntity setLevel(LevelEnum level) {
        this.level = level;
        return this;
    }

    public String getName() {
        return name;
    }

    public RouteEntity setName(String name) {
        this.name = name;
        return this;
    }

    public UserEntity getAuthor() {
        return author;
    }

    public RouteEntity setAuthor(UserEntity author) {
        this.author = author;
        return this;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public RouteEntity setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        return this;
    }

    public List<CommentEntity> getComments() {
        return comments;
    }

    public RouteEntity setComments(List<CommentEntity> comments) {
        this.comments = comments;
        return this;
    }

    public List<PictureEntity> getPictures() {
        return pictures;
    }

    public RouteEntity setPictures(List<PictureEntity> pictures) {
        this.pictures = pictures;
        return this;
    }

    public List<CategoryEntity> getCategories() {
        return categories;
    }

    public RouteEntity setCategories(List<CategoryEntity> categories) {
        this.categories = categories;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public RouteEntity setDescription(String description) {
        this.description = description;
        return this;
    }
}