package prometheus.command;

import prometheus.PrometheusException;
import prometheus.task.Task;
import prometheus.task.Todo;
import prometheus.task.Deadline;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AddCommandTest {

    @Test
    public void createTask_todoCommand_returnsTodo() throws PrometheusException {
        AddCommand addCommand = new AddCommand("todo", "read book");
        Task task = addCommand.createTask();

        assertInstanceOf(Todo.class, task);
        assertEquals("read book", task.getDescription());
    }

    @Test
    public void createTask_deadlineCommand_returnsDeadline() throws PrometheusException {
        AddCommand addCommand = new AddCommand("deadline", "return book /by 2023-12-25 1800");
        Task task = addCommand.createTask();

        assertInstanceOf(Deadline.class, task);
        assertEquals("return book", task.getDescription());
    }

    @Test
    public void createTask_emptyTodoDescription_throwsException() {
        AddCommand addCommand = new AddCommand("todo", "");

        assertThrows(PrometheusException.class, addCommand::createTask);
    }
}