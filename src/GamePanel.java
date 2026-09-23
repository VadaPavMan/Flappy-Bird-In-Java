import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
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
    private int backgroundspeed = 1;

    // base
    private Image basePlatform;
    private int groundX = 0;
    private int groundspeed = 3;
    private static final int GROUND_HEIGHT = 100;
    private int groundY = getHeight() - GROUND_HEIGHT;

    private Timer timer;

    // pipes
    private ArrayList<Pipe> pipes;
    private Image pipeImage;
    private static final int PIPE_WIDTH = 60;
    private static final int PIPE_SPEED = 3;
    private static final int PIPE_GAP = 155;
    private static final int PIPE_MIN_GAP_Y = 100;
    private static final int PIPE_MAX_GAP_Y = 350;
    private static final int PIPE_SPAWN_DISTANCE = 220;
    private final Random random = new Random();

    GamePanel() {
        backgroundimage = new ImageIcon(assets.getIcon(assets.BACKGROUND_NIGHT)).getImage();
        basePlatform = new ImageIcon(assets.getIcon(assets.BASE)).getImage();

        bird = new Bird(80, 250, 34, 24);
        setupKeyBindings();

        birdUpFlapImage = new ImageIcon(assets.getIcon(_loadAssets.REDBIRD_UPFLAP)).getImage();
        birdMidFlapImage = new ImageIcon(assets.getIcon(_loadAssets.REDBIRD_MIDFLAP)).getImage();
        birdDownFlapImage = new ImageIcon(assets.getIcon(_loadAssets.REDBIRD_DOWNFLAP)).getImage();
        birdImage = birdMidFlapImage;

        timer = new Timer(16, this);
        timer.start();

        pipes = new ArrayList<>();

        pipeImage = new ImageIcon(assets.getIcon(_loadAssets.PIPE_GREEN)).getImage();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isShowing()) {
            return;
        }

        bird.update();
        checkBirdBoundaries();
        updateBirdImage();

        for (Pipe pipe : pipes) {
            pipe.update();
        }

        if(hasCollided()){
            System.out.println("Collision!");
        }

        updatePipes();

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
        groundY = getHeight() - groundHeight;

        g.drawImage(basePlatform, groundX, groundY, getWidth(), groundHeight, this);
        g.drawImage(basePlatform, groundX + getWidth(), groundY, getWidth(), groundHeight, this);

        // for bird
        double birdCenterX = bird.getX() + bird.getWidth() / 2.0;
        double birdCenterY = bird.getY() + bird.getHeight() / 2.0;
        g2.rotate(birdAngle, birdCenterX, birdCenterY);
        g2.drawImage(birdImage, bird.getX(), bird.getY(), bird.getWidth(), bird.getHeight(), this);
        g2.dispose();

        // for pipes

        for (Pipe pipe : pipes) {
            if (pipe.isTopPipe()) {
                g.drawImage(pipeImage, pipe.getX(), pipe.getY() + pipe.getHeight(),
                        pipe.getWidth(), -pipe.getHeight(), this);
            } else {
                g.drawImage(pipeImage, pipe.getX(), pipe.getY(),
                        pipe.getWidth(), pipe.getHeight(), this);
            }
        }
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

    private void checkBirdBoundaries() {

        if (bird.getY() < 0) {
            bird.setY(0);
        }

        int groundY = getHeight() - GROUND_HEIGHT;

        if (bird.getY() + bird.getHeight() >= groundY) {
            bird.setY(groundY - bird.getHeight());
        }
    }

    private void addPipePair(int x) {
        int gapY = PIPE_MIN_GAP_Y
                + random.nextInt(PIPE_MAX_GAP_Y - PIPE_MIN_GAP_Y + 1);
        int groundY = getHeight() > 0 ? getHeight() - GROUND_HEIGHT : 540;

        pipes.add(new Pipe(x, 0, PIPE_WIDTH, gapY, PIPE_SPEED, true));
        pipes.add(new Pipe(x, gapY + PIPE_GAP, PIPE_WIDTH,
                Math.max(0, groundY - gapY - PIPE_GAP), PIPE_SPEED));
    }

    private void updatePipes() {
        if (pipes.isEmpty()) {
            if (getWidth() > 0 && getHeight() > 0) {
                addPipePair(getWidth());
            }
            return;
        }

        Pipe rightmostPipe = pipes.get(pipes.size() - 1);
        if (rightmostPipe.getX() <= getWidth() - PIPE_SPAWN_DISTANCE) {
            addPipePair(getWidth());
        }

        if (pipes.get(0).getX() + pipes.get(0).getWidth() < 0) {
            pipes.remove(0);
            pipes.remove(0);
        }
    }

    private boolean hasHitPipe(){
        Rectangle birdBounds = bird.getBounds();

        for(Pipe pipe : pipes){
            if (birdBounds.intersects(pipe.getBounds())){
                return true;
            }
        }

        return false;
    }

    private boolean hasHitGround(){
        if(bird.getY() >= groundY - bird.getHeight()) return true;
        return false;
    }

    private boolean hasCollided(){
        if (hasHitGround() || hasHitPipe()) return true;
        return false;
    }
}
