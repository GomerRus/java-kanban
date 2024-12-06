package tasks;

import status.Status;
import status.TypeTasks;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {
    private ArrayList<Integer> subTaskIds = new ArrayList<>();
    private LocalDateTime endTime;

    public Epic(String name, String description) {
        super(name, description, Status.NEW);
        this.typeTasks = TypeTasks.EPIC;
    }

    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void deleteSubTaskIdsById(int id) {
        if (!subTaskIds.isEmpty()) {
            Integer iD = Integer.valueOf(id);
            subTaskIds.remove(iD);
        } else {
            System.out.println("Список пуст");
        }
    }

    public void deleteAllSubTaskIds() {
        subTaskIds.clear();
    }

    public void createSubTaskIds(SubTask subTask) {
        subTaskIds.add(subTask.getId());
    }

    public ArrayList<Integer> getSubTaskIds() {
        return new ArrayList<>(subTaskIds);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subTaskIds, epic.subTaskIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subTaskIds);
    }

    @Override
    public String toString() {
        if (subTaskIds.isEmpty()) {
            return super.toString();
        } else {
            return super.toString() +
                    " id подзадач=" + subTaskIds +
                    "}";
        }
    }
}


