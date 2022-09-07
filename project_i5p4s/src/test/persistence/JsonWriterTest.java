package persistence;

import model.Task;
import model.ToDoList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// This class references code from JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

// Unit tests for JsonWriter
public class JsonWriterTest extends JsonTest {
    @Test
    void writerInvalidFileTest() {
        try {
            ToDoList toDoList = new ToDoList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void writerEmptyToDoListTest() {
        try {
            ToDoList toDoList = new ToDoList();
            JsonWriter writer = new JsonWriter("./data/writerEmptyToDoListTest.json");
            writer.open();
            writer.write(toDoList);
            writer.close();

            JsonReader reader = new JsonReader("./data/writerEmptyToDoListTest.json");
            toDoList = reader.read();
            assertEquals(0, toDoList.size());

        } catch (IOException e) {
            fail("Exception should have been thrown");
        }
    }

    @Test
    void writerToDoListWithTasksTest() {
        Task task1 = new Task("Finish CPSC 210 Homework");
        Task task2 = new Task("Complete WebWork 1");
        Task task3 = new Task("Finish watching Math Lecture");
        task2.markAsComplete();

        try {
            ToDoList toDoList = new ToDoList();
            toDoList.addTask(task1);
            toDoList.addTask(task2);
            toDoList.addTask(task3);
            JsonWriter writer = new JsonWriter("./data/writerToDoListWithTasksTest.json");
            writer.open();
            writer.write(toDoList);
            writer.close();

            JsonReader reader = new JsonReader("./data/writerToDoListWithTasksTest.json");
            toDoList = reader.read();
            assertEquals(3,toDoList.size());
            checkTask(task1.getTaskDescription(),task1.isCompleted(),
                    toDoList.getTaskFromDescription("Finish CPSC 210 Homework"));
            checkTask(task2.getTaskDescription(),task2.isCompleted(),
                    toDoList.getTaskFromDescription("Complete WebWork 1"));
            checkTask(task3.getTaskDescription(),task3.isCompleted(),
                    toDoList.getTaskFromDescription("Finish watching Math Lecture"));
        } catch(IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
