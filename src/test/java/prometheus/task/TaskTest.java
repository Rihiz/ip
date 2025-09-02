package prometheus.task;

import prometheus.PrometheusException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    @Test
    public void fromFileString_todoString_returnsTodoObject() throws PrometheusException {
        String fileString = "T | 1 | read book";
        Task task = Task.fromFileString(fileString);

        assertInstanceOf(Todo.class, task);
        assertTrue(task.isDone());
        assertEquals("read book", task.getDescription());
    }

    @Test
    public void fromFileString_deadlineString_returnsDeadlineObject() throws PrometheusException {
        String fileString = "D | 0 | return book | 2023-12-25 1800";
        Task task = Task.fromFileString(fileString);

        assertInstanceOf(Deadline.class, task);
        assertFalse(task.isDone());
        assertEquals("return book", task.getDescription());
    }

    @Test
    public void fromFileString_invalidString_throwsPrometheusException() {
        String invalidFileString = "Invalid format";

        assertThrows(PrometheusException.class, () -> {
            Task.fromFileString(invalidFileString);
        });
    }
}