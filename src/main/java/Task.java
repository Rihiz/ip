public class Task {
    protected String description;
    protected boolean isDone;
    protected TaskType type;

    public Task(String desciption, TaskType type) {
        this.description = desciption;
        this.isDone = false;
        this.type = type;
    }

    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsNotDone() {
        this.isDone = false;
    }

    public boolean isDone() {
        return isDone;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + this.description;
    }

}
