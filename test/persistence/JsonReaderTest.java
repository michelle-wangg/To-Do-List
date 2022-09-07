package persistence;

import model.Task;
import model.ToDoList;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// This class references code from JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

// Unit tests for JsonReader
public class JsonReaderTest extends JsonTest {
    @Test
    void readerNonExistentTest() {
        JsonReader reader = new JsonReader("./data/readerNonExistentTest.json");
        try {
            ToDoList toDoList = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass - expected outcome
        }
    }

    @Test
    void readerEmptyToDoListTest() {
        JsonReader reader = new JsonReader("./data/readerEmptyToDoListTest.json");
        try {
            ToDoList toDoList = reader.read();
            assertEquals(0, toDoList.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void readerToDoListWithTasksTest() {
        Task task1 = new Task("Finish CPSC 210 Homework");
        Task task2 = new Task("Complete WebWork 1");
        Task task3 = new Task("Finish watching Math Lecture");
        task2.markAsComplete();
        JsonReader reader = new JsonReader("./data/writerToDoListWithTasksTest.json");
        try {
            ToDoList toDoList = reader.read();
            assertEquals(3,toDoList.size());
            checkTask(task1.getTaskDescription(),task1.isCompleted(),
                    toDoList.getTaskFromDescription("Finish CPSC 210 Homework"));
            checkTask(task2.getTaskDescription(),task2.isCompleted(),
                    toDoList.getTaskFromDescription("Complete WebWork 1"));
            checkTask(task3.getTaskDescription(),task3.isCompleted(),
                    toDoList.getTaskFromDescription("Finish watching Math Lecture"));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
