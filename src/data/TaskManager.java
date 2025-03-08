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
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()) // Регистрируем адаптер
                .create();
        loadTasks();
    }

    public void addTask(Task task) {
        tasksByDate.computeIfAbsent(task.getDate(), k -> new ArrayList<>()).add(task);
        saveTasks();
    }

    public void removeTask(Task task) {
        List<Task> tasks = tasksByDate.get(task.getDate());
        if (tasks != null) {
            tasks.remove(task);
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
