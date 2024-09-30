import java.util.Objects;

public class SubTask extends Task{
    private  int epicIds;

    public SubTask(String nameTask,String description,Status status,int epicIds) {
        super(nameTask,description,status);
        this.epicIds = epicIds;
    }
    public int getEpicIds() {
        return epicIds;
    }

    public void setEpicId(int epicIds) {
        this.epicIds = epicIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SubTask subtask = (SubTask) o;
        return epicIds == subtask.epicIds;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicIds);
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "epicIds=" + getEpicIds() +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", name='" + getName() + '\'' +
                ", status=" + getStatus() +
                '}';
    }
}
