package gui;

import model.Task;
import service.TaskService;
import threads.TaskReminderThread;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class TaskGUI extends JFrame {

    private final TaskService taskService = new TaskService();
    private final TaskReminderThread reminderThread = new TaskReminderThread(taskService);

    private final JTextField titleField = new JTextField(15);
    private final JTextField descriptionField = new JTextField(15);
    private final JTextField dueDateField = new JTextField(10);

    private final JComboBox<String> priorityBox =
            new JComboBox<>(new String[]{"Low", "Medium", "High"});

    private final DefaultListModel<Task> listModel =
            new DefaultListModel<>();

    private final JList<Task> taskList = new JList<>(listModel);

    private int nextId = 1;

    public TaskGUI() {
        setTitle("Time Management System");
        setSize(650, 500);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

addWindowListener(new java.awt.event.WindowAdapter() {
    @Override
    public void windowClosing(java.awt.event.WindowEvent e) {
        reminderThread.stopReminder();
        dispose();
        System.exit(0);
    }
});
        setLocationRelativeTo(null);

        JPanel form = new JPanel(new GridLayout(5, 2, 8, 8));
        form.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        form.add(new JLabel("Task Title:"));
        form.add(titleField);

        form.add(new JLabel("Description:"));
        form.add(descriptionField);

        form.add(new JLabel("Due Date (YYYY-MM-DD):"));
        form.add(dueDateField);

        form.add(new JLabel("Priority:"));
        form.add(priorityBox);

        JButton addButton = new JButton("Add Task");
        JButton completeButton = new JButton("Complete Selected");

        form.add(addButton);
        form.add(completeButton);

        taskList.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        add(form, BorderLayout.NORTH);
        add(new JScrollPane(taskList), BorderLayout.CENTER);

        addButton.addActionListener(e -> addTask());
        completeButton.addActionListener(e -> completeSelectedTask());

        loadTasks();
        reminderThread.start();
    }

    private void loadTasks() {
        try {
            refreshTaskList();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load tasks: " + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void addTask() {
        try {
            String title = titleField.getText().trim();
            String description = descriptionField.getText().trim();
            String dateText = dueDateField.getText().trim();

            if (title.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this, "Task title is required."
                );
                return;
            }

            LocalDate dueDate = LocalDate.parse(dateText);
            String priority = (String) priorityBox.getSelectedItem();

            Task task = new Task(
                    nextId,
                    title,
                    description,
                    dueDate,
                    priority
            );

            taskService.addTask(task);

            nextId++;

            refreshTaskList();

            titleField.setText("");
            descriptionField.setText("");
            dueDateField.setText("");

            JOptionPane.showMessageDialog(
                    this, "Task saved to MySQL successfully!"
            );

        } catch (java.time.format.DateTimeParseException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Enter a valid date in YYYY-MM-DD format.",
                    "Invalid Date",
                    JOptionPane.ERROR_MESSAGE
            );
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Unable to add task: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void completeSelectedTask() {
        Task selected = taskList.getSelectedValue();

        if (selected == null) {
            JOptionPane.showMessageDialog(
                    this, "Please select a task first."
            );
            return;
        }

        try {
            boolean updated = taskService.completeTask(selected.getId());

            if (updated) {
                refreshTaskList();

                JOptionPane.showMessageDialog(
                        this, "Task marked as completed!"
                );
            } else {
                JOptionPane.showMessageDialog(
                        this, "Task was not found in the database."
                );
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Unable to complete task: " + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void refreshTaskList() {
        listModel.clear();

        List<Task> tasks = taskService.getAllTasks();

        int highestId = 0;

        for (Task task : tasks) {
            listModel.addElement(task);

            if (task.getId() > highestId) {
                highestId = task.getId();
            }
        }

        nextId = highestId + 1;
    }
}