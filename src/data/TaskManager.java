package data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Task;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.*;

public class TaskManager {
    private static final String FILE_PATH = "data/Json/Tasks.json";
    private Map<LocalDate, List<Task>> tasksByDate;
    private final Gson gson;

    public TaskManager() {
        tasksByDate = new HashMap<>();
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        loadTasks();
    }

    public String generateNextId() {
        int maxId = 0;
        for (List<Task> tasks : tasksByDate.values()) {
            for (Task task : tasks) {
                try {
                    int taskId = Integer.parseInt(task.getId());
                    if (taskId > maxId) {
                        maxId = taskId;
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        return String.valueOf(maxId + 1);  // Return the next available ID
    }

    public void addTask(Task task) {
        String newId = generateNextId();
        task.setId(newId);

        tasksByDate.computeIfAbsent(task.getDate(), k -> new ArrayList<>()).add(task);
        saveTasks();
    }

    public void removeTaskById(String taskId, LocalDate date) {
        List<Task> tasks = tasksByDate.get(date);
        if (tasks != null) {
            tasks.removeIf(task -> task.getId().equals(taskId));
            saveTasks();
        }
    }

    public List<Task> getTasksByDate(LocalDate date) {
        return tasksByDate.getOrDefault(date, new ArrayList<>());
    }

    private void saveTasks() {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(tasksByDate, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadTasks() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type type = new TypeToken<Map<LocalDate, List<Task>>>() {}.getType();
            tasksByDate = gson.fromJson(reader, type);
            if (tasksByDate == null) {
                tasksByDate = new HashMap<>();
            }
        } catch (IOException e) {
            System.out.println("Tasks.json not found, starting with empty task list.");
        }
    }
}
