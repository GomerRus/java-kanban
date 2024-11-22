package manager;

import tasks.Task;
import tasks.Epic;
import tasks.SubTask;
import status.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private int nextId = 0;
    private HistoryManager historyManager = Managers.getDefaultHistory();

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    // --------------------создали------------------------------
    @Override
    public Task createTask(Task task) {
        task.setId(++nextId);
        tasks.put(task.getId(), task);
        return task;
    }

    @Override
    public Epic createEpic(Epic epic) {
        epic.setId(++nextId);
        epics.put(epic.getId(), epic);
        return epic;
    }

    @Override
    public SubTask createSubTask(SubTask subTask) {
        subTask.setId(++nextId);
        Epic epic = epics.get(subTask.getEpicId());
        if (epic != null) {
            subTasks.put(subTask.getId(), subTask);
            epic.createSubTaskIds(subTask);
            updateStatusEpic(epic);
        } else {
            System.out.println("Такого Эпика нет");
        }
        return subTask;
    }

    //--------------------------Обновили---------------
    @Override
    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        } else {
            System.out.println("Такой задачи нет");
        }
    }

    @Override
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

    @Override
    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            Epic epicNew = epics.get(epic.getId());
            epicNew.setNameTask(epic.getName());
            epicNew.setDescription(epic.getDescription());
        } else {
            System.out.println("Такого Эпика нет ");
        }
    }

    //-----------------показали все----------------------
    @Override
    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> taskAll = new ArrayList<>();
        for (Task task : tasks.values()) {
            taskAll.add(task);
        }
        return taskAll;
    }

    @Override
    public ArrayList<SubTask> getAllSubTasks() {
        ArrayList<SubTask> subTaskAll = new ArrayList<>();
        for (SubTask subTask : subTasks.values()) {
            subTaskAll.add(subTask);
        }
        return subTaskAll;
    }

    @Override
    public ArrayList<Epic> getAllEpics() {
        ArrayList<Epic> epicAll = new ArrayList<>();
        for (Epic epic : epics.values()) {
            epicAll.add(epic);
        }
        return epicAll;
    }

    //--------------------показали по одному-------------------
    @Override
    public Task getTaskById(int id) {
        historyManager.add(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public SubTask getSubTaskById(int id) {
        historyManager.add(subTasks.get(id));
        return subTasks.get(id);
    }

    @Override
    public Epic getEpicById(int id) {
        historyManager.add(epics.get(id));
        return epics.get(id);
    }

    // ------------------удалили все--------------------
    @Override
    public void deleteAllTasks() {
        for (Task task : tasks.values()) {
            historyManager.remove(task.getId());
        }
        tasks.clear();
    }

    @Override
    public void deleteAllSubTasks() {
        for (SubTask subTask : subTasks.values()) {
            historyManager.remove(subTask.getId());
        }
        subTasks.clear();
        for (Epic epic : epics.values()) {
            epic.deleteAllSubTaskIds();
            updateStatusEpic(epic);
        }
    }

    @Override
    public void deleteAllEpics() {
        for (SubTask subTask : subTasks.values()) {
            historyManager.remove(subTask.getId());
        }
        for (Epic epic : epics.values()) {
            historyManager.remove(epic.getId());
        }
        subTasks.clear();
        epics.clear();
    }

    //-------------------удалили по одному------------------
    @Override
    public void deleteTasksById(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
            historyManager.remove(id);
        } else {
            System.out.println("Такой задачи нет.");
        }
    }

    @Override
    public void deleteSubTaskById(int id) {
        SubTask subTask = subTasks.get(id);
        if (!subTasks.isEmpty()) {
            Epic epic = epics.get(subTask.getEpicId());
            epic.deleteSubTaskIdsById(id);
            subTasks.remove(id);
            historyManager.remove(id);
            updateStatusEpic(epic);
        } else {
            System.out.println("Такой подзадачи нет.");
        }
    }

    @Override
    public void deleteEpicById(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            for (Integer subTaskId : epic.getSubTaskIds()) {
                subTasks.remove(subTaskId);
                historyManager.remove(subTaskId);
            }
            epics.remove(id);
            historyManager.remove(id);
        } else {
            System.out.println("Такого Эпика нет.");
        }
    }

    //-------------------показали по Эпику----------------------
    @Override
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