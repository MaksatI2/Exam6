package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import data.TaskManager;
import model.Task;
import template.RenderTemplate;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class TaskListHandler implements RequestHandler, HttpHandler {
    private final TaskManager taskManager;

    public TaskListHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        LocalDate date = LocalDate.parse(query.split("=")[1]);

        List<Task> tasks = taskManager.getTasksByDate(date);
        Map<String, Object> data = Map.of(
                "date", date,
                "tasks", tasks
        );

        RenderTemplate.renderTemplate(exchange, "taskList.ftlh", data);
    }
}
