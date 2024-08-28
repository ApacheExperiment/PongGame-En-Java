import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

// La classe GamePanel représente le panneau principal du jeu Pong. 
// Elle gère les éléments graphiques du jeu, les interactions utilisateur, 
// ainsi que la logique du jeu, comme les mouvements de la balle et des raquettes.

public class GamePanel extends JPanel implements Runnable, KeyListener {

    // Dimensions de l'écran du jeu.
    static final int GAME_WIDTH = 800;
    static final int GAME_HEIGHT = 600;
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);

    // Dimensions de la balle et des raquettes.
    static final int BALL_DIAMETER = 20;
    static final int PADDLE_WIDTH = 25;
    static final int PADDLE_HEIGHT = 100;

    // Variables pour la gestion du jeu.
    private Thread gameThread; // Le thread principal du jeu.
    private Image image; // Image pour le rendu graphique.
    private Graphics graphics; // Graphiques pour dessiner les éléments.
    private Paddle playerPaddle; // Raquette du joueur.
    private Paddle aiPaddle; // Raquette de l'IA.
    private Ball ball; // La balle du jeu.
    private Timer aiTimer; // Timer pour contrôler l'IA.
    private int playerScore = 0; // Score du joueur.
    private int aiScore = 0; // Score de l'IA.
    private final int WINNING_SCORE = 15; // Score nécessaire pour gagner.

    // Constructeur du panneau de jeu.
    public GamePanel() {
        newPaddles(); // Crée les raquettes.
        newBall(); // Crée la balle.
        this.setFocusable(true); // Permet à ce panneau de recevoir les événements clavier.
        this.addKeyListener(this); // Ajoute un écouteur pour les événements clavier.
        this.setPreferredSize(SCREEN_SIZE); // Définit la taille préférée du panneau de jeu.

        // Initialisation du timer de l'IA. Le mouvement de l'IA est mis à jour toutes
        // les 30 ms.
        aiTimer = new Timer(30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveAI(); // Déplace la raquette de l'IA.
            }
        });
        aiTimer.start(); // Démarre le timer de l'IA.

        // Démarrage du thread principal du jeu.
        gameThread = new Thread(this);
        gameThread.start();
    }

    // Crée une nouvelle balle au centre de l'écran.
    public void newBall() {
        ball = new Ball(GAME_WIDTH / 2 - BALL_DIAMETER / 2, GAME_HEIGHT / 2 - BALL_DIAMETER / 2, BALL_DIAMETER);
    }

    // Crée les raquettes du joueur et de l'IA.
    public void newPaddles() {
        playerPaddle = new Paddle(20, GAME_HEIGHT / 2 - PADDLE_HEIGHT / 2, PADDLE_WIDTH, PADDLE_HEIGHT);
        aiPaddle = new Paddle(GAME_WIDTH - PADDLE_WIDTH - 20, GAME_HEIGHT / 2 - PADDLE_HEIGHT / 2, PADDLE_WIDTH,
                PADDLE_HEIGHT);
    }

    // Déplace les éléments du jeu (raquette du joueur et balle).
    public void move() {
        playerPaddle.move();
        ball.move();
    }

    // Logique de déplacement de l'IA en fonction de la position de la balle.
    public void moveAI() {
        if (ball.y < aiPaddle.y) {
            aiPaddle.setYDirection(-1);
        } else if (ball.y > aiPaddle.y + aiPaddle.height) {
            aiPaddle.setYDirection(1);
        } else {
            aiPaddle.setYDirection(0);
        }
        aiPaddle.move();
    }

    // Vérifie les collisions de la balle avec les raquettes et les bords du
    // terrain.
    public void checkCollision() {
        // Si la balle touche la raquette du joueur, elle rebondit.
        if (ball.intersects(playerPaddle)) {
            ball.setXDirection(-ball.getXVelocity());
        }
        // Si la balle touche la raquette de l'IA, elle rebondit.
        if (ball.intersects(aiPaddle)) {
            ball.setXDirection(-ball.getXVelocity());
        }

        // Si la balle sort du terrain par la gauche (point pour l'IA).
        if (ball.x <= 0) {
            aiScore++;
            checkWinCondition(); // Vérifie si l'IA a gagné.
            newBall(); // Réinitialise la balle au centre.
        }
        // Si la balle sort du terrain par la droite (point pour le joueur).
        if (ball.x >= GAME_WIDTH - BALL_DIAMETER) {
            playerScore++;
            checkWinCondition(); // Vérifie si le joueur a gagné.
            newBall(); // Réinitialise la balle au centre.
        }
    }

    // Vérifie si le joueur ou l'IA a atteint le score pour gagner.
    private void checkWinCondition() {
        if (playerScore >= WINNING_SCORE) {
            stopGame("Félicitations ! Vous avez gagné la partie !");
        } else if (aiScore >= WINNING_SCORE) {
            stopGame("Dommage ! L'IA a remporté la partie !");
        }
    }

    // Arrête le jeu et affiche un message de fin de partie.
    private void stopGame(String message) {
        // Afficher un message contextuel avec l'information du résultat
        JOptionPane.showMessageDialog(this, message, "Partie terminée", JOptionPane.INFORMATION_MESSAGE);

        // Ferme le jeu après un délai pour permettre à l'utilisateur de lire le
        // message.
        Timer timer = new Timer(1500, e -> System.exit(0));
        timer.setRepeats(false);
        timer.start();
    }

    // Méthode pour dessiner le jeu à l'écran.
    public void paint(Graphics g) {
        image = createImage(getWidth(), getHeight());
        graphics = image.getGraphics();
        draw(graphics); // Dessine tous les éléments du jeu.
        g.drawImage(image, 0, 0, this); // Affiche l'image sur l'écran.
    }

    // Dessine les éléments du jeu (balle, raquettes, score).
    public void draw(Graphics g) {
        g.setColor(Color.BLACK); // Fond noir
        g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        playerPaddle.draw(g);
        aiPaddle.draw(g);
        ball.draw(g);

        // Dessin du score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Consolas", Font.PLAIN, 50));
        g.drawString(String.valueOf(playerScore), GAME_WIDTH / 2 - 100, 50);
        g.drawString(String.valueOf(aiScore), GAME_WIDTH / 2 + 65, 50);
    }

    // Boucle principale du jeu. Contrôle le timing du jeu et rafraîchit
    // l'affichage.
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
                move(); // Déplace les éléments du jeu.
                checkCollision(); // Vérifie les collisions.
                repaint(); // Redessine l'écran.
                delta--;
            }
        }
    }

    // Gestion des événements clavier : déplacement de la raquette du joueur.
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            playerPaddle.setYDirection(-1); // Déplace la raquette vers le haut.
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            playerPaddle.setYDirection(1); // Déplace la raquette vers le bas.
        }
    }

    // Arrêt du déplacement de la raquette du joueur quand la touche est relâchée.
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
            playerPaddle.setYDirection(0); // Arrête le mouvement de la raquette.
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Non utilisé mais requis par l'interface KeyListener
    }
}
