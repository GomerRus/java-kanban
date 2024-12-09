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
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class FileBakendTaskManagerTest {
    FileBackendTaskManager fb = new FileBackendTaskManager();
    Task task;
    Epic epic;
    SubTask subTask;
    SubTask subTask2;
    Path path = Path.of("src", "saved_information", "savedTasks.csv");

    @BeforeEach
    void createTask() {
        task = new Task(1, "Task", "Описание-Task", Status.NEW,
                Duration.ofMinutes(15), LocalDateTime.of(2024, 7, 7, 15, 15));
        epic = new Epic("Epic", "Описание-Epic");

        subTask = new SubTask(3, "SubTask-1", "Описание-SubTask-1", Status.NEW,
                Duration.ofMinutes(15),
                LocalDateTime.of(2024, 7, 7, 15, 0), 2);

        subTask2 = new SubTask(4, "SubTask-2", "Описание-SubTask-2", Status.NEW,
                Duration.ofMinutes(15),
                LocalDateTime.of(2024, 7, 7, 15, 45), 2);
        fb.createTask(task);
        fb.createEpic(epic);
        fb.createSubTask(subTask);
        fb.createSubTask(subTask2);
    }

    @Test
    void createFileTest() throws IOException {
        String HEAD = "id,type,name,status,description,startTime,duration,epicId";
        String[] strTasks = Files.readString(path, StandardCharsets.UTF_8).split("\n");
        assertEquals(strTasks[0], HEAD, "Заголовок не соответствует");
    }

    @Test
    void loadFromFileTest() {
        FileBackendTaskManager newFb = FileBackendTaskManager.loadFromFile(path);
        assertArrayEquals(fb.getAllTasks().toArray(), newFb.getAllTasks().toArray(), "Таски не равны");
        assertArrayEquals(fb.getAllSubTasks().toArray(), newFb.getAllSubTasks().toArray(), "СабТаски не равны");
        assertArrayEquals(fb.getAllEpics().toArray(), newFb.getAllEpics().toArray(), "Эпики не равны");
    }
}

