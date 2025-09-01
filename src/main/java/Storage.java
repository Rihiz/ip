// File: src/main/java/Storage.java
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private static final String FILE_PATH = "./data/Prometheus.txt";
    private static final String DIRECTORY_PATH = "./data/";

    public static void saveTasks(ArrayList<Task> tasks) throws PrometheusException {
        try {
            // Create data directory if it doesn't exist
            File directory = new File(DIRECTORY_PATH);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Write tasks to file
            FileWriter writer = new FileWriter(FILE_PATH);
            for (Task task : tasks) {
                writer.write(taskToFileString(task) + System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            throw new PrometheusException("Error saving tasks to file: " + e.getMessage());
        }
    }

    public static ArrayList<Task> loadTasks() throws PrometheusException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(FILE_PATH);

        // Return empty list if file doesn't exist (first run)
        if (!file.exists()) {
            return tasks;
        }

        try {
            List<String> lines = Files.readAllLines(Paths.get(FILE_PATH));
            for (String line : lines) {
                Task task = parseTaskFromString(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
            return tasks;
        } catch (IOException e) {
            throw new PrometheusException("Error loading tasks from file: " + e.getMessage());
        }
    }

    private static String taskToFileString(Task task) {
        if (task instanceof Todo) {
            return "T | " + (task.isDone() ? "1" : "0") + " | " + task.description;
        } else if (task instanceof Deadline) {
            Deadline deadline = (Deadline) task;
            return "D | " + (deadline.isDone() ? "1" : "0") + " | " + deadline.description + " | " + deadline.by;
        } else if (task instanceof Event) {
            Event event = (Event) task;
            return "E | " + (event.isDone() ? "1" : "0") + " | " + event.description + " | " + event.from + " | " + event.to;
        }
        return "";
    }

    private static Task parseTaskFromString(String line) {
        try {
            String[] parts = line.split(" \\| ");
            if (parts.length < 3) return null;

            String type = parts[0].trim();
            boolean isDone = parts[1].trim().equals("1");
            String description = parts[2].trim();

            Task task = null;
            switch (type) {
                case "T":
                    task = new Todo(description);
                    break;
                case "D":
                    if (parts.length >= 4) {
                        task = new Deadline(description, parts[3].trim());
                    }
                    break;
                case "E":
                    if (parts.length >= 5) {
                        task = new Event(description, parts[3].trim(), parts[4].trim());
                    }
                    break;
            }

            if (task != null && isDone) {
                task.markAsDone();
            }
            return task;
        } catch (Exception e) {
            return null; // Skip malformed lines
        }
    }
}