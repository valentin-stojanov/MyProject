package com.myproject.project.web;

import com.myproject.project.model.dto.UserViewModel;
import com.myproject.project.service.AdminService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping()
    String adminPage(Model model,
                     @PageableDefault(size = 2) Pageable pageable){
        Page<UserViewModel> users = this.adminService.getAllUsers(pageable);
        model.addAttribute("users", users);
        System.out.println();
        return "admin";
    }

    @GetMapping("/lock/{userId}")
    String lockUser(@PathVariable Long userId){
        this.adminService.lockUser(userId);
        return "redirect:/admin";
    }

}
