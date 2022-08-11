package com.myproject.project.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CloudinaryService {

    public CloudinaryImage upload(MultipartFile file){

        return null;
    }

    public boolean delete(String publicId){
        return false;
    }
}
