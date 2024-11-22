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
        fb.createTask(task);
        fb.createEpic(epic);

    }

    @Test
    void createFileTest() throws IOException {
        try {
            Files.delete(path);
        } catch (IOException exp) {
            throw new RuntimeException(exp);
        }
        fb.save();
        assertTrue(Files.exists(path));
        String HEAD = "id,type,name,status,description,epicId";
        String[] strTasks = Files.readString(path, StandardCharsets.UTF_8).split("\n");
        assertEquals(strTasks[0], HEAD, "Заголовок не соответствует");
    }

    @Test
    void loadFromFileTest() {
        fb.save();
        FileBackendTaskManager newFb = FileBackendTaskManager.loadFromFile(path);
        assertArrayEquals(fb.getAllTasks().toArray(), newFb.getAllTasks().toArray(), "Таски не равны");
        assertArrayEquals(fb.getAllEpics().toArray(), newFb.getAllEpics().toArray(), "Эпики не равны");
    }
}

