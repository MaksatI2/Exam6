package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import model.Task;
import data.TaskManager;
import enums.TaskType;
import template.RenderTemplate;
import utils.FormParser;
import handlers.StaticFileHandler;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasicServer {
    private final TaskManager taskManager = new TaskManager();
    private final HttpServer server;

    public BasicServer(int port) throws IOException {
        server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/", exchange -> {
            String requestPath = exchange.getRequestURI().getPath();

            File file = new File("data" + requestPath);
            if (file.exists() && !file.isDirectory()) {
                new StaticFileHandler("data/").handle(exchange);
                return;
            }

            if ("/".equals(requestPath)) {
                handleRoot(exchange);
            } else {
                RenderTemplate.sendErrorResponse(exchange, ResponseCodes.NOT_FOUND, "404 NOT FOUND");
            }
        });

        server.createContext("/tasks", this::handleTasks);
        server.setExecutor(null);
    }

    public void start() {
        server.start();
        System.out.println("Server started on port http://localhost:" + server.getAddress().getPort());
    }

    private void handleRoot(HttpExchange exchange) throws IOException {
        LocalDate today = LocalDate.now();
        YearMonth yearMonth = YearMonth.from(today);
        LocalDate firstDayOfMonth = yearMonth.atDay(1);
        LocalDate lastDayOfMonth = yearMonth.atEndOfMonth();

        List<LocalDate> daysInMonth = firstDayOfMonth.datesUntil(lastDayOfMonth.plusDays(1)).toList();

        Map<String, Object> data = new HashMap<>();
        data.put("today", today);
        data.put("daysInMonth", daysInMonth);
        data.put("tasksByDate", taskManager);

        RenderTemplate.renderTemplate(exchange, "calendar.ftlh", data);
    }

    private void handleTasks(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        if (path.equals("/tasks/add")) {
            handleAddTask(exchange);
        } else {
            handleTaskList(exchange);
        }
    }

    private void handleTaskList(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        LocalDate date = LocalDate.parse(query.split("=")[1]);

        List<Task> tasks = taskManager.getTasksByDate(date);
        Map<String, Object> data = new HashMap<>();
        data.put("date", date);
        data.put("tasks", tasks);

        RenderTemplate.renderTemplate(exchange, "task_list.ftl", data);
    }

    private void handleAddTask(HttpExchange exchange) throws IOException {
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
            RenderTemplate.renderTemplate(exchange, "add_task.ftlh", new HashMap<>());
        }
    }
}
