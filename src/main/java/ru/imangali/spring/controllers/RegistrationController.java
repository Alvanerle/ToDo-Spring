package ru.imangali.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
        if(userDAO.find(user.getUsername()) != null){
            model.addAttribute("message", "user already exists");
            return "registration";
        }
        user.setActive(true);
        userDAO.save(user);

        return "redirect:/login";
    }
}
