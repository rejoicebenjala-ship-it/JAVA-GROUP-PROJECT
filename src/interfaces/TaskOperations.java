package interfaces;

import model.Task;
import java.util.List;

public interface TaskOperations {
    void addTask(Task task);

    List<Task> getAllTasks();

    boolean completeTask(int id);
}
