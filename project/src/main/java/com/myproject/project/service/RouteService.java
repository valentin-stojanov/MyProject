package com.myproject.project.service;

import com.myproject.project.model.dto.RouteAddDto;
import com.myproject.project.model.entity.CategoryEntity;
import com.myproject.project.model.entity.RouteEntity;
import com.myproject.project.model.entity.UserEntity;
import com.myproject.project.repository.RouteRepository;
import com.myproject.project.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.stream.Collectors;

@Service
public class RouteService {
    private final RouteRepository routeRepository;
    private final UserRepository userRepository;

    public RouteService(RouteRepository routeRepository,
                        UserRepository userRepository) {
        this.routeRepository = routeRepository;
        this.userRepository = userRepository;
    }

    public void addNewRoute(RouteAddDto routeAddDto, UserDetails userDetails) throws IOException {

        UserEntity author = this.userRepository
                .findByEmail(userDetails.getUsername())
                .orElseThrow();

        RouteEntity newRoute = new RouteEntity()
                .setName(routeAddDto.getName())
                .setDescription(routeAddDto.getDescription())
                .setGpxCoordinates(new String(routeAddDto.getGpxCoordinates().getBytes()))
                .setLevel(routeAddDto.getLevel())
                .setVideoUrl(routeAddDto.getVideoUrl())
                .setCategories(routeAddDto.getCategories().stream().map(
                        c -> new CategoryEntity()
                                .setName(c))
                        .collect(Collectors.toList()))
                .setAuthor(author);

        this.routeRepository.save(newRoute);
    }
}

