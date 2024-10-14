import manager.Managers;
import manager.TaskManager;
import status.Status;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

public class Main {

 public static void main(String[] args) {




/*TaskManager taskManager = Managers.getInMemoryTaskManager(Managers.getDefaultHistory());
  System.out.println("Поехали!");
  taskManager.createTask(new Task("tasks.tasks.Task-1", "Описание-1", Status.NEW));
  taskManager.createTask(new Task("tasks.tasks.Task-2", "Описание-2", Status.NEW));
  taskManager.createEpic(new Epic("tasks.tasks.Epic-1", "Описание-1"));
  taskManager.createEpic(new Epic("tasks.tasks.Epic-2", "Описание-2"));
  taskManager.createSubTask(new SubTask("tasks.tasks.SubTask-1", "Описание-1", Status.NEW, 3));
  taskManager.createSubTask(new SubTask("tasks.tasks.SubTask-2", "Описание-2", Status.NEW, 3));
  taskManager.createSubTask(new SubTask("tasks.tasks.SubTask-3", "Описание-3", Status.NEW, 4));

  System.out.println("Выводим");
  taskManager.getAllTasks();
  taskManager.getAllEpics();
  taskManager.getAllSubTasks();
  System.out.println("");

  System.out.println("Достаем по одному");
  SubTask subTask = taskManager.getSubTaskById(5);
  System.out.println("Достали подзадачу " + subTask);
  System.out.println("");
  subTask.setStatus(Status.IN_PROGRESS);
  taskManager.updateSubTask(subTask);
  System.out.println("Обновленная подзадача" + subTask);
  System.out.println("");
  Epic epic3 = taskManager.getEpicById(3);
  System.out.println("Достали Эпик по ID c изменившимся статусом " + epic3);

  epic3.setDescription("Описание ремонта");
  epic3.setNameTask("Ремонт");
  taskManager.updateEpic(epic3);
  System.out.println("Обновленная подзадача" + epic3);

*/
 }
}