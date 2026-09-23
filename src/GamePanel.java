import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener {
    // load assets
    _loadAssets assets = new _loadAssets();

    private Bird bird;
    private Image birdImage;
    private Image birdUpFlapImage;
    private Image birdMidFlapImage;
    private Image birdDownFlapImage;
    private double birdAngle;

    private static final double UP_FLAP_VELOCITY = -2.5;
    private static final double DOWN_FLAP_VELOCITY = 2.5;

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

        birdUpFlapImage = new ImageIcon(assets.getIcon(_loadAssets.REDBIRD_UPFLAP)).getImage();
        birdMidFlapImage = new ImageIcon(assets.getIcon(_loadAssets.REDBIRD_MIDFLAP)).getImage();
        birdDownFlapImage = new ImageIcon(assets.getIcon(_loadAssets.REDBIRD_DOWNFLAP)).getImage();
        birdImage = birdMidFlapImage;

        timer = new Timer(16, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isShowing()) {
            return;
        }

        bird.update();
        updateBirdImage();

        birdAngle = Math.max(Math.toRadians(-25),
                Math.min(Math.toRadians(70), bird.getVelocityY() * 0.06));

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

    private void updateBirdImage() {
        double velocityY = bird.getVelocityY();

        if (velocityY <= UP_FLAP_VELOCITY) {
            birdImage = birdUpFlapImage;
        } else if (velocityY >= DOWN_FLAP_VELOCITY) {
            birdImage = birdDownFlapImage;
        } else {
            birdImage = birdMidFlapImage;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        // for background
        g.drawImage(backgroundimage, backgroundX, 0, getWidth(), getHeight(), this);
        g.drawImage(backgroundimage, backgroundX + getWidth(), 0, getWidth(), getHeight(), this);

        // for base
        int groundHeight = 100;
        int groundY = getHeight() - groundHeight;

        g.drawImage(basePlatform, groundX, groundY, getWidth(), groundHeight, this);
        g.drawImage(basePlatform, groundX + getWidth(), groundY, getWidth(), groundHeight, this);

        // for bird
        double birdCenterX = bird.getX() + bird.getWidth() / 2.0;
        double birdCenterY = bird.getY() + bird.getHeight() / 2.0;
        g2.rotate(birdAngle, birdCenterX, birdCenterY);
        g2.drawImage(birdImage, bird.getX(), bird.getY(), bird.getWidth(), bird.getHeight(), this);
        g2.dispose();

    }

    private void setupKeyBindings() {
        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("SPACE"), "flap");

        actionMap.put(
                "flap",
                new AbstractAction() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        bird.flap();
                        birdAngle = Math.toRadians(-25);
                    }
                });
    }
}
