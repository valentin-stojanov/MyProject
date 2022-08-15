package com.myproject.project.service;

import com.myproject.project.model.dto.CloudinaryImageDto;
import com.myproject.project.model.dto.PictureUploadDto;
import com.myproject.project.model.dto.PictureViewModel;
import com.myproject.project.model.entity.PictureEntity;
import com.myproject.project.repository.PictureRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class PictureService {

    private final PictureRepository pictureRepository;
    private final CloudinaryService cloudinaryService;

    public PictureService(PictureRepository pictureRepository, CloudinaryService cloudinaryService) {
        this.pictureRepository = pictureRepository;
        this.cloudinaryService = cloudinaryService;
    }

    public void addPicture(PictureUploadDto pictureUploadDto) throws IOException {
        PictureEntity picture = createPictureEntity(pictureUploadDto.getPicture(),
                pictureUploadDto.getTitle());

        this.pictureRepository.save(picture);
    }

    private PictureEntity createPictureEntity(MultipartFile multipartFile, String title) throws IOException {
        CloudinaryImageDto uploaded = this.cloudinaryService.upload(multipartFile);
        return new PictureEntity()
                .setPublicId(uploaded.getPublicId())
                .setTitle(title)
                .setUrl(uploaded.getUrl());
    }

    public List<PictureViewModel> getAllPictures() {
        return  this.pictureRepository
                .findAll()
                .stream()
                .map(this::asViewModel)
                .toList();
    }
    private PictureViewModel asViewModel(PictureEntity picture){
        return new PictureViewModel()
                .setPublicId(picture.getPublicId())
                .setTitle(picture.getTitle())
                .setUrl(picture.getUrl());
    }

    public void deleteByPublicId(String publicId) {
        if (cloudinaryService.delete(publicId)) {
            pictureRepository.deleteAllByPublicId(publicId);
        }

    }
}
