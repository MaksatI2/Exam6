package model;

import enums.TaskType;

import java.time.LocalDate;

public class Task {
    private String title;
    private String description;
    private TaskType type;
    private LocalDate date;

    public Task(String title, String description, TaskType type, LocalDate date) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.date = date;
    }

    // Геттеры и сеттеры
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