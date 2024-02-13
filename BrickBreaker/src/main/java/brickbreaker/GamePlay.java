/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brickbreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author A1
 */
public class GamePlay extends JPanel implements KeyListener, ActionListener {

    private boolean play = false;
    private int score = 0;
    private int totalBrick = 21;
    private Timer timer;
    private int delay = 8;
    private int playerX = 310;
    private int ballposX = 120;
    private int ballposY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;
    private MapGen map;

    public GamePlay() {
        map = new MapGen(3,7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();

    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(1, 1, 692, 592);
        
        map.draw((Graphics2D)g);

        g.setColor(Color.yellow);
        g.fillRect(0, 0, 4, 592);//left
        g.fillRect(0, 0, 692, 4);//up
        g.fillRect(680, 0, 4, 592);//right

        g.setColor(Color.blue);
        g.fillRect(playerX, 550, 100, 8);//pedal

        g.setColor(Color.green);
        g.fillOval(ballposX, ballposY, 20, 20);//ball
        
        g.setColor(Color.black);
        g.setFont(new Font("serif", Font.BOLD,25));
        g.drawString(""+ score , 590, 30);
        
        if (totalBrick<=0){
            play = false;
            ballXdir = 0;
            ballYdir = 0 ;
            g.setColor(Color.green);
            g.setFont(new Font("serif", Font.BOLD , 30));
            g.drawString("You Won, Score: "+ score, 190, 300);
            
            g.setFont(new Font("serif", Font.BOLD,20));
            g.drawString("Press Enter to Restart. ", 230, 350);
            
        }
        
        if(ballposY>570){
            play = false;
            ballXdir = 0;
            ballYdir = 0 ;
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD , 30));
            g.drawString("Game Over, Score: "+ score, 190, 300);
            
            g.setFont(new Font("serif", Font.BOLD,20));
            g.drawString("Press Enter to Restart. ", 230, 350);
            
        }

        g.dispose();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 600) {
                playerX = 600;
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            if(!play){
                play = true;
                ballposX=120;
                ballposY = 350;
                ballXdir = -1;
                ballYdir = -2;
                score = 0;
                totalBrick=21;
                map = new MapGen(3, 7);
                repaint();

            }
        }
    }
    

    public void moveRight() {
        play = true;
        playerX += 20;
    }

    public void moveLeft() {
        play = true;
        playerX -= 20;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (play) {
            //Ball - padal instreaction
            if (new Rectangle(ballposX, ballposY, 20, 30).intersects(new Rectangle(playerX, 550, 100, 8))) {
                ballYdir = -ballYdir;
            }
            for(int i = 0; i<map.map.length;i++){
                for (int j = 0 ; j<map.map[0].length;j++){
                    if(map.map[i][j]> 0){
                        int brickX = j*map.brickWidth + 80;
                        int brickY = i*map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeigt = map.brickHeight;
                        
                        Rectangle rect = new Rectangle(brickX, brickY,brickWidth, brickHeigt);
                        Rectangle ballRect = new Rectangle(ballposX,ballposY, 20 ,20);
                        Rectangle brickRect = rect;
                        
                        if(ballRect.intersects(brickRect)){
                            map.setBrickValue(0, i, j);
                            totalBrick--;
                            score+=5;
                            
                            if(ballposX + 19 <= brickRect.x || ballposX +1 >= brickRect.x + brickRect.width){
                                ballXdir = -ballXdir;
                            }
                            else {
                                ballYdir = -ballYdir;
                            }
                        }
                    }
                }
            }

            ballposX += ballXdir;
            ballposY += ballYdir;
            if (ballposX < 0) { //touche wall
                ballXdir = -ballXdir;

            }
            if (ballposY < 0) {
                ballYdir = -ballYdir;
            }
            if (ballposX > 670) {
                ballXdir = -ballXdir;
            }
        }
        repaint();
    }
}
