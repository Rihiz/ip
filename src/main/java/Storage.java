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
                if (!directory.mkdirs()) {
                    throw new PrometheusException("Failed to create data directory");
                }
            }

            // Write tasks to file using their own serialization
            try (FileWriter writer = new FileWriter(FILE_PATH)) {
                for (Task task : tasks) {
                    writer.write(task.toFileString() + System.lineSeparator());
                }
            }
        } catch (IOException e) {
            throw new PrometheusException("Error saving tasks to file: " + e.getMessage());
        }
    }

    public static ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(FILE_PATH);

        // Return empty list if file doesn't exist (first run)
        if (!file.exists()) {
            return tasks;
        }

        try {
            List<String> lines = Files.readAllLines(Paths.get(FILE_PATH));
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                try {
                    Task task = Task.fromFileString(line);
                    tasks.add(task);
                } catch (PrometheusException e) {
                    System.err.println("Skipping invalid task at line " + (i + 1) + ": " + e.getMessage());
                    // Continue with other lines
                }
            }
            return tasks;
        } catch (IOException e) {
            System.err.println("Error loading tasks from file: " + e.getMessage());
            return new ArrayList<>(); // Return empty list instead of crashing
        }
    }
}