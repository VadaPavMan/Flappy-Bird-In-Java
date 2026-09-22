import java.awt.*;
import javax.swing.*;

public class MenuPanel extends JPanel {
    // load assets
    _loadAssets assets = new _loadAssets();


    private Image backgroundimage;

    public MenuPanel(CardLayout card, JPanel mainPanel) {
        backgroundimage = new ImageIcon(assets.getIcon(assets.BACKGROUND_DAY)).getImage();
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;

        // Start Button
        JButton startButton = new JButton("Start");
        startButton.setOpaque(true);
        startButton.setBorderPainted(false);
        startButton.setPreferredSize(new java.awt.Dimension(140, 50));
        startButton.setBackground(Color.GREEN);
        startButton.setBorderPainted(true);
        startButton.addActionListener(e -> card.show(mainPanel, "Game"));
        gbc.gridy = 1;
        add(startButton, gbc);

        // Option Button
        JButton optionButton = new JButton("Options");
        optionButton.setOpaque(true);
        optionButton.setBorderPainted(false);
        optionButton.setPreferredSize(new java.awt.Dimension(140, 50));
        optionButton.setBackground(Color.LIGHT_GRAY);
        gbc.gridy = 2;
        add(optionButton, gbc);

        // Exit Button
        JButton exitButton = new JButton("Exit");
        exitButton.setOpaque(true);
        exitButton.setBorderPainted(false);
        exitButton.setPreferredSize(new java.awt.Dimension(140, 50));
        exitButton.setBackground(Color.RED);
        exitButton.addActionListener(e -> System.exit(0));
        gbc.gridy = 3;
        add(exitButton, gbc);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundimage, 0, 0, getWidth(), getHeight(), this);
    }

}
