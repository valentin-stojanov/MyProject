package com.myproject.project.web;

import com.myproject.project.model.dto.UserRegistrationDto;
import com.myproject.project.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserRegistrationController {
    private final UserService userService;

    public UserRegistrationController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("userRegistrationModel")
    public UserRegistrationDto initUserRegistrationModel(){
        return new UserRegistrationDto();
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid UserRegistrationDto userRegistrationModel,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                           HttpServletRequest req){
        if (bindingResult.hasErrors()){
            redirectAttributes
                    .addFlashAttribute("userRegistrationModel", userRegistrationModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.userRegistrationModel", bindingResult);
            return "redirect:/users/register";
        }

        String newUserEmail = this.userService.registerUser(userRegistrationModel);
        this.userService.login(req, newUserEmail, userRegistrationModel.getPassword());

        return "redirect:/";
    }
}
