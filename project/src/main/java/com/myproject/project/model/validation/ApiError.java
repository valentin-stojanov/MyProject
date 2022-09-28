package com.myproject.project.model.validation;



import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class ApiError {

    private final HttpStatus status;
    private List<String> fieldsWithErrors = new ArrayList<>();

    public ApiError(HttpStatus status) {
        this.status = status;
    }

    public void addFieldWithError(String error) {

    }

    public HttpStatus getStatus() {
        return status;
    }

    public List<String> getFieldsWithErrors() {
        return fieldsWithErrors;
    }

    public ApiError setFieldsWithErrors(List<String> fieldsWithErrors) {
        this.fieldsWithErrors = fieldsWithErrors;
        return this;
    }
}
