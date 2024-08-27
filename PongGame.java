import javax.swing.JFrame;

public class PongGame extends JFrame {

    private GamePanel gamePanel;

    public PongGame() {
        gamePanel = new GamePanel();
        add(gamePanel);
        setTitle("Pong Game");
        setResizable(false);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new PongGame();
    }
}
