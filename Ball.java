import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Ball extends Rectangle {

    private int xVelocity;
    private int yVelocity;
    private int initialSpeed = 5;

    public Ball(int x, int y, int BALL_DIAMETER) {
        super(x, y, BALL_DIAMETER, BALL_DIAMETER);
        Random random = new Random();
        int randomXDirection = random.nextInt(2);
        if (randomXDirection == 0) randomXDirection--;
        setXDirection(randomXDirection * initialSpeed);

        int randomYDirection = random.nextInt(2);
        if (randomYDirection == 0) randomYDirection--;
        setYDirection(randomYDirection * initialSpeed);
    }

    public void setXDirection(int direction) {
        xVelocity = direction;
    }

    public void setYDirection(int direction) {
        yVelocity = direction;
    }

    public int getXVelocity() {  // Getter pour xVelocity
        return xVelocity;
    }

    public void move() {
        x += xVelocity;
        y += yVelocity;

        if (y <= 0 || y >= GamePanel.GAME_HEIGHT - height) {
            setYDirection(-yVelocity);
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval(x, y, width, height);
    }
}
