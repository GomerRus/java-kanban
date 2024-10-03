import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Epic extends Task {

    private ArrayList<Integer> subTaskIds = new ArrayList<>();

   public Epic(String nameTask, String description) {
    super(nameTask,description,Status.NEW);
    }

    public void deleteSubTaskIdsById(int id) {
       if (!subTaskIds.isEmpty()){
           subTaskIds.remove(id);
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
       subTaskIds = new ArrayList<>();
        return subTaskIds;
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
        return "Epic{" +
                "subtaskIds=" + subTaskIds +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", name='" + getName() + '\'' +
                ", status=" + getStatus() +
                '}';
    }
}
