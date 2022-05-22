package ru.imangali.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.imangali.spring.dao.UserDAO;
import ru.imangali.spring.models.User;

@Controller
public class RegistrationController {
    private final UserDAO userDAO;

    @Autowired
    public RegistrationController(UserDAO userDAO){
        this.userDAO = userDAO;
    }


    @GetMapping("/registration")
    public String showRegistrationPage(){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Model model){
        user.setUsername(user.getUsername().trim());
        if(userDAO.find(user.getUsername()) != null){
            model.addAttribute("message", "user already exists");
            return "registration";
        }
        if(containsSpace(user.getUsername())){
            model.addAttribute("message", "username cannot contain spaces");
            return "registration";
        }
        String encoded = new BCryptPasswordEncoder().encode(user.getPassword());

        user.setActive(true);
        user.setPassword(encoded);
        userDAO.save(user);

        return "redirect:/login";
    }

    public boolean containsSpace(String username){
        return username.contains(" ");
    }
}
