import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel implements ActionListener {
    // background
    private Image backgroundimage;
    private int backgroundX = 0;
    private int backgroundspeed = 2;

    // base
    private Image basePlatform;
    private int groundX = 0;
    private int groundspeed = 4;

    private Timer timer;

    GamePanel() {
        backgroundimage = new ImageIcon("assets/background-day.png").getImage();
        basePlatform = new ImageIcon("assets/base.png").getImage();

        timer = new Timer(16, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        backgroundX -= backgroundspeed;
        if (backgroundX <= -getWidth()) {
            backgroundX = 0;
        }

        groundX -= groundspeed;
        if (groundX <= -getWidth()) {
            groundX = 0;
        }

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // for background
        g.drawImage(backgroundimage, backgroundX, 0, getWidth(), getHeight(), this);
        g.drawImage(backgroundimage, backgroundX + getWidth(), 0, getWidth(), getHeight(), this);

        // for base
        int groundHeight = 100;
        int groundY = getHeight() - groundHeight;

        g.drawImage(basePlatform, groundX, groundY, getWidth(), groundHeight, this);
        g.drawImage(basePlatform, groundX + getWidth(), groundY, getWidth(), groundHeight, this);
    }
}
