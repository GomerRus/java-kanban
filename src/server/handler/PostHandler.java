package server.handler;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import manager.TaskManager;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


public class PostHandler extends BaseHttpHandler {

    protected PostHandler(TaskManager taskManager) {
        super(taskManager);
    }

    protected void postHandler(HttpExchange exchange) throws IOException {
        String[] path = exchange.getRequestURI().getPath().split("/");
        String response;
        int statusCode;
        try {
            String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            JsonElement jsonElement = JsonParser.parseString(body);
            if (!jsonElement.isJsonObject()) {
                System.out.println("Ответ от сервера не соответствует ожидаемому.");
                return;
            }

            switch (path[1]) {
                case "tasks":
                    Task task = gson.fromJson(jsonElement, Task.class);
                    if (task.getId() == 0) {
                        taskManager.createTask(task);
                        statusCode = 201;
                        response = String.format("Task успешно создали.Task ID = %s.", task.getId());
                    } else {
                        Task updateTask = taskManager.getTaskById(task.getId());
                        if (updateTask == null) {
                            statusCode = 404;
                            response = String.format("Task с ID = %s не найдена", task.getId());
                        } else {
                            taskManager.updateTask(updateTask);
                            statusCode = 201;
                            response = "Task успешно обновлен";
                        }
                        sendText(exchange, response, statusCode);
                    }
                case "subtasks":
                    SubTask subTask = gson.fromJson(jsonElement, SubTask.class);
                    if (subTask.getId() == 0) {
                        taskManager.createSubTask(subTask);
                        statusCode = 201;
                        response = String.format("SubTask успешно создали.Task ID = %s.", subTask.getId());
                    } else {
                        SubTask updateSubTask = taskManager.getSubTaskById(subTask.getId());
                        if (updateSubTask == null) {
                            statusCode = 404;
                            response = String.format("SubTask с ID = %s не найдена", subTask.getId());
                        } else {
                            taskManager.updateSubTask(updateSubTask);
                            statusCode = 201;
                            response = "SubTask успешно обновлен";
                        }
                        sendText(exchange, response, statusCode);
                    }
                case "epics":
                    Epic epic = gson.fromJson(jsonElement, Epic.class);
                    if (epic.getId() == 0) {
                        taskManager.createEpic(epic);
                        statusCode = 201;
                        response = String.format("Epic  успешно создали.Task ID = %s.", epic.getId());
                    } else {
                        Epic updateEpic = taskManager.getEpicById(epic.getId());
                        if (updateEpic == null) {
                            statusCode = 404;
                            response = String.format("Epic с ID = %s не найдена", epic.getId());
                        } else {
                            Epic newEpic = new Epic(epic.getName(), epic.getDescription());
                            taskManager.updateEpic(newEpic);
                            statusCode = 201;
                            response = "Epic обновлен";
                        }
                    }
                    sendText(exchange, response, statusCode);
                default:
                    sendHasInteractions(exchange);
            }
        } catch (IllegalArgumentException e) {
            exchange.sendResponseHeaders(400, 0);
            exchange.close();
        }
    }
}