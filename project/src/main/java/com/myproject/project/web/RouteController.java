package com.myproject.project.web;

import com.myproject.project.model.dto.RouteAddDto;
import com.myproject.project.service.RouteService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Controller
@RequestMapping("/routes")
public class RouteController {


    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @ModelAttribute
    public RouteAddDto initRouteAddDto(){
        return new RouteAddDto();
    }

    @GetMapping("/add")
    public String addRoute() {
        return "add-route";
    }

    @PostMapping("/add")
    public String addPicture(@Valid RouteAddDto routeAddDto,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()){
            redirectAttributes
                    .addFlashAttribute("routeAddDto", routeAddDto)
                    .addFlashAttribute("org.springframework.validation.BindingResult.routeAddDto", bindingResult);

            return "redirect:/routes/add";
        }

        this.routeService

        return "redirect:/pictures/all";
    }

}
