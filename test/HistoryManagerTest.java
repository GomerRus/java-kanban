import static org.junit.jupiter.api.Assertions.*;

import manager.HistoryManager;
import manager.Managers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import status.Status;
import tasks.Task;
import tasks.Epic;
import tasks.SubTask;
import java.util.List;

class HistoryManagerTest {
    HistoryManager historyManager;

    @BeforeEach
    void beforeEach() {
        historyManager = Managers.getDefaultHistory();
    }

    @Test
    void add() {
        Task task1 = new Task("Task-1", "Описание-1", Status.NEW);
        Epic epic2 = new Epic("Epic-2", "Описание-2");
        SubTask subTask3 = new SubTask("SubTask-3", "Описание-3", Status.NEW, 2);
        historyManager.add(task1);
        historyManager.add(epic2);
        historyManager.add(subTask3);
        List<Task> list = historyManager.getHistory();
        assertEquals(3, list.size(), "Размер list = 3 элемента");
        assertEquals(task1, list.get(0), "Task-1 должен быть первым ");
        assertEquals(epic2, list.get(1), "Epic-2 должен быть вторым");
        assertEquals(subTask3, list.get(2), "SubTask-3 должен быть третьим ");
    }

    @Test
    void addTasksSecondTime() {
        Task task1 = new Task("Task-1", "Описание-1", Status.NEW);
        Epic epic2 = new Epic("Epic-2", "Описание-2");
        SubTask subTask3 = new SubTask("SubTask-3", "Описание-3", Status.NEW, 2);
        historyManager.add(task1);
        historyManager.add(epic2);
        historyManager.add(subTask3);
        historyManager.add(epic2);
        historyManager.add(task1);
        List<Task> list = historyManager.getHistory();
        assertEquals(5, list.size(), "Размер list = 5 элементов");
    }

    @Test
    void removeFirstElement() {
        Task task1 = new Task("Task-1", "Описание-1", Status.NEW);
        Epic epic2 = new Epic("Epic-2", "Описание-2");
        SubTask subTask3 = new SubTask("SubTask-3", "Описание-3", Status.NEW, 2);
        historyManager.add(task1);
        historyManager.add(epic2);
        historyManager.add(subTask3);
        List<Task> list = historyManager.getHistory();
        list.remove(0);
        assertEquals(2, list.size(),"Размер list = 2, после удаления");
        assertEquals(epic2, list.get(0), "По идексу 0 должны получить Epic-2");
        assertEquals(subTask3,list.get(1), "По индексу 1 должны получить SubTask-3");
    }

    @Test
    void getHistory() {
        Task task1 = new Task("Task-1", "Описание-1", Status.NEW);
        Epic epic2 = new Epic("Epic-2", "Описание-2");
        SubTask subTask3 = new SubTask("SubTask-3", "Описание-3", Status.NEW, 2);
        historyManager.add(task1);
        historyManager.add(epic2);
        historyManager.add(subTask3);
        List<Task> list = historyManager.getHistory();
        assertEquals(3, list.size(), "Размер list = 3 элемента");
    }
}