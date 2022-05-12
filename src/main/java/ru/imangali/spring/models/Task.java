package ru.imangali.spring.models;


import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.util.Date;

public class Task {
    private int id;

    @NotEmpty(message = "Name should not be empty")
    private String name;

    @NotEmpty(message = "Description should not be empty")
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deadline;

    @Min(value = 1, message = "Priority should be in range [1, 10]")
    @Max(value = 10, message = "Priority should be in range [1, 10]")
    private int priority;

    public Task(){

    }

    public Task(int id, String name, String description, Date deadline, int priority) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
