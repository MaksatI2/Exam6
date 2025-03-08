package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.BasicServer;
import template.RenderTemplate;

import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarHandler implements RequestHandler, HttpHandler {
    private final BasicServer server;

    public CalendarHandler(BasicServer server) {
        this.server = server;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        LocalDate today = LocalDate.now();
        YearMonth yearMonth = YearMonth.from(today);
        LocalDate firstDayOfMonth = yearMonth.atDay(1);
        LocalDate lastDayOfMonth = yearMonth.atEndOfMonth();

        List<LocalDate> daysInMonth = firstDayOfMonth.datesUntil(lastDayOfMonth.plusDays(1)).toList();

        Map<String, Object> data = new HashMap<>();
        data.put("today", today);
        data.put("daysInMonth", daysInMonth);
        data.put("tasksByDate", server.getTaskManager());

        RenderTemplate.renderTemplate(exchange, "calendar.ftlh", data);
    }
}

