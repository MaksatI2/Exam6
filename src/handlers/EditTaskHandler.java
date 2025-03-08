package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import data.TaskManager;
import model.Task;
import server.ResponseCodes;
import template.RenderTemplate;
import utils.FormParser;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditTaskHandler implements HttpHandler {
    private final TaskManager taskManager;

    public EditTaskHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();

        if ("GET".equalsIgnoreCase(method)) {
            showEditPage(exchange);
        } else if ("POST".equalsIgnoreCase(method)) {
            processEditTask(exchange);
        } else {
            RenderTemplate.sendErrorResponse(exchange, ResponseCodes.NOT_FOUND, "405 Method Not Allowed");
        }
    }

    private void showEditPage(HttpExchange exchange) throws IOException {
        URI requestUri = exchange.getRequestURI();
        String query = requestUri.getQuery();

        if (query == null || !query.contains("id=") || !query.contains("date=")) {
            RenderTemplate.sendErrorResponse(exchange, ResponseCodes.NOT_FOUND, "400 BAD REQUEST: Missing 'id' or 'date' parameter");
            return;
        }

        String[] params = query.split("&");
        String taskId = params[0].split("=")[1];
        LocalDate date = LocalDate.parse(params[1].split("=")[1]);

        List<Task> tasks = taskManager.getTasksByDate(date);
        Task taskToEdit = null;

        for (Task task : tasks) {
            if (task.getId().equals(taskId)) {
                taskToEdit = task;
                break;
            }
        }

        if (taskToEdit == null) {
            RenderTemplate.sendErrorResponse(exchange, ResponseCodes.NOT_FOUND, "404 Task Not Found");
            return;
        }

        Map<String, Object> data = new HashMap<>();
        data.put("task", taskToEdit);

        RenderTemplate.renderTemplate(exchange, "editTask.ftlh", data);
    }

    private void processEditTask(HttpExchange exchange) throws IOException {
        Map<String, String> formData = FormParser.parse(exchange);

        String taskId = formData.get("id");
        String dateString = formData.get("date");
        String title = formData.get("title");
        String description = formData.get("description");
        String type = formData.get("type");

        if (taskId == null || dateString == null || title == null || title.isEmpty()) {
            RenderTemplate.sendErrorResponse(exchange, ResponseCodes.NOT_FOUND, "400 NOT FOUND: Missing required fields");
            return;
        }

        LocalDate date = LocalDate.parse(dateString);
        taskManager.updateTask(taskId, date, title, description, type);

        exchange.getResponseHeaders().set("Location", "/tasks?date=" + date);
        exchange.sendResponseHeaders(302, -1);
    }
}

