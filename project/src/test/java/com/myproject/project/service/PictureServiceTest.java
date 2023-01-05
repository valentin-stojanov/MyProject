package com.myproject.project.service;

import com.myproject.project.model.dto.CloudinaryImageDto;
import com.myproject.project.model.dto.PictureUploadDto;
import com.myproject.project.model.entity.PictureEntity;
import com.myproject.project.model.entity.RoleEntity;
import com.myproject.project.model.entity.RouteEntity;
import com.myproject.project.model.entity.UserEntity;
import com.myproject.project.model.enums.RoleEnum;
import com.myproject.project.repository.PictureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PictureServiceTest {

    @Mock
    private PictureRepository pictureRepositoryMock;

    @Mock
    private CloudinaryService cloudinaryServiceMock;

    @Mock
    MultipartFile multipartFileMock;

    @InjectMocks
    PictureService toTest;

    @Captor
    ArgumentCaptor<PictureEntity> pictureEntityArgumentCaptor;

    @BeforeEach
    void setUp() {
        multipartFileMock = new MockMultipartFile("testMultipartFile", new byte[]{1, 2, 3});
    }

    @Test
    void shouldAddPicture() throws IOException {
        //Arrange
        PictureUploadDto testPictureUploadDto = new PictureUploadDto()
                .setTitle("Test picture title")
                .setPicture(multipartFileMock);

        UserEntity testAuthor = new UserEntity()
                .setEmail("test@example.com")
                .setPassword("topsecret")
                .setFirstName("Test")
                .setLastName("Testov")
                .setRoles(List.of(
                                new RoleEntity().setRole(RoleEnum.ADMIN),
                                new RoleEntity().setRole(RoleEnum.USER)
                        )
                );

        RouteEntity testRouteEntity = new RouteEntity()
                .setAuthor(testAuthor);

        //Arrange for createPictureEntity() in PictureService
        CloudinaryImageDto testUploadedCloudinaryImageDto = new CloudinaryImageDto()
                .setPublicId("Test_public_id")
                .setUrl("/test/url");

        PictureEntity expected = new PictureEntity()
                .setPublicId(testUploadedCloudinaryImageDto.getPublicId())
                .setTitle(testPictureUploadDto.getTitle())
                .setUrl(testUploadedCloudinaryImageDto.getUrl())
                .setAuthor(testAuthor)
                .setRoute(testRouteEntity);

        when(cloudinaryServiceMock.upload(multipartFileMock))
                .thenReturn(testUploadedCloudinaryImageDto);

        //Act
        this.toTest.addPicture(testPictureUploadDto, testAuthor, testRouteEntity);

        //Assert
        verify(this.pictureRepositoryMock).save(pictureEntityArgumentCaptor.capture());

        PictureEntity pictureFromCreatePictureEntity = pictureEntityArgumentCaptor.getValue();

        assertEquals(expected.getPublicId(), pictureFromCreatePictureEntity.getPublicId());
        assertEquals(expected.getTitle(), pictureFromCreatePictureEntity.getTitle());
        assertEquals(expected.getUrl(), pictureFromCreatePictureEntity.getUrl());
        assertEquals(expected.getAuthor(), pictureFromCreatePictureEntity.getAuthor());
        assertEquals(expected.getRoute(), pictureFromCreatePictureEntity.getRoute());

        //Assertion for createPictureEntity()
        verify(cloudinaryServiceMock).upload(same(multipartFileMock));
    }
}