package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents a to-do lists with tasks on it
public class ToDoList implements Writable {
    private ArrayList<Task> toDoList;  //to-do list

    // EFFECTS: creates a new empty to-do list
    public ToDoList() {
        toDoList = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: add a new task to the bottom of the to-do list
    public void addTask(Task t) {
        if (!toDoList.contains(t)) {
            toDoList.add(t);
        }
        EventLog.getInstance().logEvent(new Event("A task was added to the to-do list"));
    }

    // REQUIRES: Task t is an element of the to-do list
    // MODIFIES: this
    // EFFECTS: Task t is removed from the to-do list
    public void deleteTask(Task t) {
        toDoList.remove(t);
        EventLog.getInstance().logEvent(new Event("A task was removed from the to-do list"));
    }

    // EFFECTS: returns true if Task t is on the to-do list, else false
    public boolean contains(Task t) {
        return toDoList.contains(t);
    }

    // EFFECTS: returns true if description matches a task in the to-do list, else false
    public boolean containsDescription(String s) {
        for (Task t: toDoList) {
            if (s.equals(t.getTaskDescription())) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: returns the number of tasks on the to-do list
    public int size() {
        return toDoList.size();
    }

    // EFFECTS: returns the task at index i
    public Task get(int i) {
        return toDoList.get(i);
    }

    // EFFECTS: returns the index number of task t using zero-based indexing
    public int getIndex(Task t) {
        return toDoList.indexOf(t);
    }

    // EFFECTS: returns the task with description s
    public Task getTaskFromDescription(String s) {
        for (Task t: toDoList) {
            if (s.equals(t.getTaskDescription())) {
                return t;
            }
        }
        return null;
    }

    // This method references code from JsonSerializationDemo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("tasks", toDoListItemsToJson());
        EventLog.getInstance().logEvent(new Event("The to-do list was saved to file"));
        return json;
    }
    // This method references code from JsonSerializationDemo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

    // EFFECTS: returns tasks on to-do list as a JSON array
    private JSONArray toDoListItemsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Task t : toDoList) {
            jsonArray.put(t.toJson());
        }
        EventLog.getInstance().logEvent(new Event("The to-do list was loaded from file"));
        return jsonArray;
    }


}
