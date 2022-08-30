package com.myproject.project.web;

import com.myproject.project.model.dto.PictureUploadDto;
import com.myproject.project.model.dto.RouteAddDto;
import com.myproject.project.model.dto.RouteViewModel;
import com.myproject.project.model.entity.RouteEntity;
import com.myproject.project.model.entity.UserEntity;
import com.myproject.project.service.PictureService;
import com.myproject.project.service.RouteService;
import com.myproject.project.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


@Controller
@RequestMapping("/routes")
public class RouteController {


    private final RouteService routeService;
    private final UserService userService;
    private final PictureService pictureService;

    public RouteController(RouteService routeService,
                           UserService userService,
                           PictureService pictureService) {
        this.routeService = routeService;
        this.userService = userService;
        this.pictureService = pictureService;
    }

    @ModelAttribute
    public RouteAddDto initRouteAddDto(){
        return new RouteAddDto();
    }

    @GetMapping()
    public String routes(Model model){

        List<RouteViewModel> routeViewModels = this.routeService
                .findAllRoutesView();
        model.addAttribute("routes", routeViewModels);

        return "routes";
    }

    @GetMapping("/add")
    public String addRoute() {
        return "add-route";
    }

    @PostMapping("/add")
    public String addRoute(@Valid RouteAddDto routeAddDto,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                           @AuthenticationPrincipal UserDetails userDetails) throws IOException {

        if (bindingResult.hasErrors()){
            redirectAttributes
                    .addFlashAttribute("routeAddDto", routeAddDto)
                    .addFlashAttribute("org.springframework.validation.BindingResult.routeAddDto", bindingResult);

            return "redirect:/routes/add";
        }

        this.routeService.addNewRoute(routeAddDto, userDetails);

        return "redirect:/routes";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable Long id, Model model){

        model.addAttribute("route", this.routeService.findRouteById(id));

        return "route-details";
    }

    @PostMapping("/details/{id}")
    public String addPictureToRoute(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails, PictureUploadDto pictureUploadDto) throws IOException {


        UserEntity author = this.userService.findUserByEmail(userDetails.getUsername());

        RouteEntity route = this.routeService.findRouteEntityById(id);

        //TODO: refactoring this part!!!
        if (route.getAuthor().getEmail().equals(author.getEmail())) {
            //add picture
            this.pictureService.addPicture(pictureUploadDto, author, route);
            //TODO: implement showing pictures for given route!
            return "redirect:/routes/details/" + id;
        } else{
            //don't have permission for this action
            return "redirect:/routes/details/" + id;
        }

    }

}
