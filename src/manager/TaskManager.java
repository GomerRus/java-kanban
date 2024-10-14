package manager;
import tasks.Task;
import tasks.Epic;
import tasks.SubTask;
import java.util.ArrayList;
import java.util.List;
public interface TaskManager {
    List<Task> getHistory ();
    // --------------------создали------------------------------
    Task createTask(Task task);
    Epic createEpic(Epic epic);
    SubTask createSubTask(SubTask subTask);
    //--------------------------Обновили---------------
    void updateTask(Task task);
    void updateSubTask(SubTask subTask);
    void updateEpic(Epic epic);
    //-----------------показали все----------------------
    ArrayList<Task> getAllTasks();
    ArrayList<SubTask> getAllSubTasks();
    ArrayList<Epic> getAllEpics();
    //--------------------показали по одному-------------------
    Task getTaskById(int id);
    SubTask getSubTaskById(int id);
    Epic getEpicById(int id);
    // ------------------удалили все--------------------
    void deleteAllTasks();
    void deleteAllSubTasks();
    void deleteAllEpics();
    //-------------------удалили по одному------------------
    void deleteTasksById(int id);
    void deleteSubTaskById(int id);
    void deleteEpicById(int id);
    //-------------------показали по Эпику----------------------
    ArrayList<SubTask> getAllSubTaskByEpicId(int id);
    //----------------------------Обновление статуса Эпика-----------
}

