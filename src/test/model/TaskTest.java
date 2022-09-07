package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for Task
class TaskTest {
    private Task task;

    @BeforeEach
    void runBefore() {
        task = new Task("Finish CPSC 210 Homework");
    }

    @Test
    void constructorTest() {
        assertEquals("Finish CPSC 210 Homework", task.getTaskDescription());
        assertFalse(task.isCompleted());
    }

    @Test
    void getTaskDescriptionTest() {
        assertEquals("Finish CPSC 210 Homework", task.getTaskDescription());
    }

    @Test
    void isCompletedTest() {
        assertFalse(task.isCompleted());

        task.markAsComplete();
        assertTrue(task.isCompleted());
    }

    @Test
    void markAsCompleteTest() {
        assertFalse(task.isCompleted());

        task.markAsComplete();
        assertTrue(task.isCompleted());

    }

    @Test
    void markAsIncompleteTest() {
        task.markAsComplete();
        assertTrue(task.isCompleted());

        task.markAsIncomplete();
        assertFalse(task.isCompleted());

    }

    @Test
    void taskStatusAsStringTest() {
        assertEquals("[NOT COMPLETED]", task.taskStatusAsString());

        task.markAsComplete();
        assertEquals("[COMPLETED]", task.taskStatusAsString());
    }




}
