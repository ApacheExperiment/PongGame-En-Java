import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

// La classe Paddle représente une raquette dans le jeu Pong. 
// Elle hérite de la classe Rectangle, ce qui lui permet d'avoir des propriétés 
// géométriques (position, taille) et de gérer les collisions facilement.

public class Paddle extends Rectangle {

    // La vitesse de déplacement en pixels par rafraîchissement de l'écran.
    private int yVelocity;
    private final int speed = 10;

    // Constructeur de la classe Paddle.
    // Initialise une raquette à une position donnée avec une largeur et une hauteur
    // spécifiques.
    public Paddle(int x, int y, int PADDLE_WIDTH, int PADDLE_HEIGHT) {
        super(x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
    }

    // Définit la direction du mouvement en fonction de l'entrée utilisateur ou de
    // l'IA.
    // 'direction' est multiplié par la vitesse pour obtenir la vélocité en y.
    public void setYDirection(int direction) {
        yVelocity = direction * speed;
    }

    // Déplace la raquette verticalement en fonction de la vélocité.
    public void move() {
        y += yVelocity;
        // Empêche la raquette de sortir de l'écran en la limitant aux bords supérieurs
        // et inférieurs.
        if (y < 0)
            y = 0;
        if (y > GamePanel.GAME_HEIGHT - height)
            y = GamePanel.GAME_HEIGHT - height;
    }

    // Dessine la raquette sur l'écran en utilisant les paramètres de la classe
    // Rectangle.
    public void draw(Graphics g) {
        g.setColor(Color.WHITE); // Couleur de la raquette : blanc.
        g.fillRect(x, y, width, height); // Dessine la raquette comme un rectangle rempli.
    }
}
