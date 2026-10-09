
import gui.TaskGUI;
import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
        // Application starts here
        SwingUtilities.invokeLater(() -> {
            TaskGUI gui = new TaskGUI();
            gui.setVisible(true);
        });
    }
}