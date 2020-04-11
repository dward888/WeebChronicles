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

	//images//
	private Image back;


    public GamePanel(WeebChronicles m) {
    	keys = new boolean[KeyEvent.KEY_LAST+1];
		mainFrame = m;
		chars = new Character[10];
		loadCharacters();
        p = new Player(chars[0]);

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
           p.jump();//hi

        }
        keys[e.getKeyCode()] = true;
    }
    public void keyReleased(KeyEvent e){
        keys[e.getKeyCode()] = false;

        direction = still;

        p.resetCurrentF();
    }

    public void loadCharacters(){
        Character itachi = new Character("Itachi", 100, 100, 100, 6 , 6);
        chars[0] = itachi;
    }

    public void paintComponent(Graphics g){


        //background
        g.drawImage(back, 0, 0, null);




        //drawing the sprites for their respective direction
        if(direction == still){
            g.drawImage(p.getFrame(still), p.getX(), p.getY(), null);
        }
        if(direction == right){
            if (p.checkScroll()){
                g.drawImage(p.getFrame(right), 850, p.getY(), null);
            }
            else if (!p.checkScroll()){
                g.drawImage(p.getFrame(right), p.getX()-10, p.getY(), null);
            }
        }
        if(direction == left){
            g.drawImage(p.getFrame(left), p.getX()-10, p.getY(), null);
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
    }


}