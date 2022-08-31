package com.myproject.project.service;

import com.myproject.project.model.dto.RouteAddDto;
import com.myproject.project.model.dto.RouteDetailsViewModel;
import com.myproject.project.model.dto.RouteViewModel;
import com.myproject.project.model.entity.CategoryEntity;
import com.myproject.project.model.entity.RouteEntity;
import com.myproject.project.model.entity.UserEntity;
import com.myproject.project.repository.RouteRepository;
import com.myproject.project.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RouteService {
    private static String DEFAULT_IMAGE_URL = "https://res.cloudinary.com/trippictures/image/upload/v1661692507/png-transparent-albums-computer-icons-others_avqku6.png";
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

    @Transactional//because pictures(List<PictureEntity>) in RouteEntity are lazy loaded.
    public List<RouteViewModel> findAllRoutesView() {
        return this.routeRepository
                .findAll()
                .stream()
                .map(e -> new RouteViewModel()
                        .setId(e.getId())
                        .setName(e.getName())
                        .setPictureUrl(e.getPictures().isEmpty() ?
                                DEFAULT_IMAGE_URL :
                                e.getPictures().get(0).getUrl())
                        .setDescription(e.getDescription()))
                .collect(Collectors.toList());
    }

    @Transactional
    public RouteDetailsViewModel findRouteById(Long id) {

        RouteEntity routeEntity = this.routeRepository.findById(id).get();

        RouteDetailsViewModel routeDetailsViewModel = new RouteDetailsViewModel()
                .setId(routeEntity.getId())
                .setDescription(routeEntity.getDescription())
                .setName(routeEntity.getName())
                .setPictures(routeEntity.getPictures().stream().toList())// .stream().toList() is needed so collection of PictureEntity to be initialized.
                .setGpxCoordinates(routeEntity.getGpxCoordinates())
                .setVideoUrl(routeEntity.getVideoUrl())
                .setLevel(routeEntity.getLevel())
                .setAuthorFullName(routeEntity.getAuthor().getFirstName() + " " + routeEntity.getAuthor().getLastName());

        return routeDetailsViewModel;
    }

    public RouteEntity findRouteEntityById(Long id){
        RouteEntity routeEntity = this.routeRepository.findById(id).get();
        return routeEntity;
    }
}

