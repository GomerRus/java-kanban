import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private int nextId = 0;

    // --------------------создали------------------------------
    public void createTask(Task task) {
        task.setId(++nextId);
        tasks.put(task.getId(), task);
    }

    public void createEpic(Epic epic) {
        epic.setId(++nextId);
        epics.put(epic.getId(), epic);
    }

    public void createSubTask(SubTask subTask) {
        subTask.setId(++nextId);
        Epic epic = epics.get(subTask.getEpicIds());
        if (epic != null) {
            subTasks.put(subTask.getId(), subTask);
            epic.getSubTaskIds().add(subTask.getId());
            updateStatusEpic(epic);
        } else {
            System.out.println("Такого Эпика нет");
        }
    }

    //--------------------------Обновили---------------
    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        } else {
            System.out.println("Такой задачи нет");
        }
    }

    public void updateSubTask(SubTask subTask) {
        if (subTasks.containsKey(subTask.getId())) {
            subTasks.put(subTask.getId(), subTask);
            Epic epic = epics.get(subTask.getEpicIds());
            updateStatusEpic(epic);
        } else {
            System.out.println("Такой подзадачи не найдено");
        }
    }

    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            epics.put(epic.getId(), epic);
            updateStatusEpic(epic);
        } else {
            System.out.println("Такого Эпика нет ");
        }
    }

    //----------------------показали все-------------------------------
    public void getAllTasks() {
        for (Task taskAll : tasks.values()) {
            System.out.println(taskAll);
        }
    }

    public void getAllSubTasks() {
        for (SubTask subTaskAll : subTasks.values()) {
            System.out.println(subTaskAll);
        }
    }

    public void getAllEpics() {
        for (Epic epicAll : epics.values()) {
            System.out.println(epicAll);
        }
    }

    //--------------------показали по одному-------------------
    public Task getTaskById(int id) {
        if (tasks.containsKey(id)) {
            return tasks.get(id);
        }
        return null;
    }

    public SubTask getSubTaskById(int id) {
        if (subTasks.containsKey(id)) {
            return subTasks.get(id);
        }
        return null;
    }

    public Epic getEpiсById(int id) {
        if (epics.containsKey(id)) {
            return epics.get(id);
        }
        return null;
    }

    // ------------------удалили все--------------------
    public void deleteAllTasks() {
        tasks.clear();
    }

    public void deleteAllSubTasks() {
        subTasks.clear();
        for (Epic epic : epics.values()) {
            epic.getSubTaskIds().clear();
            updateStatusEpic(epic);
        }
    }

    public void deleteAllEpics() {
        subTasks.clear();
        epics.clear();
    }

    //-------------------удалили по одному------------------
    public void deleteTasksById(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
        } else {
            System.out.println("Такой задачи нет.");
        }
    }

    public void deleteSubTaskById(int id) {
        SubTask subTask = subTasks.get(id); // поместили в subTask по ключу подзадачи
        if (subTask != null) {
            Epic epic = epics.get(subTask.getEpicIds());// доcтали из мапы по ID SubTaska
            epic.getSubTaskIds().remove(subTask.getId()); //Integer добавить
            updateStatusEpic(epic);
            subTasks.remove(id);
        } else {
            System.out.println("Такой подзадачи нет.");
        }
    }

    public void deleteEpicById(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            for (Integer subTaskId : epic.getSubTaskIds()) {
                subTasks.remove(subTaskId);
            }
            epics.remove(id);
        } else {
            System.out.println("Такого Эпика нет.");
        }
    }

    //-------------------показали по Эпику----------------------
    public void getAllSubTaskByEpicId(int id) {
        if (epics.containsKey(id)) {
            ArrayList<SubTask> subTasksNew = new ArrayList<>();
            Epic epic = epics.get(id);
            for (int i = 0; i < epic.getSubTaskIds().size(); i++) {
                subTasksNew.add(subTasks.get(epic.getSubTaskIds().get(i)));
                System.out.println(subTasksNew);
            }
        } else {
            System.out.println("По вашему запросу нет данных");
        }
    }
    //----------------------------Обновление статуса Эпика-----------
    public void updateStatusEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            if (epic.getSubTaskIds().size() == 0) {
                epic.setStatus(Status.NEW);
            } else {
                ArrayList<SubTask> subTasksNew = new ArrayList<>();
                int countDone = 0;
                int countNew = 0;
                for (int i = 0; i < epic.getSubTaskIds().size(); i++) {
                    subTasksNew.add(subTasks.get(epic.getSubTaskIds().get(i)));
                }
                for (SubTask subtask : subTasksNew) {
                    if (subtask.getStatus() == Status.DONE) {
                        countDone++;
                    }
                    if (subtask.getStatus() == Status.NEW) {
                        countNew++;
                    }
                    if (subtask.getStatus() == Status.IN_PROGRESS) {
                        epic.setStatus(Status.IN_PROGRESS);
                        return;
                    }
                }
                if (countDone == epic.getSubTaskIds().size()) {
                    epic.setStatus(Status.DONE);
                } else if (countNew == epic.getSubTaskIds().size()) {
                    epic.setStatus(Status.NEW);
                } else {
                    epic.setStatus(Status.IN_PROGRESS);
                }
            }
        } else {
            System.out.println("Такого эпика нет");
        }
    }
}







