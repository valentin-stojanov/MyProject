package com.myproject.project.web;

import com.myproject.project.model.dto.PictureUploadDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RouteController {

    @GetMapping("/route")
    public String addRoute() {
        return "add-route";
    }

    @PostMapping("/route")
    public String addPicture(PictureUploadDto pictureUploadDto) {
        return "redirect:/pictures/all";
    }

    @GetMapping("/pictures/all")
    public String allPictures(Model model) {
        return "all";
    }
}
