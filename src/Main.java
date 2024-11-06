import manager.Managers;
import manager.TaskManager;
import status.Status;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

public class Main {

    public static void main(String[] args) {
        TaskManager manager = Managers.getInMemoryTaskManager();

        Task task1 = new Task("Доработать приложение", "Манагер", Status.NEW);
        manager.createTask(task1);
        Task task2 = new Task("Пробежать марафон", "Пушкин-Питер", Status.NEW);
        manager.createTask(task2);

        Epic epic = new Epic("Понедельник", "!!!");
        System.out.println(manager.createEpic(epic));

        SubTask subTask = new SubTask("Налоги", "Позвонить бухгалтеру", Status.NEW, epic.getId());
        System.out.println(manager.createSubTask(subTask));

        System.out.println(manager.getTaskById(task1.getId()));
        System.out.println(manager.getTaskById(task2.getId()));

    }
}