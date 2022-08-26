package com.myproject.project.web;

import com.myproject.project.model.dto.PictureUploadDto;
import com.myproject.project.model.dto.PictureViewModel;
import com.myproject.project.service.PictureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/routes")
public class RouteController {

    private final PictureService pictureService;

    public RouteController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    @GetMapping("/add")
    public String addRoute() {
        return "add-route";
    }

    @PostMapping("/add")
    public String addPicture(PictureUploadDto pictureUploadDto) throws IOException {
        this.pictureService.addPicture(pictureUploadDto);
        return "redirect:/pictures/all";
    }



    @GetMapping("/pictures/all")
    public String allPictures(Model model) {
        List<PictureViewModel> pictures = this.pictureService.getAllPictures();
        model.addAttribute("pictures", pictures);
        return "all";
    }

    @Transactional
    @DeleteMapping("/pictures/delete")
    public String delete(@RequestParam("public_id") String publicId){
        this.pictureService.deleteByPublicId(publicId);

        return "redirect:/pictures/all";
    }


}
