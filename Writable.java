package persistence;

import org.json.JSONObject;

// This interface references code from JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public interface Writable {
    // EFFECTS: returns ths as JSON object
    JSONObject toJson();
}
