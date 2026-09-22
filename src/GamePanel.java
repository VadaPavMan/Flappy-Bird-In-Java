import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener {
    // load assets
    _loadAssets assets = new _loadAssets();

    private Bird bird;

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
        backgroundimage = new ImageIcon(assets.getIcon(assets.BACKGROUND_DAY)).getImage();
        basePlatform = new ImageIcon(assets.getIcon(assets.BASE)).getImage();

        bird = new Bird(80, 250, 34, 24);
        setupKeyBindings();

        timer = new Timer(16, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isShowing()) {
            return;
        }

        bird.update();

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

        g.setColor(Color.RED);
        g.fillRect(bird.getX(), bird.getY(), bird.getWidth(), bird.getHeight());

    }

    private void setupKeyBindings(){
        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("SPACE"), "flap");

        actionMap.put(
            "flap",
            new AbstractAction(){

                @Override 
                public void actionPerformed(ActionEvent e){
                    bird.flap();
                }
            }
        );
    }
}
