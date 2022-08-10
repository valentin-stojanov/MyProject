package com.myproject.project.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RouteController {

    @GetMapping("/route")
    public String addRoute(){
        return "add-route";
    }
}
