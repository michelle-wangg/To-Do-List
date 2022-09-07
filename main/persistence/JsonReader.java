package persistence;

import model.ToDoList;
import model.Task;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;


// This class references code from JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

// Represents a reader that reads a to-do list from the JSON data stored in the file
public class JsonReader {
    private String source;

    // EFFECTS: constructs the reader to read from the source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads to-do list from file and returns it
    // throws IOException if an error occurs reading data from file
    public ToDoList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseToDoList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses through to-do list from JSON object and returns it
    private ToDoList parseToDoList(JSONObject jsonObject) {
        ToDoList toDoList = new ToDoList();
        addTasks(toDoList, jsonObject);
        return toDoList;
    }


    // MODIFIES: list
    // EFFECTS: parses through tasks from JSON object and adds them to the to-do list
    private void addTasks(ToDoList toDoList, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("tasks");
        for (Object json : jsonArray) {
            JSONObject nextTask = (JSONObject) json;
            addTask(toDoList, nextTask);
        }
    }


    // MODIFIES: list
    // EFFECTS: parses through task from JSON object and adds them to the to-do list
    private void addTask(ToDoList toDoList, JSONObject jsonObject) {
        String taskDescription = jsonObject.getString("task description");
        boolean isCompleted = jsonObject.getBoolean("completion status");
        Task newTask = new Task(taskDescription);
        if (isCompleted) {
            newTask.markAsComplete();
        }
        toDoList.addTask(newTask);
    }


}
