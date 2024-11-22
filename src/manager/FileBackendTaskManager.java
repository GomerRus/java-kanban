package manager;

import exceptions.ManagerSaveException;
import status.Status;
import status.TypeTasks;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileBackendTaskManager extends InMemoryTaskManager implements TaskManager {
    private Path path = Path.of("src", "saved_information", "savedTasks.csv");
    private static final String HEAD = "id,type,name,status,description,epicId";

    public FileBackendTaskManager(Path path) {
        this.path = path;
    }

    public FileBackendTaskManager() {
    }

    public static Task fromString(String value) {
        String[] str = value.split(",");
        int id = Integer.parseInt(str[0]);
        TypeTasks type = TypeTasks.valueOf(str[1]);
        String name = str[2];
        Status status = Status.valueOf(str[3]);
        String description = str[4];
        int epicId;
        if (str.length > 5) {
            epicId = Integer.parseInt(str[5]);
        } else {
            epicId = 0;
        }
        switch (type) {
            case TASK -> {
                return new Task(name, description, status);
            }
            case EPIC -> {
                Epic epic = new Epic(name, description);
                epic.setStatus(status);
                epic.setId(id);
                return epic;
            }
            case SUBTASK -> {
                return new SubTask(name, description, status, epicId);
            }
            default -> {
                return null;
            }
        }
    }

    public void save() {
        try (BufferedWriter bw = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            bw.write(HEAD + "\n");
            for (Task task : getAllTasks()) {
                bw.write(task.toStringCsv() + "\n");
            }
            for (Epic epic : getAllEpics()) {
                bw.write(epic.toStringCsv() + "\n");
            }
            for (SubTask subTask : getAllSubTasks()) {
                bw.write(subTask.toStringCsv() + "\n");
            }
        } catch (IOException exp) {
            throw new ManagerSaveException("Сохранить данные в файл НЕ УДАЛОСЬ ", exp);
        }
    }

    public static FileBackendTaskManager loadFromFile(Path path) {
        FileBackendTaskManager fb = new FileBackendTaskManager(path);
        int maxId = 0;
        try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            br.readLine();
            while (br.ready()) {
                String line = br.readLine();
                Task task = fromString(line);
                assert task != null;
                int id = task.getId();
                switch (task.getTypeTasks()) {
                    case TASK:
                        fb.createTask(task);
                        break;
                    case EPIC:
                        fb.createEpic((Epic) task);
                        break;
                    case SUBTASK:
                        fb.createSubTask((SubTask) task);
                        break;
                }
                if (id > maxId) {
                    maxId = id;
                }
            }
        } catch (IOException exp) {
            throw new ManagerSaveException("Восстановить данные НЕ УДАЛОСЬ");
        }
        return fb;
    }

    @Override
    public Task createTask(Task task) {
        Task newTask = super.createTask(task);
        save();
        return newTask;
    }

    @Override
    public Epic createEpic(Epic epic) {
        Epic newEpic = super.createEpic(epic);
        save();
        return newEpic;
    }

    @Override
    public SubTask createSubTask(SubTask subTask) {
        SubTask newSubTask = super.createSubTask(subTask);
        save();
        return newSubTask;
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        super.updateSubTask(subTask);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void deleteEpicById(int id) {
        super.deleteEpicById(id);
        save();
    }

    @Override
    public void deleteSubTaskById(int id) {
        super.deleteSubTaskById(id);
        save();
    }

    @Override
    public void deleteTasksById(int id) {
        super.deleteTasksById(id);
        save();
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void deleteAllSubTasks() {
        super.deleteAllSubTasks();
        save();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }
}

