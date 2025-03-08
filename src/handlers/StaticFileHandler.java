package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.ResponseCodes;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class StaticFileHandler implements HttpHandler {
    private final String basePath;

    public StaticFileHandler(String basePath) {
        this.basePath = basePath;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String requestPath = exchange.getRequestURI().getPath();
            File file = new File(basePath + requestPath);

            if (!file.exists() || file.isDirectory()) {
                exchange.sendResponseHeaders(ResponseCodes.NOT_FOUND.getCode(), -1);
                return;
            }

            // Устанавливаем Content-Type в зависимости от файла
            String mimeType = Files.probeContentType(Path.of(file.getAbsolutePath()));
            if (mimeType != null) {
                exchange.getResponseHeaders().set("Content-Type", mimeType);
            } else {
                exchange.getResponseHeaders().set("Content-Type", "application/octet-stream");
            }

            // Отправляем файл
            exchange.sendResponseHeaders(200, file.length());
            try (OutputStream os = exchange.getResponseBody()) {
                Files.copy(Path.of(file.getAbsolutePath()), os);
            }
        } catch (Exception e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(ResponseCodes.NOT_FOUND.getCode(), -1);
        }
    }
}

