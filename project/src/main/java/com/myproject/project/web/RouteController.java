package com.myproject.project.web;

import com.myproject.project.model.dto.PictureUploadDto;
import com.myproject.project.model.entity.PictureEntity;
import com.myproject.project.repository.PictureRepository;
import com.myproject.project.service.CloudinaryImage;
import com.myproject.project.service.CloudinaryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class RouteController {

    private final CloudinaryService cloudinaryService;
    private final PictureRepository pictureRepository;

    public RouteController(CloudinaryService cloudinaryService,
                           PictureRepository pictureRepository) {
        this.cloudinaryService = cloudinaryService;
        //TODO: refactoring!
        this.pictureRepository = pictureRepository;
    }

    @GetMapping("/route")
    public String addRoute() {
        return "add-route";
    }

    @PostMapping("/route")
    public String addPicture(PictureUploadDto pictureUploadDto) throws IOException {
        PictureEntity picture = createPictureEntity(pictureUploadDto.getPicture(), pictureUploadDto.getTitle());

        this.pictureRepository.save(picture);

        return "redirect:/pictures/all";
    }

    private PictureEntity createPictureEntity(MultipartFile multipartFile, String title) throws IOException {
        CloudinaryImage uploaded = this.cloudinaryService.upload(multipartFile);
        return new PictureEntity()
                .setPublicId(uploaded.getPublicId())
                .setTitle(title)
                .setUrl(uploaded.getUrl());
    }

    @GetMapping("/pictures/all")
    public String allPictures(Model model) {

        return "all";
    }
}
