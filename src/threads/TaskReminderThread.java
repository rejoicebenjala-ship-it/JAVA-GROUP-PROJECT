package threads;

import model.Task;
import service.TaskService;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TaskReminderThread extends Thread {

private final TaskService taskService;
private volatile boolean running = true;
private final Set<Integer> notifiedTaskIds = new HashSet<>();

public TaskReminderThread(TaskService taskService) {
    this.taskService = taskService;
    setName("Task-Reminder-Thread");
    setDaemon(true);
}

@Override
public void run() {
    while (running) {
        try {
            checkTasks();
            Thread.sleep(60_000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            break;
        } catch (Exception e) {
            System.err.println(
                "Reminder thread error: " + e.getMessage()
            );

            try {
                Thread.sleep(60_000);
            } catch (InterruptedException interrupted) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}

private void checkTasks() {
    List<Task> tasks = taskService.getAllTasks();
    LocalDate today = LocalDate.now();

    for (Task task : tasks) {
        boolean isDueOrOverdue =
            !task.isCompleted()
            && !task.getDueDate().isAfter(today);

        if (isDueOrOverdue && markAsNotified(task.getId())) {
            String message =
                "Task: " + task.getTitle()
                + "\nDue date: " + task.getDueDate()
                + "\nPlease review this task.";

            SwingUtilities.invokeLater(() ->
                JOptionPane.showMessageDialog(
                    null,
                    message,
                    "Task Reminder",
                    JOptionPane.INFORMATION_MESSAGE
                )
            );
        }
    }
}

private synchronized boolean markAsNotified(int taskId) {
    return notifiedTaskIds.add(taskId);
}

public void stopReminder() {
    running = false;
    interrupt();
}

}
