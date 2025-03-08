package model;

import enums.TaskType;

import java.time.LocalDate;

public class Task {
    private String id;
    private String title;
    private String description;
    private TaskType type;
    private LocalDate date;

    public Task(String id,String title, String description, TaskType type, LocalDate date) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.date = date;
    }

    public  String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskType getType() {
        return type;
    }

    public void setType(TaskType type) {
        this.type = type;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}