package server.handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.Managers;
import manager.TaskManager;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class BaseHttpHandler implements HttpHandler {

    protected TaskManager taskManager;
    protected Gson gson = Managers.getDefaultGson();

    public BaseHttpHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange exchange) {
        try {
            String requestMethod = exchange.getRequestMethod();
            switch (requestMethod) {
                case "GET":
                    getHandler(exchange);
                case "POST":
                    postHandler(exchange);
                case "DELETE":
                    deleteHandler(exchange);
                default:
                    sendText(exchange, "Такого метода нет", 405);
            }
        } catch (IOException exp) {
            throw new RuntimeException();
        }
    }

    private void getHandler(HttpExchange exchange) throws IOException {
        new GetHandler(taskManager).getHandler(exchange);
    }

    private void postHandler(HttpExchange exchange) throws IOException {
        new PostHandler(taskManager).postHandler(exchange);
    }

    private void deleteHandler(HttpExchange exchange) throws IOException {
        new DeleteHandler(taskManager).deleteHandler(exchange);
    }

    protected void sendText(HttpExchange exchange, String responseString,
                            int responceCode) throws IOException {
        byte[] resp = responseString.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        exchange.sendResponseHeaders(responceCode, resp.length);
        exchange.getResponseBody().write(resp);
        exchange.close();
    }

    protected void sendNotFound(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(404, 0);
        exchange.close();
    }

    protected void sendHasInteractions(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(406, 0);
        exchange.close();
    }
}


