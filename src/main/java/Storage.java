import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<Task> load() throws PrometheusException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            return tasks;
        }

        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (int i = 0; i < lines.size(); i++) {
                try {
                    Task task = Task.fromFileString(lines.get(i));
                    tasks.add(task);
                } catch (PrometheusException e) {
                    throw new PrometheusException("Line " + (i + 1) + ": " + e.getMessage());
                }
            }
            return tasks;
        } catch (IOException e) {
            throw new PrometheusException("Failed to load tasks: " + e.getMessage());
        }
    }

    public void save(TaskList tasks) throws PrometheusException {
        try {
            File directory = new File("./data/");
            if (!directory.exists()) {
                directory.mkdirs();
            }

            try (FileWriter writer = new FileWriter(filePath)) {
                for (int i = 0; i < tasks.size(); i++) {
                    Task task = tasks.get(i);
                    writer.write(task.toFileString() + System.lineSeparator());
                }
            }
        } catch (IOException e) {
            throw new PrometheusException("Failed to save tasks: " + e.getMessage());
        }
    }
}