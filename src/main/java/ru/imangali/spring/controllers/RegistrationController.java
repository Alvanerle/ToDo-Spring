package ru.imangali.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.imangali.spring.domain.Role;
import ru.imangali.spring.domain.User;
import ru.imangali.spring.repo.UserRepo;

import java.util.Collections;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/registration")
    public String showRegistrationPage(){
        return "registration";
    }

    boolean validate(User user, Model model) {
        if(userRepo.findByUsername(user.getUsername()) != null){
            model.addAttribute("message", "user already exists");
            return false;
        }

        if(containsSpace(user.getUsername())){
            model.addAttribute("message", "username cannot contain spaces");
            return false;
        }

        if(user.getPassword().length() > 50 || user.getPassword().length() < 6){
            model.addAttribute("message", "password length should be in range [6, 50]");
            return false;
        }

        return true;
    }

    @PostMapping("/registration")
    public String addUser(User user, Model model) {
        user.setUsername(user.getUsername().trim());
        if(!validate(user, model)){
            return "registration";
        }

        String encoded = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(encoded);
        userRepo.save(user);

        return "redirect:/login";
    }

    public boolean containsSpace(String username){
        return username.contains(" ");
    }
}
