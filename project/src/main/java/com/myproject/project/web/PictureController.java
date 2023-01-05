package com.myproject.project.web;

import com.myproject.project.model.dto.PictureUploadDto;
import com.myproject.project.model.entity.RouteEntity;
import com.myproject.project.model.entity.UserEntity;
import com.myproject.project.service.PictureService;
import com.myproject.project.service.RouteService;
import com.myproject.project.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;

@Controller
public class PictureController {

    private final PictureService pictureService;
    private final UserService userService;
    private final RouteService routeService;

    public PictureController(PictureService pictureService,
                             UserService userService,
                             RouteService routeService) {
        this.pictureService = pictureService;
        this.userService = userService;
        this.routeService = routeService;
    }

    @PostMapping("/routes/details/{routeId}/pictures")
    public String addPictureToRoute(@PathVariable Long routeId,
                                    @AuthenticationPrincipal UserDetails userDetails,
                                    PictureUploadDto pictureUploadDto) {

        UserEntity author = this.userService.findUserByEmail(userDetails.getUsername());
        RouteEntity route = this.routeService.findRouteEntityById(routeId);

        boolean isAuthorOfRoute = route.getAuthor().getEmail().equals(author.getEmail());

        //TODO: refactor this part!!!
        if (isAuthorOfRoute) {
            this.pictureService.addPicture(pictureUploadDto, author, route);
        } else{
            //don't have permission for this action
        }
        return "redirect:/routes/details/" + routeId;

    }

    @Transactional
    @DeleteMapping("/routes/details/{routeId}/pictures")
    public String deletePictureForRoute(@PathVariable Long routeId,
                                        @AuthenticationPrincipal UserDetails userDetails,
                                        @RequestParam("public_id") String publicId){

        UserEntity author = this.userService.findUserByEmail(userDetails.getUsername());
        RouteEntity route = this.routeService.findRouteEntityById(routeId);

        boolean isAuthorOfPicture = route.getAuthor().getEmail().equals(author.getEmail())
                && route.getPictures()
                .stream()
                .anyMatch(p -> p.getPublicId().equals(publicId));

        if (isAuthorOfPicture) {
            this.pictureService.deleteByPublicId(publicId);
        } else {
            ////don't have permission for this action
        }

        return "redirect:/routes/details/" + routeId;
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ModelAndView onIllegalArgument(IllegalArgumentException iae) {
        ModelAndView modelAndView = new ModelAndView("picture-error");
        modelAndView.addObject("error", iae.getMessage());

        return modelAndView;
    }


//    @GetMapping("/pictures/all")
//    public String allPictures(Model model) {
//        List<PictureViewModel> pictures = this.pictureService.getAllPictures();
//        model.addAttribute("pictures", pictures);
//        return "all";
//    }

//    @Transactional
//    @DeleteMapping("/pictures/delete")
//    public String delete(@RequestParam("public_id") String publicId){
//        this.pictureService.deleteByPublicId(publicId);
//
//        return "redirect:/pictures/all";
//    }
}
