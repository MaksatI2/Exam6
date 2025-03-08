package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.BasicServer;
import utils.FormParser;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

public class TaskDeleteHandler implements HttpHandler {
    private final BasicServer server;

    public TaskDeleteHandler(BasicServer server) {
        this.server = server;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            Map<String, String> formData = FormParser.parse(exchange);

            String taskId = formData.get("taskId");
            LocalDate date = LocalDate.parse(formData.get("date"));

            server.getTaskManager().removeTaskById(taskId, date);

            String redirectUrl = "/tasks?date=" + date;
            exchange.getResponseHeaders().set("Location", redirectUrl);
            exchange.sendResponseHeaders(303, -1);
        }
    }
}
