package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for ToDoList
class ToDoListTest {
    private ToDoList toDoList;

    @BeforeEach
    void runBefore() {
        toDoList = new ToDoList();
    }

    @Test
    public void addTaskTest() {
        Task task1 = new Task("Finish CPSC 210 Homework");

        assertEquals(0, toDoList.size());

        toDoList.addTask(task1);

        assertTrue(toDoList.contains(task1));
        assertEquals(1,toDoList.size());

        toDoList.addTask(task1);
        assertTrue(toDoList.contains(task1));
        assertEquals(1,toDoList.size());
    }

    @Test
    public void deleteTaskTest() {
        Task task1 = new Task("Finish CPSC 210 Homework");
        Task task2 = new Task("Complete WebWork 1");
        Task task3 = new Task("Finish watching Math Lecture");

        toDoList.addTask(task1);
        toDoList.addTask(task2);
        toDoList.addTask(task3);

        assertEquals(3, toDoList.size());

        toDoList.deleteTask(task1);

        assertEquals(2, toDoList.size());
        assertFalse(toDoList.contains(task1));
        assertTrue(toDoList.contains(task2));
        assertTrue(toDoList.contains(task3));
    }

    @Test
    public void containsTest() {
        Task task1 = new Task("Finish CPSC 210 Homework");
        Task task2 = new Task("Complete WebWork 1");
        Task task3 = new Task("Finish watching Math Lecture");


        assertFalse(toDoList.contains(task1));
        toDoList.addTask(task1);
        assertTrue(toDoList.contains(task1));
        assertFalse(toDoList.contains(task2));
        assertFalse(toDoList.contains(task3));

        toDoList.addTask(task2);
        assertTrue(toDoList.contains(task1));
        assertTrue(toDoList.contains(task2));
        assertFalse(toDoList.contains(task3));

        toDoList.addTask(task3);
        assertTrue(toDoList.contains(task1));
        assertTrue(toDoList.contains(task2));
        assertTrue(toDoList.contains(task3));
    }

    @Test
    public void sizeTest() {
        Task task1 = new Task("Finish CPSC 210 Homework");
        Task task2 = new Task("Complete WebWork 1");
        Task task3 = new Task("Finish watching Math Lecture");

        assertEquals(0, toDoList.size());

        toDoList.addTask(task1);
        toDoList.addTask(task2);
        toDoList.addTask(task3);

        assertEquals(3,toDoList.size());

    }

    @Test
    public void getTest() {
        Task task1 = new Task("Finish CPSC 210 Homework");
        Task task2 = new Task("Complete WebWork 1");
        Task task3 = new Task("Finish watching Math Lecture");

        toDoList.addTask(task1);
        toDoList.addTask(task2);
        toDoList.addTask(task3);

        assertEquals(task1, toDoList.get(0));
        assertEquals(task2, toDoList.get(1));
        assertEquals(task3, toDoList.get(2));

    }

    @Test
    public void getIndexTest() {
        Task task1 = new Task("Finish CPSC 210 Homework");
        Task task2 = new Task("Complete WebWork 1");
        Task task3 = new Task("Finish watching Math Lecture");

        toDoList.addTask(task1);
        toDoList.addTask(task2);
        toDoList.addTask(task3);

        assertEquals(0, toDoList.getIndex(task1));
        assertEquals(1, toDoList.getIndex(task2));
        assertEquals(2, toDoList.getIndex(task3));
    }



    @Test
    public void getTaskFromDescriptionTest() {
        Task task1 = new Task("Finish CPSC 210 Homework");
        Task task2 = new Task("Complete WebWork 1");
        Task task3 = new Task("Finish watching Math Lecture");

        String task1Description = "Finish CPSC 210 Homework";
        String task2Description = "Complete WebWork 1";
        String task3Description = "Finish watching Math Lecture";
        String taskDescriptionNotInToDoList = "Cook dinner";

        toDoList.addTask(task1);
        toDoList.addTask(task2);
        toDoList.addTask(task3);

        assertEquals(task1,toDoList.getTaskFromDescription(task1Description));
        assertEquals(task2,toDoList.getTaskFromDescription(task2Description));
        assertEquals(task3,toDoList.getTaskFromDescription(task3Description));
        assertNull(toDoList.getTaskFromDescription(taskDescriptionNotInToDoList));

    }

    @Test
    void containsDescriptionTest() {
        Task task1 = new Task("Finish CPSC 210 Homework");
        Task task2 = new Task("Complete WebWork 1");
        Task task3 = new Task("Finish watching Math Lecture");

        String task1Description = "Finish CPSC 210 Homework";
        String task2Description = "Complete WebWork 1";
        String task3Description = "Finish watching Math Lecture";
        String taskDescriptionNotInToDoList = "Cook dinner";

        toDoList.addTask(task1);
        toDoList.addTask(task2);
        toDoList.addTask(task3);

        assertTrue(toDoList.containsDescription(task1Description));
        assertTrue(toDoList.containsDescription(task2Description));
        assertTrue(toDoList.containsDescription(task3Description));
        assertFalse(toDoList.containsDescription(taskDescriptionNotInToDoList));
    }




}