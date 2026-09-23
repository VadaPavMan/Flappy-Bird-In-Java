
import java.awt.Rectangle;

public class Bird {
    private int x;
    private int y;
    private int width;
    private int height;

    private double velocityY;

    private final double gravity = 0.5;
    private final double flapStrength = -8.0;

    public Bird(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;

        this.width = width;
        this.height = height;

        this.velocityY = 0;
    }

    public void update() {
        velocityY += gravity;
        y += velocityY;

    }

    public void flap() {
        velocityY = flapStrength;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
