package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.BasicServer;
import server.ResponseCodes;
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
        String path = exchange.getRequestURI().getPath();

        if (!"/calendar".equals(path) && path.startsWith("/calendar/")) {
            RenderTemplate.sendErrorResponse(exchange, ResponseCodes.NOT_FOUND, "404 NOT FOUND");
            return;
        }

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
