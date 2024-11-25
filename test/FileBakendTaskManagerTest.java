import manager.FileBackendTaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import status.Status;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileBakendTaskManagerTest {
    FileBackendTaskManager fb = new FileBackendTaskManager();
    Task task;
    Epic epic;
    SubTask subTask1;
    SubTask subTask2;
    Path path = Path.of("src", "saved_information", "savedTasks.csv");

    @BeforeEach
    void createTask() {
        task = new Task("Task-1", "Описание-1", Status.NEW);
        epic = new Epic("Epic-1", "Описание-1");
        SubTask subTask1 = new SubTask("SubTask-1", "Описание-1", Status.NEW, epic.getId());
        SubTask subTask2 = new SubTask("SubTask-2", "Описание-2", Status.NEW, epic.getId());
        fb.createTask(task);
        fb.createEpic(epic);
        fb.createSubTask(subTask1);
        fb.createSubTask(subTask2);

    }

    @Test
    void createFileTest() throws IOException {
        String HEAD = "id,type,name,status,description,epicId";
        String[] strTasks = Files.readString(path, StandardCharsets.UTF_8).split("\n");
        assertEquals(strTasks[0], HEAD, "Заголовок не соответствует");
    }

    @Test
    void loadFromFileTest() {
        FileBackendTaskManager newFb = FileBackendTaskManager.loadFromFile(path);
        assertArrayEquals(fb.getAllTasks().toArray(), newFb.getAllTasks().toArray(), "Таски не равны");
        assertArrayEquals(fb.getAllEpics().toArray(), newFb.getAllEpics().toArray(), "Эпики не равны");
        assertArrayEquals(fb.getAllSubTasks().toArray(), newFb.getAllSubTasks().toArray(), "СабТаски не равны");
    }
}

