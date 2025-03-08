package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.Task;
import server.BasicServer;
import template.RenderTemplate;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskListHandler implements RequestHandler, HttpHandler {
    private final BasicServer server;

    public TaskListHandler(BasicServer server) {
        this.server = server;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        LocalDate today = LocalDate.now();

        String query = exchange.getRequestURI().getQuery();
        LocalDate date = LocalDate.parse(query.split("=")[1]);

        List<Task> tasks = server.getTaskManager().getTasksByDate(date);

        Map<String, Object> data = new HashMap<>();
        data.put("today", today);
        data.put("date", date);
        data.put("tasks", tasks);

        RenderTemplate.renderTemplate(exchange, "taskList.ftlh", data);
    }
}
