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
import java.util.Map;

public class AddTaskHandler implements RequestHandler, HttpHandler {
    private final TaskManager taskManager;

    public AddTaskHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            Map<String, String> formData = FormParser.parse(exchange);
            String title = formData.get("title");
            String description = formData.get("description");
            TaskType type = TaskType.valueOf(formData.get("type"));
            LocalDate date = LocalDate.parse(formData.get("date"));

            Task task = new Task(title, description, type, date);
            taskManager.addTask(task);

            exchange.getResponseHeaders().set("Location", "/tasks?date=" + date);
            exchange.sendResponseHeaders(303, -1);
        } else {
            RenderTemplate.renderTemplate(exchange, "addTask.ftlh", Map.of("date", exchange.getRequestURI().getQuery().split("=")[1]));
        }
    }
}
