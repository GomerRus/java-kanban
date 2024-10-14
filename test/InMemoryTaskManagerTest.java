
import static org.junit.jupiter.api.Assertions.*;

import manager.InMemoryTaskManager;
import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import status.Status;
import tasks.Task;
import tasks.Epic;
import tasks.SubTask;
import java.util.List;

  class InMemoryTaskManagerTest {
      protected TaskManager taskManager;

      @BeforeEach
      void beforeEach() {
          taskManager = new InMemoryTaskManager(Managers.getDefaultHistory());
      }

      @Test
      void createTask() {
          Task task = new Task("Task-1", "Описание-1", Status.NEW);
          Task result = taskManager.createTask(task);
          assertNotNull(result, "Сбой создания ЗАДАЧИ");
          assertTrue(result.getId() > 0, "Сбой подсчета Id ЗАДАЧИ");
      }

      @Test
      void createEpic() {
          Epic epic = new Epic("Epic-1", "Описание-1");
          Epic result = taskManager.createEpic(epic);
          assertNotNull(result, "Сбой создания ЭПИКА");
          assertTrue(result.getId() > 0, "Сбой подсчета Id ЭПИКА");
      }

      @Test
      void createSubTaskWhenHaveEpic() {
          taskManager.createEpic(new Epic("Epic-1", "Описание-1"));
          SubTask subTask = new SubTask("SubTask-1", "Описание-1", Status.NEW, 1);
          SubTask result = taskManager.createSubTask(subTask);
          assertNotNull(result, "Сбой создания ПодЗадачи");
          assertTrue(result.getId() > 0, "Сбой подсчета Id ПодЗадачи");
      }

      @Test
      void createSubTaskWhenNotEpic() {
          SubTask subTask = new SubTask("SubTask-1", "Описание-1", Status.NEW, 1);
          SubTask result = taskManager.createSubTask(subTask);
          assertNotNull(result, "Нет ЭПИКА,нет ПодЗадачи");
      }

      @Test
      void getAllTasks() {
          Task task1 = new Task("Task-1", "Описание-1", Status.NEW);
          Task task2 = new Task("Task-2", "Описание-2", Status.NEW);
          Task task3 = new Task("Task-3", "Описание-3", Status.NEW);
          taskManager.createTask(task1);
          taskManager.createTask(task2);
          taskManager.createTask(task3);
          List<Task> tasks = taskManager.getAllTasks();
          assertEquals(3, tasks.size(), "Должно быть 3 ЗАДАЧИ");
      }

      @Test
      void getAllTasksFromEmptyList() {
          List<Task> tasks = taskManager.getAllTasks();
          assertEquals(0, tasks.size(), "Должно быть 0 Задач");
      }

      @Test
      void getAllEpics() {
          Epic epic1 = new Epic("Epic-1", "Описание-1");
          Epic epic2 = new Epic("Epic-2", "Описание-2");
          taskManager.createEpic(epic1);
          taskManager.createEpic(epic2);
          List<Epic> epics = taskManager.getAllEpics();
          assertEquals(2, epics.size(), "Должно быть 2 ЭПИКА");
      }

      @Test
      void getAllEpicsFromEmptyList() {
          List<Epic> epics = taskManager.getAllEpics();
          assertEquals(0, epics.size(), "Должно быть 0 ЭПИКОВ");
      }

      @Test
      void getAllSubTasks() {
          taskManager.createEpic(new Epic("Epic-1", "Описание-1"));
          SubTask subTask1 = new SubTask("SubTask-1", "Описание-1", Status.NEW, 1);
          SubTask subTask2 = new SubTask("SubTask-2", "Описание-2", Status.NEW, 1);
          taskManager.createSubTask(subTask1);
          taskManager.createSubTask(subTask2);
          List<SubTask> subTasks = taskManager.getAllSubTasks();
          assertEquals(2, subTasks.size(), "Должно быть 2 ПодЗадачи");
      }

      @Test
      void getAllSubTaskFromEmptyList() {
          taskManager.createEpic(new Epic("Epic-1", "Описание-1"));
          List<SubTask> subTasks = taskManager.getAllSubTasks();
          assertEquals(0, subTasks.size(), "Должно быть 0 ПодЗадач");
      }

      @Test
      void getTaskById() {
          Task task = new Task("Task-1", "Описание-1", Status.NEW);
          taskManager.createTask(task);
          assertEquals(task, taskManager.getTaskById(1), "Неверный индекс в хранилище");
          assertNull(taskManager.getTaskById(0), "Нет такого Id.Результат должен быть 0");
      }

      @Test
      void getEpicById() {
          Epic epic = new Epic("Epic-1", "Описание-1");
          taskManager.createEpic(epic);
          assertEquals(epic, taskManager.getEpicById(1), "Неверный индекс в хранилище");
          assertNull(taskManager.getEpicById(0), "Нет такого Id.Результат должен быть 0");
      }

      @Test
      void getSubTaskById() {
          taskManager.createEpic(new Epic("Epic-1", "Описание-1"));
          SubTask subTask = new SubTask("SubTask-2", "Описание-2", Status.NEW, 1);
          taskManager.createSubTask(subTask);
          assertEquals(subTask, taskManager.getSubTaskById(2), "Неверный индекс в хранилище");
          assertNull(taskManager.getEpicById(3), "Нет такого Id.Результат должен быть 0");
      }

      @Test
      void deleteAllTasks() {
          Task task1 = new Task("Task-1", "Описание-1", Status.NEW);
          Task task2 = new Task("Task-2", "Описание-2", Status.NEW);
          taskManager.createTask(task1);
          taskManager.createTask(task2);
          taskManager.deleteAllTasks();
          assertEquals(0, taskManager.getAllTasks().size(), "Размер списка Задач должен = 0");
      }

      @Test
      void deleteAllEpics() {
          Epic epic1 = new Epic("Epic-1", "Описание-1");
          Epic epic2 = new Epic("Epic-2", "Описание-2");
          taskManager.createEpic(epic1);
          taskManager.createEpic(epic2);
          taskManager.deleteAllEpics();
          assertEquals(0, taskManager.getAllTasks().size(), "Размер списка Эпика должен = 0");
      }

      @Test
      void deleteAllSubTasks() {
          taskManager.createEpic(new Epic("Epic-1", "Описание-1"));
          SubTask subTask1 = new SubTask("SubTask-1", "Описание-1", Status.NEW, 1);
          SubTask subTask2 = new SubTask("SubTask-2", "Описание-2", Status.NEW, 1);
          taskManager.createSubTask(subTask1);
          taskManager.createSubTask(subTask2);
          taskManager.deleteAllSubTasks();
          assertEquals(0, taskManager.getAllSubTasks().size(), "Размер списка ПодЗадач должен = 0");
      }

      @Test
      void deleteTaskById() {
          Task task1 = new Task("Task-1", "Описание-1", Status.NEW);
          taskManager.createTask(task1);
          taskManager.deleteTasksById(1);
          assertNull(taskManager.getTaskById(1), "Должен быть 0 Задач после удаления");
      }

      @Test
      void deleteEpicById() {
          Epic epic = new Epic("Epic-1", "Описание-1");
          taskManager.createEpic(epic);
          taskManager.deleteEpicById(1);
          assertNull(taskManager.getEpicById(1), "Должен быть 0 Эпика после удаления");
      }

      @Test
      void deleteSubTaskById() {
          taskManager.createEpic(new Epic("Epic-1", "Описание-1"));
          SubTask subTask = new SubTask("SubTask-1", "Описание-1", Status.NEW, 1);
          taskManager.createSubTask(subTask);
          taskManager.deleteSubTaskById(2);
          assertNull(taskManager.getSubTaskById(2), "Подзадача не была удалена");
          assertEquals(0, taskManager.getEpicById(1).getSubTaskIds().size(),
                  "Список подзадач Epic должен измениться");
      }

      @Test
      void getHistory() {
          Task task1 = taskManager.createTask(new Task("Task-1", "Описание-1", Status.NEW));
          Epic epic2 = taskManager.createEpic(new Epic("Epic-2", "Описание-2"));
          SubTask subTask3 = taskManager.createSubTask(new SubTask("SubTask-3",
                  "Описание-3", Status.NEW, 2));
          taskManager.getTaskById(1);
          taskManager.getEpicById(2);
          taskManager.getSubTaskById(3);
          List<Task> list = taskManager.getHistory();
          assertEquals(3, list.size(), "Размер list должен быть = 3");
          assertEquals(task1, list.get(0), "Task-1 должен быть первым");
          assertEquals(epic2, list.get(1), "Epic-2");
          assertEquals(subTask3, list.get(2), "SubTask-3 должен быть третьим");
          assertEquals(3, list.size(), "list должен быть = 3");
      }
  }






