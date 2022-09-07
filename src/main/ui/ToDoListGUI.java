package ui;

import model.Event;
import model.EventLog;
import model.Task;
import model.ToDoList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;



// This class references code from components-ListDemoProject
// https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html
// GUI for to-do list application
public class ToDoListGUI extends JPanel implements ListSelectionListener {

    private JList list;
    private ToDoList toDoListGUI;
    private DefaultListModel listModel;

    private static final String addTaskString = "Add task";
    private static final String deleteTaskString = "Delete task";
    private static final String saveToDoListString = "Save to-do list";
    private static final String loadToDoListString = "Load to-do list";
    private static final String changeCompletionStatusString = "Complete/Incomplete Task";

    private JButton deleteTaskButton;
    private JButton addTaskButton;
    private JButton saveToDoListButton;
    private JButton loadToDoListButton;
    private JButton changeCompletionStatusButton;
    private JTextField taskDescription;


    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/todolist.json";

    // Image taken from: https://giphy.com/gifs/MeufParis-work-meuf-meufparis-HYYbdk46gUzrgWi1Iz
    private static final String TO_DO_LIST_GIF = "./data/toDoListGif.gif";


    // EFFECTS: constructor for to-do list GUI
    public ToDoListGUI() {
        super(new BorderLayout());

        listModel = new DefaultListModel();
        toDoListGUI = new ToDoList();

        // create the to-do list and put it in a scroll pane
        listModel = toDoListToListModel();

        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(25);

        initializeInteraction();
        initializeGraphics();
        splashScreen();
    }



    // MODIFIES: this
    // EFFECTS: initializes all interaction components of the to-do list GUI
    public void initializeInteraction() {
        addAndDeleteTaskButtons();
        saveAndLoadButtons();
        changeCompletionStatusButton();
    }

    // MODIFIES: this
    // EFFECTS: creates the splash screen that appears when the app is first opened
    public void splashScreen() {
        ImageIcon image = new ImageIcon(TO_DO_LIST_GIF);
        JOptionPane.showMessageDialog(null, "   Welcome to your to-do list!",
                "To-do list", JOptionPane.PLAIN_MESSAGE, image);
    }

    // MODIFIES: this
    // EFFECTS: initializes all the graphical panels of the to-do list GUI
    public void initializeGraphics() {

        JScrollPane listScrollPane = new JScrollPane(list);

        JPanel lowerButtonPane = new JPanel();
        lowerButtonPane.setLayout(new BoxLayout(lowerButtonPane, BoxLayout.LINE_AXIS));
        lowerButtonPane.add(deleteTaskButton);
        lowerButtonPane.add(addTaskButton);
        lowerButtonPane.add(Box.createHorizontalStrut(5));
        lowerButtonPane.add(taskDescription);
        lowerButtonPane.add(changeCompletionStatusButton);
        lowerButtonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JPanel upperButtonPane = new JPanel();
        upperButtonPane.setLayout(new BoxLayout(upperButtonPane, BoxLayout.LINE_AXIS));
        upperButtonPane.add(saveToDoListButton);
        upperButtonPane.add(loadToDoListButton);
        upperButtonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        add(listScrollPane, BorderLayout.CENTER);
        add(lowerButtonPane, BorderLayout.PAGE_END);
        add(upperButtonPane, BorderLayout.PAGE_START);

    }



    // MODIFIES: this
    // EFFECTS: initializes a change completion status button
    public void changeCompletionStatusButton() {
        changeCompletionStatusButton = new JButton(changeCompletionStatusString);
        changeCompletionStatusButton.setActionCommand(changeCompletionStatusString);
        changeCompletionStatusButton.addActionListener(new ChangeCompletionStatusListener());

    }

    // MODIFIES: this
    // EFFECTS: initializes the add and delete tasks button
    public void addAndDeleteTaskButtons() {
        addTaskButton = new JButton(addTaskString);
        AddTaskListener addTaskListener = new AddTaskListener(addTaskButton);
        addTaskButton.setActionCommand(addTaskString);
        addTaskButton.addActionListener(addTaskListener);
        addTaskButton.setEnabled(false);

        deleteTaskButton = new JButton(deleteTaskString);
        deleteTaskButton.setActionCommand(deleteTaskString);
        deleteTaskButton.addActionListener(new DeleteTaskListener());

        taskDescription = new JTextField(10);
        taskDescription.addActionListener(addTaskListener);
        taskDescription.getDocument().addDocumentListener(addTaskListener);
    }

    // MODIFIES: this
    // EFFECTS: initializes the save and load to-do list buttons
    public void saveAndLoadButtons() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        saveToDoListButton = new JButton(saveToDoListString);
        saveToDoListButton.setActionCommand(saveToDoListString);
        saveToDoListButton.addActionListener(new SaveToDoListListener());

        loadToDoListButton = new JButton(loadToDoListString);
        loadToDoListButton.setActionCommand(loadToDoListString);
        loadToDoListButton.addActionListener(new LoadToDoListListener());
    }

    // MODIFIES: listModel
    // EFFECTS: returns the listModel with all tasks from to-do list as strings
    DefaultListModel toDoListToListModel() {
        listModel.clear();
        for (int i = 0; i < toDoListGUI.size(); i++) {
            listModel.addElement(toDoListGUI.get(i).taskStatusAsString() + " "
                    + toDoListGUI.get(i).getTaskDescription());
        }
        return listModel;
    }



    // The DeleteTaskListener deletes the selected task when but delete button is pushed
    class DeleteTaskListener implements ActionListener {

        // MODIFIES: this
        // EFFECTS: If the to-do list is not empty, delete the selected task from the to-do list
        //          Disable the deleteTaskButton if to-do list size is zero
        @Override
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();
            listModel.remove(index);
            toDoListGUI.deleteTask(toDoListGUI.get(index));

            int size = listModel.getSize();

            if (size == 0) {
                deleteTaskButton.setEnabled(false);
            } else {
                if (index == listModel.getSize()) {
                    index--;
                }

                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
            }
        }
    }

    // The AddTaskListener is shared by the text field and the addTask button
    // The AddTaskListener adds a task to the to-do list when AddTaskButton is pushed
    class AddTaskListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        // MODIFIES: this
        // EFFECTS: constructor for the addTask button
        public AddTaskListener(JButton button) {
            this.button = button;
        }

        // MODIFIES: this
        // EFFECTS: if text field is not empty, add the task to the to-do list
        @Override
        public void actionPerformed(ActionEvent e) {
            String description = taskDescription.getText();

            if (description.equals("") || alreadyInList(description)) {
                Toolkit.getDefaultToolkit().beep();
                taskDescription.requestFocusInWindow();
                taskDescription.selectAll();
                return;
            }

            int index = list.getSelectedIndex();
            if (index == -1) {
                index = 0;
            } else {
                index++;
            }
            Task task = new Task(description);

            toDoListGUI.addTask(task);
            listModel.addElement(task.taskStatusAsString() + " " + taskDescription.getText());

            taskDescription.requestFocusInWindow();
            taskDescription.setText("");

            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);

        }


        // EFFECTS: returns true if the description of task is already in the list
        protected boolean alreadyInList(String description) {
            return listModel.contains(description);
        }

        // MODIFIES: this
        // EFFECTS: enables the addTaskButton if the text field is not empty
        @Override
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        // MODIFIES: this
        // EFFECTS: disables the addTaskButton if the text field is empty
        @Override
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        // MODIFIES: this
        // EFFECTS: if text field is not empty, enable add task button
        @Override
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }


        // MODIFIES: this
        // EFFECTS: if the addTaskButton is not already enabled, set it to enabled
        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        // MODIFIES: this
        // EFFECTS: if the text field is empty (length is zero), disable the AddTaskButton
        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }

    // The ChangeCompletionStatusListener changes completion status of task when button is pushed
    class ChangeCompletionStatusListener implements ActionListener {

        // MODIFIES: this
        // EFFECTS: if the task is incomplete, mark as complete, if the task is incomplete, mark as complete
        @Override
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();
            Task task = toDoListGUI.get(index);

            if (task.isCompleted()) {
                task.markAsIncomplete();
            } else {
                task.markAsComplete();
            }
            listModel.remove(index);

            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);

            listModel.insertElementAt(task.taskStatusAsString() + " " + task.getTaskDescription(), index);

        }
    }

    // This class references code from JsonSerializationDemo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // The SaveToDoListListener saves the current to-do list when SaveToDoList button is pushed
    class SaveToDoListListener implements ActionListener {

        // MODIFIES: this
        // EFFECTS: Writes a to-do list into the Json data file and saves it
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                jsonWriter.open();
                jsonWriter.write(toDoListGUI);
                jsonWriter.close();
            } catch (FileNotFoundException a) {
                System.out.println("Unable to write to file: " + JSON_STORE);
            }
        }
    }

    // This class references code from JsonSerializationDemo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // The LoadToDoListListener loads the saved to-do list on file onto the to-do list GUI
    class LoadToDoListListener implements ActionListener {

        // MODIFIES: this
        // EFFECTS: Reads a to-do list from the JSON data stored in the file and loads it
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                toDoListGUI = jsonReader.read();
                listModel = toDoListToListModel();
            } catch (IOException a) {
                System.out.println("Unable to read from file: " + JSON_STORE);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: enables/disables delete task button depending on the value of list selection
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (list.getSelectedIndex() == -1) {
                deleteTaskButton.setEnabled(false);
            } else {
                deleteTaskButton.setEnabled(true);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: creates and shows the GUI that is visible to the user
    public static void createAndShowGUI() {
        JFrame frame = new JFrame("To-Do List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JComponent newContentPane = new ToDoListGUI();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);

        frame.pack();
        frame.setVisible(true);


        // this piece of code references material from:
        // https://stackoverflow.com/questions/39845163/
        // is-windowadapter-is-an-adapter-pattern-implementation-in-java-swing
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                for (Event next : EventLog.getInstance()) {
                    System.out.println(next.getDate() + "\n" + next.getDescription() + "\n");
                }
            }
        });



    }
}
