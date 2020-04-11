import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.io.*;
import javax.imageio.*;
import javax.sound.midi.*;
import java.applet.*;

public class GamePanel extends JPanel implements KeyListener{
	private boolean []keys;
	private WeebChronicles mainFrame;
	private Player p;
	private Character[]chars;
	private int still;
	private int right;
	private int left;
	private int direction;
	private int frame;
	private boolean midAir;

	//images//
	private Image back;


    public GamePanel(WeebChronicles m) {
    	keys = new boolean[KeyEvent.KEY_LAST+1];
		mainFrame = m;
		chars = new Character[10];
		loadCharacters();
        p = new Player(chars[0]);
        midAir = false;

        //Direction
        still = 0;
        left = 1;
        right = 2;
        direction = still;

        frame = 0;

		addKeyListener(this);

		//loading images//
		back = new ImageIcon("Pictures/back.jpg").getImage();

    }
    
    public void addNotify() {
        super.addNotify();
        requestFocus();
        mainFrame.start();
    }

    public void keyTyped(KeyEvent e){
    }
    public void keyPressed(KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_UP && !keys[e.getKeyCode()]){
           if(!midAir){
               p.jump();
               midAir = true;
           }
        }
        keys[e.getKeyCode()] = true;
    }
    public void keyReleased(KeyEvent e){
        keys[e.getKeyCode()] = false;
        if(!midAir){
            direction = still;
            p.resetCurrentF();
        }
    }

    public void loadCharacters(){
        Character itachi = new Character("Itachi", 100, 100, 100, 6 , 6);
        chars[0] = itachi;
    }

    public void paintComponent(Graphics g){
        //background
        g.drawImage(back, 0, 0, null);

        //drawing the sprites for their respective direction
        if(direction == still && !midAir){
            g.drawImage(p.getFrame(still), p.getX(), p.getY(), null);
        }
        if(direction == right && !midAir){
            if (p.checkScroll()){
                g.drawImage(p.getFrame(right), 850, p.getY(), null);
            }
            else if (!p.checkScroll()){
                g.drawImage(p.getFrame(right), p.getX()-10, p.getY(), null);
            }
        }
        if(direction == left && !midAir){
            g.drawImage(p.getFrame(left), p.getX()-10, p.getY(), null);
        }
        if(midAir){
            if(direction == left){
                //Going Up
                if(p.getSy() < 0) {
                    if (p.getCurrentF() > 5) {
                        g.drawImage(p.getJumpL()[0], p.getX() - 10, p.getY(), null);
                    } else {
                        g.drawImage(p.getJumpL()[1], p.getX() - 10, p.getY(), null);
                    }
                    p.addCurrentF();
                }
                //Going Down
                if(p.getSy() > 0){
                    g.drawImage(p.getFallL()[0], p.getX()-10, p.getY(), null);
                }
            }
            if(direction == right){
                //Going Up
                if(p.getSy() < 0){
                    if (p.getCurrentF() > 5) {
                        g.drawImage(p.getJumpR()[0], p.getX() - 10, p.getY(), null);
                    }
                    else {
                        g.drawImage(p.getJumpR()[1], p.getX() - 10, p.getY(), null);
                    }
                    p.addCurrentF();
                }
                //Going Down
                if(p.getSy() > 0){
                    g.drawImage(p.getFallR()[0], p.getX()-10, p.getY(), null);
                }
            }
        }
        frame ++;
    }

    //user moving the character f
    public void move(){
        if (keys[KeyEvent.VK_RIGHT]) {
            p.update(right);
            p.runR();
            direction = right;
        }
        if (keys[KeyEvent.VK_LEFT]) {
            p.update(left);
            p.runL();
            direction = left;
        }
        if(keys[KeyEvent.VK_UP]){
            //p.jump();
            //direction = jumpUp;
            //direction = p.update(jumpUp);
        }
    }

    public void playerUpdate(){
        p.update2();
        if(p.getSy() == 0){
            midAir = false;
            p.resetCurrentF();
        }
    }
}