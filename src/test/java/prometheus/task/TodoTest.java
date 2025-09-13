package prometheus.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TodoTest {

    @Test
    public void toFileString_todoObject_returnsCorrectFormat() {
        Todo todo = new Todo("read book");
        todo.markAsDone();

        String result = todo.toFileString();
        assertEquals("T | 1 | 1 | read book", result);
    }

    @Test
    public void toString_todoObject_returnsCorrectFormat() {
        Todo todo = new Todo("read book");
        todo.markAsDone();

        String result = todo.toString();
        assertEquals("[T][X] read book", result);
    }

    @Test
    public void constructor_validDescription_createsTodo() {
        Todo todo = new Todo("read book");

        assertEquals("read book", todo.getDescription());
        assertFalse(todo.isDone());
    }
}