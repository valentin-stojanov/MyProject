package com.myproject.project.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class NewCommentDto {
    @NotBlank
    @Size(min = 10)
    private String message;

    private String creator;

    private Long routeId;

    public String getMessage() {
        return message;
    }

    public NewCommentDto setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getCreator() {
        return creator;
    }

    public NewCommentDto setCreator(String creator) {
        this.creator = creator;
        return this;
    }

    public Long getRouteId() {
        return routeId;
    }

    public NewCommentDto setRouteId(Long routeId) {
        this.routeId = routeId;
        return this;
    }
}
