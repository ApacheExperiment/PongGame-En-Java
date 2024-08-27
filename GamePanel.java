import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
//import java.util.Random;
import javax.swing.Timer;

public class GamePanel extends JPanel implements Runnable, KeyListener {

    static final int GAME_WIDTH = 800;
    static final int GAME_HEIGHT = 600;
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    static final int BALL_DIAMETER = 20;
    static final int PADDLE_WIDTH = 25;
    static final int PADDLE_HEIGHT = 100;

    private Thread gameThread;
    private Image image;
    private Graphics graphics;
    private Paddle playerPaddle;
    private Paddle aiPaddle;
    private Ball ball;
    private Timer aiTimer;
    private int playerScore = 0;
    private int aiScore = 0;
    private final int WINNING_SCORE = 15;

    public GamePanel() {
        newPaddles();
        newBall();
        this.setFocusable(true);
        this.addKeyListener(this);
        this.setPreferredSize(SCREEN_SIZE);

        aiTimer = new Timer(30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveAI();
            }
        });
        aiTimer.start();

        gameThread = new Thread(this);
        gameThread.start();
    }

    public void newBall() {
        ball = new Ball(GAME_WIDTH / 2 - BALL_DIAMETER / 2, GAME_HEIGHT / 2 - BALL_DIAMETER / 2, BALL_DIAMETER);
    }

    public void newPaddles() {
        playerPaddle = new Paddle(20, GAME_HEIGHT / 2 - PADDLE_HEIGHT / 2, PADDLE_WIDTH, PADDLE_HEIGHT);
        aiPaddle = new Paddle(GAME_WIDTH - PADDLE_WIDTH - 20, GAME_HEIGHT / 2 - PADDLE_HEIGHT / 2, PADDLE_WIDTH,
                PADDLE_HEIGHT);
    }

    public void move() {
        playerPaddle.move();
        ball.move();
    }

    public void moveAI() {
        // Déplacement de l'IA en fonction de la position de la balle
        if (ball.y < aiPaddle.y) {
            aiPaddle.setYDirection(-1);
        } else if (ball.y > aiPaddle.y + aiPaddle.height) {
            aiPaddle.setYDirection(1);
        } else {
            aiPaddle.setYDirection(0);
        }
        aiPaddle.move();
    }

    public void checkCollision() {
        // Rebonds de la balle sur les raquettes
        if (ball.intersects(playerPaddle)) {
            ball.setXDirection(-ball.getXVelocity());
        }
        if (ball.intersects(aiPaddle)) {
            ball.setXDirection(-ball.getXVelocity());
        }

        // Si la balle sort des limites (côtés gauche ou droit), on la recentre
        if (ball.x <= 0) {
            aiScore++;
            checkWinCondition();
            newBall();
        }
        if (ball.x >= GAME_WIDTH - BALL_DIAMETER) {
            playerScore++;
            checkWinCondition();
            newBall();
        }
    }

    private void checkWinCondition() {
        if (playerScore >= WINNING_SCORE) {
            stopGame("Félicitations ! Vous avez gagné la partie !");
        } else if (aiScore >= WINNING_SCORE) {
            stopGame("Dommage ! L'IA a remporté la partie !");
        }
    }

    private void stopGame(String message) {
        // Afficher un message contextuel avec l'information du résultat
        JOptionPane.showMessageDialog(this, message, "Partie terminée", JOptionPane.INFORMATION_MESSAGE);

        // Ferme le jeu après un court délai pour permettre à l'utilisateur de lire le
        // message
        Timer timer = new Timer(1500, e -> System.exit(0));
        timer.setRepeats(false);
        timer.start();
    }

    public void paint(Graphics g) {
        image = createImage(getWidth(), getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK); // Fond noir
        g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        playerPaddle.draw(g);
        aiPaddle.draw(g);
        ball.draw(g);

        // Dessiner le score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Consolas", Font.PLAIN, 50));
        g.drawString(String.valueOf(playerScore), GAME_WIDTH / 2 - 100, 50);
        g.drawString(String.valueOf(aiScore), GAME_WIDTH / 2 + 65, 50);
    }

    @Override
    public void run() {
        // Boucle principale du jeu
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        while (true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1) {
                move();
                checkCollision();
                repaint();
                delta--;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            playerPaddle.setYDirection(-1);
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            playerPaddle.setYDirection(1);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
            playerPaddle.setYDirection(0);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Non utilisé mais requis par l'interface KeyListener
    }
}
