package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a task with a task description and a status of whether it is completed or not
public class Task implements Writable {
    private String taskDescription;       //description of the to-do task
    private boolean isCompleted;          // status of task, whether it is completed or not

    // EFFECTS: to-do list has a description and is not completed
    public Task(String taskDescription) {
        this.taskDescription = taskDescription;
        isCompleted = false;
    }


    // EFFECTS: returns the task description
    public String getTaskDescription() {
        return taskDescription;
    }

    // EFFECTS: returns true if task is completed, false otherwise
    public boolean isCompleted() {
        return isCompleted;
    }

    // MODIFIES: this
    // EFFECTS: marks task as complete
    public void markAsComplete() {
        EventLog.getInstance().logEvent(new Event("A task was marked as complete"));
        isCompleted = true;
    }

    // MODIFIES: this
    // EFFECTS: marks task as incomplete
    public void markAsIncomplete() {
        EventLog.getInstance().logEvent(new Event("A task was marked as incomplete"));
        isCompleted = false;
    }

    // EFFECTS: returns a string of whether or not a task is complete
    public String taskStatusAsString() {
        if (this.isCompleted) {
            return "[COMPLETED]";
        } else {
            return "[NOT COMPLETED]";
        }
    }

    // This method references code from JsonSerializationDemo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("task description", taskDescription);
        json.put("completion status", isCompleted);
        return json;
    }
}
