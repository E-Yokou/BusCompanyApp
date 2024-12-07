package com.example.BusCompanyApp.controlles;

import com.example.BusCompanyApp.models.Role;
import com.example.BusCompanyApp.models.UserRegistrationDto;
import com.example.BusCompanyApp.repositories.RoleRepository;
import com.example.BusCompanyApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import java.util.Optional;

@Controller
public class MainController {

    @GetMapping("/")
    public String redirectWithUsingRedirectPrefix() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/admin";
        } else if (authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_USER"))) {
            return "redirect:/user";
        } else {
            return "index";
        }
    }

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userRegistrationDto", new UserRegistrationDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute UserRegistrationDto userRegistrationDto, Model model) {
        try {
            // Устанавливаем роль ROLE_USER автоматически
            Optional<Role> userRole = roleRepository.findByRoleName("ROLE_USER");
            userRegistrationDto.setUserRoleId(userRole.get().getRoleId());
            userService.registerUser(userRegistrationDto);
            model.addAttribute("successMessage", "User registered successfully");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error registering user: " + e.getMessage());
        }
        return "index";
    }
}