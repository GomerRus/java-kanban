import static org.junit.jupiter.api.Assertions.*;

import manager.HistoryManager;
import manager.Managers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import status.Status;
import tasks.Task;
import tasks.Epic;
import tasks.SubTask;

import java.util.List;

class HistoryManagerTest {

    HistoryManager historyManager = Managers.getDefaultHistory();
    static Task task1, task2;
    static Epic epic1, epic2;
    static SubTask subTask1, subTask2;
    static int id = 0;

    @BeforeAll
    static void createTasks() {
        task1 = new Task("Task-1", "Описание-1", Status.NEW);
        task1.setId(id++);
        task2 = new Task("Task-2", "Описание-2", Status.NEW);
        task2.setId(id++);
        epic1 = new Epic("Epic-1", "Описание-1");
        epic1.setId(id++);
        epic2 = new Epic("Epic-2", "Описание-2");
        epic2.setId(id++);
        subTask1 = new SubTask("SubTask-1", "Описание-1", Status.NEW, epic1.getId());
        subTask1.setId(id++);
        subTask2 = new SubTask("SubTask-2", "Описание-2", Status.NEW, epic1.getId());
        subTask2.setId(id++);
    }

    @Test
    void add() {
        historyManager.add(task1);
        assertEquals(1, historyManager.getHistory().size(), "Задача не добавляется");
        assertEquals(task1, historyManager.getHistory().getFirst(), "Задачи не совпадают");
        historyManager.add(epic1);
        historyManager.add(subTask1);
        assertEquals(3, historyManager.getHistory().size(), "Задачи других типов не добавляются");
        assertEquals(subTask1, historyManager.getHistory().getLast(), "Последняя задача не совпадает");
        List<Task> list = historyManager.getHistory();
        assertArrayEquals(list.toArray(), historyManager.getHistory().toArray(), "Добавленные задачи не совпадают");
    }

    @Test
    void addTasksSecondTime() {
        historyManager.add(task1);
        historyManager.add(epic1);
        historyManager.add(subTask1);
        historyManager.add(epic1);
        historyManager.add(task1);
        List<Task> list = historyManager.getHistory();
        assertEquals(3, list.size(), "Размер list = 3 элементов");
    }

    @Test
    void getHistory() {
        historyManager.add(task1);
        historyManager.add(epic1);
        historyManager.add(subTask1);
        List<Task> list = historyManager.getHistory();
        assertEquals(3, list.size(), "Размер list = 3 элемента");
    }

    @Test
    void removeTaskTest() {
        historyManager.add(task1);
        historyManager.add(subTask2);
        historyManager.add(epic2);
        historyManager.add(epic1);
        historyManager.remove(task1.getId());
        List<Task> list = historyManager.getHistory();
        assertEquals(3, list.size(), "task1 не удален");
        historyManager.remove(epic2.getId());
        list = historyManager.getHistory();
        assertEquals(2, list.size(), "epic2 не удален");
    }
}