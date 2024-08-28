import javax.swing.JFrame;

// La classe PongGame est la classe principale qui configure et lance la fenêtre du jeu Pong.
// Elle hérite de JFrame, ce qui lui permet de créer une fenêtre graphique avec toutes les fonctionnalités d'une application Swing.

public class PongGame extends JFrame {

    // Déclaration d'une instance de GamePanel, qui contient la logique et le rendu
    // du jeu.
    private GamePanel gamePanel;

    // Constructeur de la classe PongGame.
    // C'est ici que la fenêtre du jeu est configurée et que le GamePanel est ajouté
    // à cette fenêtre.
    public PongGame() {
        // Initialisation de l'instance de GamePanel.
        gamePanel = new GamePanel();

        // Ajout de GamePanel à la fenêtre JFrame.
        add(gamePanel);

        // Définition du titre de la fenêtre.
        setTitle("Pong Game");

        // Empêche le redimensionnement de la fenêtre pour maintenir la taille fixe du
        // jeu.
        setResizable(false);

        // Ajuste la taille de la fenêtre en fonction du contenu, ici le GamePanel.
        pack();

        // Assure que l'application se ferme complètement lorsque la fenêtre est fermée.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Centre la fenêtre sur l'écran.
        setLocationRelativeTo(null);

        // Rendre la fenêtre visible.
        setVisible(true);
    }

    // Méthode principale qui lance l'application.
    // C'est le point d'entrée du programme.
    public static void main(String[] args) {
        // Crée une nouvelle instance de PongGame, ce qui déclenche l'ouverture de la
        // fenêtre et le démarrage du jeu.
        new PongGame();
    }
}
