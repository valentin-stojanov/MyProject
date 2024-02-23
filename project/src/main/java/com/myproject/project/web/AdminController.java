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

    @GetMapping("/users")
    String adminPage(Model model,
                     @PageableDefault(size = 2) Pageable pageable){
        Page<UserViewModel> users = this.adminService.getAllUsers(pageable);
        model.addAttribute("users", users);
        System.out.println();
        return "admin";
    }

    @GetMapping("users/{userId}/actions/lock")
    String lockUser(@PathVariable Long userId){
        this.adminService.lockUser(userId);
        return "redirect:/admin/users";
    }

    @GetMapping("users/{userId}/actions/unlock")
    String unlockUser(@PathVariable Long userId){
        this.adminService.unlockUser(userId);
        return "redirect:/admin/users";
    }

}
