import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Paddle extends Rectangle {

    private int yVelocity;
    private final int speed = 10;

    public Paddle(int x, int y, int PADDLE_WIDTH, int PADDLE_HEIGHT) {
        super(x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
    }

    public void setYDirection(int direction) {
        yVelocity = direction * speed;
    }

    public void move() {
        y += yVelocity;
        // Limites pour que la raquette ne sorte pas de l'écran
        if (y < 0) y = 0;
        if (y > GamePanel.GAME_HEIGHT - height) y = GamePanel.GAME_HEIGHT - height;
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, width, height);
    }
}
