package prometheus;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import prometheus.task.Task;

/**
 * Handles the persistence of tasks to and from a file storage.
 * This class manages reading and writing task data to a specified file path,
 * allowing the application to maintain task data between sessions.
 */
public class Storage {
    private final String filePath;

    /**
     * Constructs a new Storage instance with the specified file path.
     *
     * @param filePath The path to the file where tasks will be stored
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the storage file into memory.
     * If the file doesn't exist, returns an empty task list.
     * Each line in the file is converted to a Task object.
     *
     * @return ArrayList of Task objects loaded from the file
     * @throws PrometheusException If there's an error reading the file or parsing its contents
     */
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

    /**
     * Saves the current list of tasks to the storage file.
     * Overwrites any existing data in the file.
     *
     * @param tasks The TaskList object containing tasks to be saved
     * @throws PrometheusException If there's an error writing to the file
     */
    public void save(TaskList tasks) throws PrometheusException {
        try {
            File directory = new File("./data/");
            if (!directory.exists() && !directory.mkdirs()) {
                throw new PrometheusException("Failed to create directory: " + directory.getPath());
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
