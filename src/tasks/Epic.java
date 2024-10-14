package tasks;

import status.Status;

import java.util.ArrayList;
import java.util.Objects;
public class Epic extends Task {
    private ArrayList<Integer> subTaskIds = new ArrayList<>();
    public Epic(String nameTask, String description) {
        super(nameTask,description, Status.NEW);
    }
    public void deleteSubTaskIdsById(int id) {
        if (!subTaskIds.isEmpty()){
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
        return "tasks.tasks.Epic{" +
                "subtaskIds=" + subTaskIds +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", name='" + getName() + '\'' +
                ", status=" + getStatus() +
                '}';
    }
}
