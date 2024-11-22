package tasks;

import status.Status;
import status.TypeTasks;

import java.util.Objects;

public class SubTask extends Task {
    private int epicId;

    public SubTask(String nameTask, String description, Status status, int epicId) {
        super(nameTask, description, status);
        this.epicId = epicId;
        this.typeTasks = TypeTasks.SUBTASK;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SubTask subtask = (SubTask) o;
        return epicId == subtask.epicId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicId);
    }

    @Override
    public String toString() {
        return super.toString() +
                " epicId=" + epicId +
                "}";
    }

    @Override
    public String toStringCsv() {
        return super.toStringCsv().concat(',' + String.valueOf(epicId));
    }
}
