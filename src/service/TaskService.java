package service;

import dao.DatabaseConnection;
import interfaces.TaskOperations;
import model.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class TaskService implements TaskOperations {

    @Override
    public void addTask(Task task) {
        String sql = "INSERT INTO tasks " +
                "(id, title, description, due_date, priority, completed) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, task.getId());
            statement.setString(2, task.getTitle());
            statement.setString(3, task.getDescription());
            statement.setDate(4, Date.valueOf(task.getDueDate()));
            statement.setString(5, task.getPriority());
            statement.setBoolean(6, task.isCompleted());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Could not save task: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();

        String sql = "SELECT id, title, description, due_date, priority, completed " +
                "FROM tasks ORDER BY id";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(sql)) {

            while (result.next()) {
                Task task = new Task(
                        result.getInt("id"),
                        result.getString("title"),
                        result.getString("description"),
                        result.getDate("due_date").toLocalDate(),
                        result.getString("priority")
                );

                if (result.getBoolean("completed")) {
                    task.markCompleted();
                }

                tasks.add(task);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Could not load tasks: " + e.getMessage(), e);
        }

        return tasks;
    }

    @Override
    public boolean completeTask(int id) {
        String sql = "UPDATE tasks SET completed = TRUE WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            int rowsUpdated = statement.executeUpdate();

            return rowsUpdated > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Could not complete task: " + e.getMessage(), e);
        }
    }
}
