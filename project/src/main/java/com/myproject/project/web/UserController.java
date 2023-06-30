package com.myproject.project.web;

import com.myproject.project.model.dto.UserResetEmailDto;
import com.myproject.project.model.dto.UserResetPasswordDto;
import com.myproject.project.model.dto.UserViewModel;
import com.myproject.project.service.EmailService;
import com.myproject.project.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final EmailService emailService;

    @ModelAttribute("userResetEmailModel")
    public UserResetEmailDto initUserResetEmailModel(){
        return new UserResetEmailDto();
    }

    @ModelAttribute("userResetPasswordModel")
    public UserResetPasswordDto initUserResetPasswordModel(){
        return new UserResetPasswordDto();
    }

    public UserController(UserService userService,
                          EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login-error")
    public String onFailedLogin(@ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) String username,
                                RedirectAttributes redirectAttributes){
        redirectAttributes.
                addFlashAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, username)
                .addFlashAttribute("bad_credentials", true);
        return "redirect:/users/login";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@Valid UserResetEmailDto userResetEmailModel,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()){
            redirectAttributes
                    .addFlashAttribute("userResetEmailModel", userResetEmailModel)
                    .addFlashAttribute("invalid_email", true)
                    .addFlashAttribute("org.springframework.validation.BindingResult.userResetEmailModel", bindingResult);
            return "redirect:/users/login";
        }

        String resetUrl = this.userService.generateResetUrl(userResetEmailModel.getEmail());
        this.emailService.sendResetPasswordEmail(userResetEmailModel.getEmail(), resetUrl);

        redirectAttributes
                .addFlashAttribute("valid_email", true);
        return "redirect:/users/login";
    }

    @GetMapping("/reset-password/reset/{token}")
    public String onResetPassword(@PathVariable("token") String token, Model model){
        model.addAttribute("resetToken", token);
        return "reset-password";
    }

    @PostMapping("/reset-password/reset/{token}")
    public String onResetPassword(@PathVariable("token") String token,
                                  @Valid UserResetPasswordDto userResetPasswordModel,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            redirectAttributes
                    .addFlashAttribute("userResetPasswordModel", userResetPasswordModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.userResetPasswordModel", bindingResult);

            return "redirect:/users/reset-password/reset/{token}";
        }

        this.userService.resetPasswordWithResetToken(token, userResetPasswordModel);
        return "redirect:/users/login";
    }


    @GetMapping("/profile")
    public String profile(Model model, @AuthenticationPrincipal UserDetails userDetails){
        UserViewModel userViewModel = this.userService.getUserInfo(userDetails.getUsername());
        model.addAttribute("userView", userViewModel);
        return "profileN";
    }

    @ExceptionHandler({IllegalStateException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView onIllegalState(IllegalStateException ise){
        ModelAndView modelAndView = new ModelAndView("reset-password-error");
        modelAndView.addObject("error", ise.getMessage());

        return modelAndView;
    }
}
