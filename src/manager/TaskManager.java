package manager;
import tasks.Task;
import tasks.Epic;
import tasks.SubTask;
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
    List<Task> getAllTasks();
    List<SubTask> getAllSubTasks();
    List<Epic> getAllEpics();
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
    List<SubTask> getAllSubTaskByEpicId(int id);
    //----------------------------Обновление статуса Эпика-----------
}

