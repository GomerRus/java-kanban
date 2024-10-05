public class Main {

 public static void main(String[] args) {

  System.out.println("Поехали!");
  TaskManager taskManager = new TaskManager();
  taskManager.createTask(new Task("Task-1", "Описание-1", Status.NEW));
  taskManager.createTask(new Task("Task-2", "Описание-2", Status.NEW));
  taskManager.createEpic(new Epic("Epic-1", "Описание-1"));
  taskManager.createEpic(new Epic("Epic-2", "Описание-2"));
  taskManager.createSubTask(new SubTask("SubTask-1", "Описание-1", Status.NEW, 3));
  taskManager.createSubTask(new SubTask("SubTask-2", "Описание-2", Status.NEW, 3));
  taskManager.createSubTask(new SubTask("SubTask-3", "Описание-3", Status.NEW, 4));

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


 }
}