import manager.FileBackendTaskManager;
import status.Status;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.nio.file.Path;

public class Main {

    public static void main(String[] args) {

        FileBackendTaskManager fb = new FileBackendTaskManager();
        Task task1 = new Task(1, "Доработать приложение", "Манагер", Status.NEW);
        Task task2 = new Task(2, "Пробежать марафон", "Пушкин-Питер", Status.NEW);
        fb.createTask(task1);
        fb.createTask(task2);
        Epic epic1 = new Epic("Понедельник", "!!!");
        SubTask subTask1 = new SubTask(4, "Нал", "Позвонить ", Status.NEW, epic1.getId());
        SubTask subTask = new SubTask(5, "Налоги", "Позвонить бухгалтеру", Status.NEW, epic1.getId());
        fb.createEpic(epic1);
        fb.createSubTask(subTask);
        fb.createSubTask(subTask1);
        System.out.println(fb.getAllTasks());
        System.out.println(fb.getAllEpics());
        System.out.println(fb.getAllSubTasks());
        System.out.println("-------");

        FileBackendTaskManager.loadFromFile(Path.of("src", "saved_information", "savedTasks.csv"));
        System.out.println(fb.getAllTasks());
        System.out.println(fb.getAllEpics());
        System.out.println(fb.getAllSubTasks());

      /*  TaskManager manager = Managers.getInMemoryTaskManager();

        Task task1 = new Task("Доработать приложение", "Манагер", Status.NEW);
        manager.createTask(task1);
        Task task2 = new Task("Пробежать марафон", "Пушкин-Питер", Status.NEW);
        manager.createTask(task2);

        Epic epic = new Epic("Понедельник", "!!!");
        System.out.println(manager.createEpic(epic));

        SubTask subTask = new SubTask("Налоги", "Позвонить бухгалтеру", Status.NEW, epic.getId());
        System.out.println(manager.createSubTask(subTask));


        System.out.println(manager.getAllTasks());
        System.out.println(manager.getAllEpics());
        System.out.println(manager.getAllSubTasks());*/

    }
}