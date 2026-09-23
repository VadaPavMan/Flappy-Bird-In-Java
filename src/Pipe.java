import java.awt.Rectangle;

public class Pipe {
    private int x;
    private int y;
    private int width;
    private int height;

    private int speed;
    private boolean topPipe;

    public Pipe(int x, int y, int width, int height, int speed) {
        this(x, y, width, height, speed, false);
    }

    public Pipe(int x, int y, int width, int height, int speed, boolean topPipe) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.topPipe = topPipe;
    }

    public void update() {
        x -= speed;
    }

    public void setX(int x) {
        this.x = x;
    }

    public boolean isTopPipe() {
        return topPipe;
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

    public Rectangle getBounds() {
        return new Rectangle(
                x,
                y,
                width,
                height);
    }
}
