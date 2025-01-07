package server.handler;

import com.sun.net.httpserver.HttpExchange;
import manager.TaskManager;

import java.io.IOException;

public class GetHandler extends BaseHttpHandler {

    protected GetHandler(TaskManager taskManager) {
        super(taskManager);
    }

    protected void getHandler(HttpExchange exchange) throws IOException {
        String[] path = exchange.getRequestURI().getPath().split("/");

        switch (path.length) {
            case 2 -> {
                try {
                    switch (path[1]) {
                        case "tasks":
                            sendText(exchange, gson.toJson(taskManager.getAllTasks()), 200);
                        case "subtasks":
                            sendText(exchange, gson.toJson(taskManager.getAllSubTasks()), 200);
                        case "epics":
                            sendText(exchange, gson.toJson(taskManager.getAllEpics()), 200);
                        case "history":
                            sendText(exchange, gson.toJson(taskManager.getHistory()), 200);
                        case "prioritized":
                            sendText(exchange, gson.toJson(taskManager.getPrioritizedTasks()), 200);
                        default:
                            sendNotFound(exchange);
                    }
                } catch (IllegalArgumentException e) {
                    exchange.sendResponseHeaders(400, 0);
                    exchange.close();
                }
            }
            case 3 -> {
                try {
                    int id = Integer.parseInt(path[2]);
                    switch (path[1]) {
                        case "tasks":
                            if (taskManager.getTaskById(id) == null) {
                                sendNotFound(exchange);
                            } else {
                                sendText(exchange, gson.toJson(taskManager.getTaskById(id)),
                                        200);
                            }

                        case "subtasks":
                            if (taskManager.getSubTaskById(id) == null) {
                                sendNotFound(exchange);
                            } else {
                                sendText(exchange, gson.toJson(taskManager.getSubTaskById(id)),
                                        200);
                            }
                        case "epics":
                            if (taskManager.getEpicById(id) == null) {
                                sendNotFound(exchange);
                            } else {
                                sendText(exchange, gson.toJson(taskManager.getEpicById(id)),
                                        200);
                            }
                        default:
                            sendNotFound(exchange);
                    }
                } catch (IllegalArgumentException exp) {
                    exchange.sendResponseHeaders(400, 0);
                    exchange.close();
                }
            }
            case 4 -> {
                int id = Integer.parseInt(path[2]);
                switch (path[1]) {
                    case "epics":
                        if (taskManager.getEpicById(id) == null) {
                            sendNotFound(exchange);
                        } else {
                            sendText(exchange, gson.toJson(taskManager.getAllSubTaskByEpicId(id)),
                                    200);
                        }
                    default:
                        sendNotFound(exchange);
                }
            }
        }
    }
}
