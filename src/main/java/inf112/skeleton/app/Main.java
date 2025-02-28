package inf112.skeleton.app;

import javax.swing.JFrame;

import com.badlogic.gdx.Game;


public class Main {
    public static void main(String[] args) {

       JFrame window = new JFrame();
       window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       window.setResizable(false);
       window.setTitle("Stairs");

       GamePanel gamePanel = new GamePanel();
       window.add(gamePanel); 
       window.pack();  

    
       
       window.setLocationRelativeTo(null);
       window.setVisible(true);

       gamePanel.startGameThread();
    
    }
}
