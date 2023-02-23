package com.myproject.project.model.mapper;

import com.myproject.project.model.dto.RouteAddDto;
import com.myproject.project.model.entity.CategoryEntity;
import com.myproject.project.model.entity.RouteEntity;
import com.myproject.project.model.entity.UserEntity;
import com.myproject.project.model.enums.RouteCategoryEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RouteMapper {
    @Mapping(target = "categories", qualifiedByName = "mapCategories")
    @Mapping(target = "gpxCoordinates", qualifiedByName = "mapGpxCoordinates")
    @Mapping(target = "author", source = "author")
    @Mapping(target = "id", ignore = true)
    RouteEntity toRouteEntity(RouteAddDto routeAddDto, UserEntity author);

    @Named("mapCategories")
    default List<CategoryEntity> mapCategories(List<RouteCategoryEnum> categories) {
        return categories.stream().map(c -> new CategoryEntity().setName(c)).collect(Collectors.toList());
    }

    @Named("mapGpxCoordinates")
    default String mapGpxCoordinates(MultipartFile gpxCoordinates) throws IOException {
        return new String(gpxCoordinates.getBytes());
    }

}
