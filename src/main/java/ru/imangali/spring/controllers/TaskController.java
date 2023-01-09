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

    @GetMapping("/assigned")
    public String assigned_tasks(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("tasks", taskRepo.assigned_tasks(user.getId()));

        return "tasks/assigned";
    }

    @GetMapping("/in_process")
    public String in_process_tasks(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("tasks", taskRepo.to_do_tasks(user.getId()));

        return "tasks/to-do";
    }

    @GetMapping("/finished")
    public String finished_tasks(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("tasks", taskRepo.finished_tasks(user.getId()));

        return "tasks/finished";
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

        return "redirect:/tasks/assigned";
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

        taskRepo.update(task.getName(), task.getDescription(), task.getDeadline(), task.getPriority(), task.getType(), id);

        return "redirect:/tasks/assigned";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id){
        taskRepo.deleteById(id);

        return "redirect:/tasks/assigned";
    }
}
