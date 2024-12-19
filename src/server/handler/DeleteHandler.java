package server.handler;

import com.sun.net.httpserver.HttpExchange;
import manager.TaskManager;

import java.io.IOException;

public class DeleteHandler extends BaseHttpHandler {

    protected DeleteHandler(TaskManager taskManager) {
        super(taskManager);
    }

    protected void deleteHandler(HttpExchange exchange) throws IOException {
        String[] path = exchange.getRequestURI().getPath().split("/");
        if (path.length == 3) {
            try {
                int id = Integer.parseInt(path[2]);
                switch (path[1]) {
                    case "tasks":
                        taskManager.deleteTasksById(id);
                    case "subtasks":
                        taskManager.deleteSubTaskById(id);
                    case "epics":
                        taskManager.deleteEpicById(id);
                }
                exchange.sendResponseHeaders(200, 0);
                exchange.close();
            } catch (NumberFormatException exp) {
                exchange.sendResponseHeaders(400, 0);
                exchange.close();
            }
        } else {
            sendNotFound(exchange);
        }
    }
}
