package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import data.TaskManager;
import handlers.*;
import template.RenderTemplate;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;

public class BasicServer {
    private final TaskManager taskManager = new TaskManager();
    private final HttpServer server;

    public BasicServer(int port) throws IOException {
        server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/calendar", new CalendarHandler(this));
        server.createContext("/tasks", new TaskListHandler(this));
        server.createContext("/tasks/add", new AddTaskHandler(taskManager));
        server.createContext("/tasks/delete", new TaskDeleteHandler(this));

        server.createContext("/", exchange -> {
            String requestPath = exchange.getRequestURI().getPath();

            File file = new File("data" + requestPath);
            if (file.exists() && !file.isDirectory()) {
                new StaticFileHandler("data/").handle(exchange);
                return;
            }

            if ("/".equals(requestPath)) {
                RenderTemplate.renderTemplate(exchange, "root.html", Map.of());
                return;
            }

            RenderTemplate.sendErrorResponse(exchange, ResponseCodes.NOT_FOUND, "404 NOT FOUND");
        });

        server.setExecutor(null);
    }

    public void start() {
        server.start();
        System.out.println("Server started on port http://localhost:" + server.getAddress().getPort());
    }

    public TaskManager getTaskManager() {
        return taskManager;
    }
}
