package tasks;

import status.Status;

import java.util.Objects;
public class Task {
    private int id;
    private String name;
    private Status status;
    private String description;
    public Task(String name, String description, Status status) {
        this.name = name;
        this.status = status;
        this.description = description;
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
        return getId() == task.getId() && Objects.equals(getName(), task.getName()) && getStatus() == task.getStatus() && Objects.equals(getDescription(), task.getDescription());
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, name, status, description);
    }
    @Override
    public String toString() {
        return "tasks.tasks.Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", description='" + description + '\'' +
                '}';
    }
}
