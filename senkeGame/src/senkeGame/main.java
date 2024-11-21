package senkeGame;

import java.awt.Color;
import javax.swing.JFrame;

/**
 *
 * @author A1
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GamePlay gameplay= new GamePlay();
        // TODO code application logic here
        JFrame obj = new JFrame();
        obj.setBounds(10, 10, 905, 700);
        obj.setBackground(Color.DARK_GRAY);
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(gameplay);
    }
    
}
