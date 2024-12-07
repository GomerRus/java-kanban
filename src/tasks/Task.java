package tasks;

import status.Status;
import status.TypeTasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task {
    private int id;
    private String name;
    private Status status;
    private String description;
    protected TypeTasks typeTasks;
    private Duration duration;
    private LocalDateTime startTime;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.status = Status.NEW;
        this.typeTasks = TypeTasks.TASK;
    }

    public Task(int id, String name, String description, Status status) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.description = description;
        this.typeTasks = TypeTasks.TASK;
    }

    public Task(String name, String description, Status status) {
        this.name = name;
        this.status = status;
        this.description = description;
        this.typeTasks = TypeTasks.TASK;
    }

    public Task(int id, String name, String description, Status status, Duration duration, LocalDateTime startTime) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.description = description;
        this.typeTasks = TypeTasks.TASK;
        this.duration = duration;
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return startTime.plus(duration);
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public TypeTasks getTypeTasks() {
        return typeTasks;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Status getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNameTask(String nameTask) {
        this.name = nameTask;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task task)) return false;
        return getId() == task.getId() && Objects.equals(getName(),
                task.getName()) && getStatus() == task.getStatus() && Objects.equals(getDescription(),
                task.getDescription()) && getTypeTasks() == task.getTypeTasks() && Objects.equals(getDuration(),
                task.getDuration()) && Objects.equals(getStartTime(), task.getStartTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getStatus(), getDescription(), getTypeTasks(),
                getDuration(), getStartTime());
    }

    @Override
    public String toString() {
        return "Task{" +
                "description='" + description + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", typeTasks=" + typeTasks +
                ", duration=" + duration +
                ", startTime=" + startTime +
                '}';
    }

    public String toStringCsv() {
        if (startTime != null) {
            return String.join(",", String.valueOf(id), typeTasks.toString(), name, status.toString(),
                    description, startTime.toString(), duration.toString());
        } else {
            return String.join(",", String.valueOf(id), typeTasks.toString(), name, status.toString(),
                    description);
        }
    }
}

