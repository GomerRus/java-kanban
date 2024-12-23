package manager;

import tasks.Task;
import tasks.Epic;
import tasks.SubTask;
import status.Status;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private int nextId = 0;
    private HistoryManager historyManager = Managers.getDefaultHistory();
    private Set<Task> prioritizedTasks = new TreeSet<>(Comparator.comparing(Task::getStartTime));

    @Override
    public List<Task> getPrioritizedTasks() {
        return new ArrayList<>(prioritizedTasks);
    }

    private void validTask(Task validTask) {
        if (prioritizedTasks.isEmpty()) {
            return;
        }
        for (Task task : prioritizedTasks) {
            if (validTask.getStartTime().isBefore(task.getEndTime())
                    && validTask.getEndTime().isAfter(task.getStartTime())) {
                throw new RuntimeException("Задачи пересекаются по времени");
            }
        }
    }

    private void checkTimeAndDurationInEpic(Epic epic) {
        LocalDateTime startTime = getAllSubTaskByEpicId(epic.getId()).stream()
                .filter(subTask -> subTask.getStartTime() != null)
                .map(SubTask::getStartTime)
                .min(LocalDateTime::compareTo)
                .orElse(null);
        epic.setStartTime(startTime);

        LocalDateTime endTime = getAllSubTaskByEpicId(epic.getId()).stream()
                .filter(subTask -> subTask.getStartTime() != null)
                .map(SubTask::getEndTime)
                .max(LocalDateTime::compareTo)
                .orElse(null);
        epic.setEndTime(endTime);

        Duration duration = getAllSubTaskByEpicId(epic.getId()).stream()
                .filter(subTask -> subTask.getStartTime() != null)
                .map(SubTask::getDuration)
                .reduce(Duration.ZERO, Duration::plus);
        if (duration == Duration.ZERO) {
            epic.setDuration(null);
        } else {
            epic.setDuration(duration);
        }
    }

    protected void setNextId(int nextId) {
        this.nextId = nextId;
    }

    private int getNextId() {
        return nextId++;
    }

    protected void setTasks(Task task) {
        switch (task.getTypeTasks()) {
            case TASK -> {
                tasks.put(task.getId(), task);
                if (task.getStartTime() != null) {
                    prioritizedTasks.add(task);
                }
            }
            case SUBTASK -> {
                subTasks.put(task.getId(), (SubTask) task);
                Epic epic = epics.get(((SubTask) task).getEpicId());
                epic.createSubTaskIds((SubTask) task);
                if (task.getStartTime() != null) {
                    prioritizedTasks.add(task);
                }
            }
            case EPIC -> epics.put(task.getId(), (Epic) task);
        }
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    // --------------------создали------------------------------
    @Override
    public Task createTask(Task task) {
        if (task.getStartTime() != null) {
            validTask(task);
        }
        task.setId(++nextId);
        tasks.put(task.getId(), task);
        if (task.getStartTime() != null) {
            prioritizedTasks.add(task);
        }
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
        if (subTask.getStartTime() != null) {
            validTask(subTask);
        }
        Epic epic = epics.get(subTask.getEpicId());

        if (epic != null) {
            subTask.setId(++nextId);
            subTasks.put(subTask.getId(), subTask);
            epic.createSubTaskIds(subTask);
            updateStatusEpic(epic);
            checkTimeAndDurationInEpic(epic);
            if (subTask.getStartTime() != null) {
                prioritizedTasks.add(subTask);
            }

        } else {
            System.out.println("Такого Эпика нет");
        }

        return subTask;
    }

    //--------------------------Обновили---------------
    @Override
    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            deleteTasksById(task.getId());
        }
        if (task.getStartTime() != null) {
            validTask(task);
        }
        tasks.put(task.getId(), task);
        prioritizedTasks.add(task);
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        if (subTasks.containsKey(subTask.getId())
                && subTasks.get(subTask.getId()).getEpicId() == subTask.getEpicId()) {
            deleteSubTaskById(subTask.getId());
        }
        if (subTask.getStartTime() != null) {
            validTask(subTask);
        }
        subTasks.put(subTask.getId(), subTask);
        prioritizedTasks.add(subTask);
        Epic epic = epics.get(subTask.getEpicId());
        updateStatusEpic(epic);
        checkTimeAndDurationInEpic(epic);
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
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<SubTask> getAllSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    @Override
    public ArrayList<Epic> getAllEpics() {
        ArrayList<Epic> epicAll = new ArrayList<>();
        for (Epic epic : epics.values()) {
            epicAll.add(epic);
            checkTimeAndDurationInEpic(epic);
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
            prioritizedTasks.remove(task);
        }
        tasks.clear();
    }

    @Override
    public void deleteAllSubTasks() {
        for (SubTask subTask : subTasks.values()) {
            historyManager.remove(subTask.getId());
            prioritizedTasks.remove(subTask);
        }
        subTasks.clear();
        for (Epic epic : epics.values()) {
            epic.deleteAllSubTaskIds();
            updateStatusEpic(epic);
            checkTimeAndDurationInEpic(epic);
        }
    }

    @Override
    public void deleteAllEpics() {
        for (SubTask subTask : subTasks.values()) {
            historyManager.remove(subTask.getId());
            prioritizedTasks.remove(subTask);
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
            Task removeTask = tasks.get(id);
            prioritizedTasks.remove(removeTask);
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
            prioritizedTasks.remove(subTask);
            Epic epic = epics.get(subTask.getEpicId());
            epic.deleteSubTaskIdsById(id);
            subTasks.remove(id);
            historyManager.remove(id);
            updateStatusEpic(epic);
            checkTimeAndDurationInEpic(epic);
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
                prioritizedTasks.remove(tasks.get(subTaskId));
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