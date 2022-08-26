package com.myproject.project.model.entity;

import com.myproject.project.model.enums.RouteCategoryEnum;

import javax.persistence.*;

@Entity
@Table(name = "categories")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RouteCategoryEnum name;

    public CategoryEntity() {
    }

    public Long getId() {
        return id;
    }

    public CategoryEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public RouteCategoryEnum getName() {
        return name;
    }

    public CategoryEntity setName(RouteCategoryEnum name) {
        this.name = name;
        return this;
    }

}
