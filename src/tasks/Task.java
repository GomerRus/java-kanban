package tasks;

import status.Status;
import status.TypeTasks;
import java.util.Objects;

public class Task {
    private int id;
    private String name;
    private Status status;
    private String description;
    protected TypeTasks typeTasks;

    public Task(String name, String description, Status status) {
        this.name = name;
        this.status = status;
        this.description = description;
        this.typeTasks = TypeTasks.TASK;
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
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return getId() == task.getId() && Objects.equals(getName(), task.getName()) && getStatus() == task.getStatus() && Objects.equals(getDescription(), task.getDescription()) && getTypeTasks() == task.getTypeTasks();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getStatus(), getDescription(), getTypeTasks());
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + '{' +
                "название='" + name + '\'' +
                ", описание='" + description + '\'' +
                ", id=" + id + '\'' +
                ", статус=" + status +
                '}';
    }

    public String toStringCsv() {
        return String.join(",", String.valueOf(id), typeTasks.toString(), name, status.toString(), description);
    }
}
