import java.awt.*;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        int width = 360;
        int height = 640;

        JFrame frame = new JFrame("Flappy Bird");
        CardLayout layout = new CardLayout();
        JPanel mainPanel = new JPanel(layout);

        MenuPanel menuPanel = new MenuPanel(layout, mainPanel);
        GamePanel gamepanel = new GamePanel();

        mainPanel.add(menuPanel, "Menu");
        mainPanel.add(gamepanel, "Game");

        // Main Window
        frame.add(mainPanel);
        frame.setVisible(true);
        frame.setSize(width, height);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}