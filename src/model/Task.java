package model;

import java.time.LocalDate;

public class Task {
    private int id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private String priority;
    private boolean completed;

    public Task(int id, String title, String description, LocalDate dueDate, String priority){
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.completed = false;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public String getPriority() {
        return priority;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void markCompleted() {
        this.completed = true;
    }

    @Override 
    public String toString(){
        return title + " | Due: " + dueDate + " | Priority: " + priority + " | Completed: " + completed;
    }
}
