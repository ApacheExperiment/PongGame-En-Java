import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

// La classe Ball représente la balle dans le jeu Pong. Elle hérite de la classe Rectangle, 
// ce qui permet de manipuler la balle comme une forme rectangulaire avec une position et une dimension.

public class Ball extends Rectangle {

    // Vitesse actuelle de la balle en x et y.
    private int xVelocity;
    private int yVelocity;

    // Vitesse initiale de la balle.
    private int initialSpeed = 5;

    // Constructeur de la classe Ball. Il initialise la position de la balle et sa
    // direction initiale de mouvement.
    public Ball(int x, int y, int BALL_DIAMETER) {
        super(x, y, BALL_DIAMETER, BALL_DIAMETER); // Appel du constructeur de la classe Rectangle pour définir la
                                                   // taille et la position.
        Random random = new Random();

        // Définir une direction initiale aléatoire pour le mouvement en x.
        int randomXDirection = random.nextInt(2); // Retourne 0 ou 1.
        if (randomXDirection == 0)
            randomXDirection--; // Si c'est 0, on le transforme en -1.
        setXDirection(randomXDirection * initialSpeed); // Applique la vitesse initiale à la direction.

        // Définir une direction initiale aléatoire pour le mouvement en y.
        int randomYDirection = random.nextInt(2); // Retourne 0 ou 1.
        if (randomYDirection == 0)
            randomYDirection--; // Si c'est 0, on le transforme en -1.
        setYDirection(randomYDirection * initialSpeed); // Applique la vitesse initiale à la direction.
    }

    // Définit la direction de la balle en x.
    public void setXDirection(int direction) {
        xVelocity = direction;
    }

    // Définit la direction de la balle en y.
    public void setYDirection(int direction) {
        yVelocity = direction;
    }

    // Getter pour récupérer la vitesse actuelle en x.
    public int getXVelocity() {
        return xVelocity;
    }

    // Déplace la balle selon sa vitesse actuelle. Change la direction si la balle
    // touche le haut ou le bas de l'écran.
    public void move() {
        x += xVelocity; // Déplace la balle horizontalement.
        y += yVelocity; // Déplace la balle verticalement.

        // Si la balle touche le bord supérieur ou inférieur du jeu, inverse la
        // direction verticale.
        if (y <= 0 || y >= GamePanel.GAME_HEIGHT - height) {
            setYDirection(-yVelocity);
        }
    }

    // Dessine la balle sur l'écran en utilisant les coordonnées actuelles.
    public void draw(Graphics g) {
        g.setColor(Color.WHITE); // Définit la couleur de la balle.
        g.fillOval(x, y, width, height); // Dessine un ovale blanc représentant la balle.
    }
}
