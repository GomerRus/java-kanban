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
        Epic epic = epics.get(subTask.getEpicId());
        if (epic != null) {
            subTasks.put(subTask.getId(), subTask);
            epic.createSubTaskIds(subTask);
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
            if (subTasks.get(subTask.getId()).getEpicId() == subTask.getEpicId()) {
                subTasks.put(subTask.getId(), subTask);
                Epic epic = epics.get(subTask.getEpicId());
                updateStatusEpic(epic);
            }
        } else {
            System.out.println("Такой подзадачи не найдено");
        }
    }

    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            Epic epicNew = epics.get(epic.getId()); // получили старый Epic по ID нового
            epicNew.setNameTask(epic.getName()); // поменяли имя старого Epic-a
            epicNew.setDescription(epic.getDescription()); // поменяли описание
            // Ирек, я очень надеюсь что я сделал то что надо...
        } else {
            System.out.println("Такого Эпика нет ");
        }
    }

    //-----------------показали все----------------------
    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> taskAll = new ArrayList<>();
        for (Task task : tasks.values()) {
            taskAll.add(task);
        }
        return taskAll;
    }
    public ArrayList<SubTask> getAllSubTasks() {
        ArrayList<SubTask> subTaskAll = new ArrayList<>();
        for (SubTask subTask : subTasks.values()) {
            subTaskAll.add(subTask);
        }
        return subTaskAll;
    }

    public ArrayList<Epic> getAllEpics() {
        ArrayList<Epic> epicAll = new ArrayList<>();
        for (Epic epic : epics.values()) {
            epicAll.add(epic);
        }
        return epicAll;
    }

    //--------------------показали по одному-------------------
    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    public SubTask getSubTaskById(int id) {
        return subTasks.get(id);
    }

    public Epic getEpicById(int id) {
        return epics.get(id);
    }

    // ------------------удалили все--------------------
    public void deleteAllTasks() {
        tasks.clear();
    }

   public void deleteAllSubTasks() {
       subTasks.clear();
       for (Epic epic : epics.values()) {
           epic.deleteAllSubTaskIds();
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
        SubTask subTask = subTasks.get(id);
        if (!subTasks.isEmpty()){
            Epic epic = epics.get(subTask.getEpicId());
            epic.deleteSubTaskIdsById(id);
            subTasks.remove(id);
            updateStatusEpic(epic);

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
    public ArrayList<SubTask> getAllSubTaskByEpicId(int id) {
        ArrayList<SubTask> subTasksNew = new ArrayList<>();
        if (epics.containsKey(id)) {
            Epic epic = epics.get(id);
            for (int i = 0; i < epic.getSubTaskIds().size(); i++) {
                subTasksNew.add(subTasks.get(epic.getSubTaskIds().get(i)));
            }
        } else {
            System.out.println("По вашему запросу нет данных");
        }
        return subTasksNew;
    }
    //----------------------------Обновление статуса Эпика-----------
    private void updateStatusEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            if (epic.getSubTaskIds().isEmpty()) {
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







