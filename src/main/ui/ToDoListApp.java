package ui;

import model.Task;
import model.ToDoList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// to-do list application
public class ToDoListApp {
    private static final String JSON_STORE = "./data/todolist.json";
    private ToDoList toDoList;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: constructs to-do list application and runs it
    public ToDoListApp() throws FileNotFoundException {
        initiateToDoList();
        runToDoList();
    }

    // MODIFIES: this
    // EFFECTS: processes user input for the to-do list
    private void runToDoList() {
        boolean keepRunning = true;
        String inputType = "";

        while (keepRunning) {
            displayInputOptions();

            inputType = input.next();
            inputType = inputType.toLowerCase();

            if (inputType.equals("q")) {
                keepRunning = false;
            } else if (inputType.equals("a")) {
                doAddTask();
            } else if (inputType.equals("d")) {
                doDeleteTask();
            } else if (inputType.equals("c")) {
                doComplete();
            } else if (inputType.equals("i")) {
                doIncomplete();
            } else if (inputType.equals("s")) {
                saveToDoList();
            } else if (inputType.equals("l")) {
                loadToDoList();
            }

        }

        System.out.println("See you next time!");

    }

    // EFFECTS: displays tasks currently on to-do list
    private void displayTasks() {

        System.out.println("TO-DO LIST:");

        for (int i = 0; i < toDoList.size(); i++) {
            Task task = toDoList.get(i);

            if (task.isCompleted()) {
                System.out.print("(COMPLETED) ");
            } else {
                System.out.print("(NOT COMPLETED) ");
            }
            System.out.println(task.getTaskDescription());
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes the to-do list
    private void initiateToDoList() {
        toDoList = new ToDoList();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // EFFECTS: displays options of either adding, deleting, or completing a task
    private void displayInputOptions() {
        System.out.println("Choose one of the following options:");
        System.out.println("a - add task");
        System.out.println("d - delete task");
        System.out.println("c - mark task as completed");
        System.out.println("i - mark task as incomplete");
        System.out.println("s - save to-do list to file");
        System.out.println("l - load to-do list from file");
        System.out.println("q - quit to-do list app");
    }

    // MODIFIES: this
    // EFFECTS: adds a task to the to-do list
    private void doAddTask() {
        System.out.println("Please enter the task you would like to add:");
        String taskInput = input.next();

        Task task = new Task(taskInput);

        toDoList.addTask(task);

        displayTasks();
    }

    // MODIFIES: this
    // EFFECTS: deletes a task off the to-do list
    private void doDeleteTask() {
        System.out.println("Please enter the task you would like to delete:");
        String taskInputDescription = input.next();
        Task task = toDoList.getTaskFromDescription(taskInputDescription);

        toDoList.deleteTask(task);

        displayTasks();
    }

    // MODIFIES: this
    // EFFECTS: marks a task on the to-do list as completed
    private void doComplete() {
        System.out.println("Please enter the task you have completed:");
        String taskInputDescription = input.next();

        Task task = toDoList.getTaskFromDescription(taskInputDescription);

        if (toDoList.containsDescription(taskInputDescription)) {
            task.markAsComplete();
        }

        displayTasks();
    }


    // MODIFIES: this
    // EFFECTS: marks a task on the to-do list as incomplete
    private void doIncomplete() {
        System.out.println("Please enter the task that is incomplete:");
        String taskInputDescription = input.next();

        Task task = toDoList.getTaskFromDescription(taskInputDescription);
        if (toDoList.containsDescription(taskInputDescription)) {
            task.markAsIncomplete();
        }

        displayTasks();
    }
    // This method references code from JsonSerializationDemo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

    // EFFECTS: saves to-do list to file
    private void saveToDoList() {
        try {
            jsonWriter.open();
            jsonWriter.write(toDoList);
            jsonWriter.close();
            System.out.println("Saved to-do list to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // This method references code from JsonSerializationDemo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadToDoList() {
        try {
            toDoList = jsonReader.read();
            displayTasks();
            System.out.println("Loaded to-do list from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
