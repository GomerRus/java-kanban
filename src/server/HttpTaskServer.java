package server;

import com.sun.net.httpserver.HttpServer;
import manager.Managers;
import manager.TaskManager;
import server.handler.BaseHttpHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {
    private final HttpServer server;
    private final TaskManager taskManager;
    private static final int PORT = 8080;

    public HttpTaskServer() throws IOException {
        taskManager = Managers.getInMemoryTaskManager();
        server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/", new BaseHttpHandler(taskManager));
        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
    }

    public static void main(String[] args) {

        try {
            HttpTaskServer server = new HttpTaskServer();
            server.start();
        } catch (IOException exp) {
            throw new RuntimeException(exp);
        }
    }

    private void start() {
        server.start();
    }
}
