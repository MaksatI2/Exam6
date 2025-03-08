package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import data.TaskManager;
import enums.TaskType;
import model.Task;
import template.RenderTemplate;
import utils.FormParser;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Map;

public class AddTaskHandler implements RequestHandler, HttpHandler {
    private final TaskManager taskManager;

    public AddTaskHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("POST")) {
                Map<String, String> formData = FormParser.parse(exchange);
                String title = formData.get("title");
                String description = formData.get("description");
                TaskType type = TaskType.valueOf(formData.get("type"));
                LocalDate date = LocalDate.parse(formData.get("date"));
                String id = taskManager.generateNextId();

                Task task = new Task(id, title, description, type, date);
                taskManager.addTask(task);

                exchange.getResponseHeaders().set("Location", "/tasks?date=" + date);
                exchange.sendResponseHeaders(303, -1);
            } else {
                String query = exchange.getRequestURI().getQuery();
                if (query == null || !query.startsWith("date=")) {
                    throw new IllegalArgumentException("Invalid URL: 'date' parameter missing");
                }
                String dateParam = query.split("=")[1];
                LocalDate date;
                try {
                    date = LocalDate.parse(dateParam);
                } catch (DateTimeParseException e) {
                    throw new IllegalArgumentException("Invalid date format in URL: " + dateParam);
                }

                RenderTemplate.renderTemplate(exchange, "addTask.ftlh", Map.of("date", date));
            }
        } catch (IllegalArgumentException | DateTimeParseException e) {
            String errorMessage = "Error: " + e.getMessage();
            exchange.getResponseHeaders().set("Content-Type", "text/plain");
            exchange.sendResponseHeaders(400, errorMessage.getBytes().length);
            exchange.getResponseBody().write(errorMessage.getBytes());
            exchange.getResponseBody().close();
        }
    }
}
