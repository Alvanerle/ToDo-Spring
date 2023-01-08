package ru.imangali.spring.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.imangali.spring.domain.Task;
import ru.imangali.spring.domain.User;
import ru.imangali.spring.repo.TaskRepo;


import javax.validation.Valid;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskRepo taskRepo;

    @GetMapping()
    public String index(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("tasks", taskRepo.index(user.getId()));

        return "tasks/index";
    }
    

    @GetMapping("/new")
    public String newTask(@ModelAttribute("task") Task task){
        return "tasks/new";
    }

    @PostMapping
    public String create(@AuthenticationPrincipal User user,
                        @ModelAttribute("task") @Valid Task task,
                         BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "tasks/new";

        task.setAuthor(user);
        taskRepo.save(task);

        return "redirect:/tasks";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id){
        model.addAttribute("task", taskRepo.get(id));

        return "tasks/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("task") @Valid Task task, BindingResult bindingResult,
                         @PathVariable("id") Long id){
        if(bindingResult.hasErrors())
            return "tasks/edit";

        taskRepo.update(task.getName(), task.getDescription(), task.getDeadline(), task.getPriority(), id);

        return "redirect:/tasks";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id){
        taskRepo.deleteById(id);

        return "redirect:/tasks";
    }
}
