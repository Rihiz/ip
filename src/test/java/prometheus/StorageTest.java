package prometheus;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StorageTest {
    @Test
    void save_nullTaskList_shouldThrowAssertionError() {
        Storage storage = new Storage("test.txt");
        assertThrows(AssertionError.class, () -> {
            storage.save(null);
        });
    }

    @Test
    void save_nullFilePath_shouldThrowAssertionError() {
        Storage storage = new Storage(null);
        TaskList taskList = new TaskList();
        assertThrows(AssertionError.class, () -> {
            storage.save(taskList);
        });
    }
}