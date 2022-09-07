package persistence;

import model.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;

// This class references code from JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonTest {
    protected void checkTask(String taskDescription, Boolean isCompleted, Task task) {
        assertEquals(taskDescription, task.getTaskDescription());
        assertEquals(isCompleted,task.isCompleted());
    }
}
