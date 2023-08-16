package com.myproject.project.web;

import com.myproject.project.model.dto.RouteAddDto;
import com.myproject.project.model.dto.RouteViewModel;
import com.myproject.project.service.RouteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


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

    @GetMapping()
    public String routes(Model model,
                         @PageableDefault(size = 2) Pageable pageable){

        Page<RouteViewModel> routeViewModels = this.routeService
                .findAllRoutesView(pageable);
        model.addAttribute("routes", routeViewModels);

        return "routesN";
    }

    @GetMapping("/add")
    public String addRoute() {
        return "add-routeN";
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
    public String details(@PathVariable Long id,
                          Model model){

        model.addAttribute("route", this.routeService.findRouteById(id));

        return "route-detailsN";
    }

}
