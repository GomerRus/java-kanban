package server.handler;

import com.sun.net.httpserver.HttpExchange;
import manager.TaskManager;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.io.IOException;

public class PostHandler extends BaseHttpHandler {

    protected PostHandler(TaskManager taskManager) {
        super(taskManager);
    }

    protected void postHandler(HttpExchange exchange) throws IOException {
        String[] path = exchange.getRequestURI().getPath().split("/");

        switch (path.length) {
            case 2 -> {
                try {
                    String strRequest = exchange.getRequestBody().readAllBytes().toString();
                    int id = Integer.parseInt(path[2]);
                    switch (path[1]) {
                        case "tasks":
                            if (taskManager.getTaskById(id) == null) {
                                sendHasInteractions(exchange);
                            } else {
                                taskManager.createTask(gson.fromJson(strRequest,
                                        Task.class));
                            }
                        case "subtasks":
                            if (taskManager.getSubTaskById(id) == null) {
                                sendHasInteractions(exchange);
                            } else {
                                taskManager.createSubTask(gson.fromJson(strRequest,
                                        SubTask.class));
                            }
                        case "epics":
                            taskManager.createEpic(gson.fromJson(strRequest,
                                    Epic.class));
                        default:
                            sendNotFound(exchange);
                    }
                    exchange.sendResponseHeaders(200, 0);
                    exchange.close();
                } catch (IllegalArgumentException exp) {
                    exchange.sendResponseHeaders(400, 0);
                    exchange.close();
                }
            }
            case 3 -> {
                try {
                    String strRequest = exchange.getRequestBody().readAllBytes().toString();

                    switch (path[1]) {
                        case "tasks" -> taskManager.updateTask(gson.fromJson(strRequest,
                                Task.class));
                        case "subtasks" -> taskManager.updateSubTask(gson.fromJson(strRequest,
                                SubTask.class));
                        case "epics" -> taskManager.updateEpic(gson.fromJson(strRequest,
                                Epic.class));
                        default -> sendNotFound(exchange);
                    }
                    exchange.sendResponseHeaders(200, 0);
                    exchange.close();
                } catch (NumberFormatException exp) {
                    exchange.sendResponseHeaders(400, 0);
                    exchange.close();
                }
            }
        }
    }
}