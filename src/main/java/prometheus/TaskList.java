package prometheus;// File: src/main/java/prometheus.TaskList.java

import prometheus.task.Task;

import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task remove(int index) throws PrometheusException {
        if (index < 0 || index >= tasks.size()) {
            throw new PrometheusException("Invalid task index!");
        }
        return tasks.remove(index);
    }

    public Task get(int index) throws PrometheusException {
        if (index < 0 || index >= tasks.size()) {
            throw new PrometheusException("Invalid task index!");
        }
        return tasks.get(index);
    }

    public int size() {
        return tasks.size();
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }
}