package ui;

import static ui.ToDoListGUI.createAndShowGUI;

import model.EventLog;
import model.ToDoList;

/*
public class Main {
    public static void main(String[] args) {
        try {
            new ToDoListApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
 */

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }

        });


    }
}
