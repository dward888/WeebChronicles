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
	private String direction;
	private int frame;
	
	//images//
	private Image back;
	//private Image player;

    public GamePanel(WeebChronicles m) {
    	keys = new boolean[KeyEvent.KEY_LAST+1];
		mainFrame = m;
		chars = new Character[10];
		loadCharacters();
        p = new Player(chars[0]);
        direction = "still";
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
        keys[e.getKeyCode()] = true;
    }
    public void keyReleased(KeyEvent e){
        keys[e.getKeyCode()] = false;
        direction = "still";
    }

    public void loadCharacters(){
        Character itachi = new Character("Itachi", 100, 100, 100 );
        chars[0] = itachi;
    }

    public void paintComponent(Graphics g){

    	//g.drawImage(back,1185-p.getBx(),0,null);
        if ((p.getX() - 850) %  3840 == 0){
            p.setBx(0);
        }
        if ((p.getX() - 2770) % 3840 == 0){
            p.setBx2(0);
        }
		g.drawImage(back,1185-p.getBx2(),0,null);
        if (p.getX() >= 850){
            g.drawImage(back,1185-p.getBx(),0,null);
        }
    	g.drawImage(p.getImage(),70/*p.getX()*/,p.getY(),null);

        if(direction.equals("still")){
            g.drawImage(p.getFrame("standing", 0), p.getX(), p.getY(), null);
        }
        if(direction.equals("right")){
            if(frame % 5 == 0){
                g.drawImage(p.getFrame("run right", 0), p.getX(), p.getY(), null);
            }
        }
        frame ++;
    }//yugkjhg

    public void move(){
        if (keys[KeyEvent.VK_RIGHT]) {
            p.setDx(3);
            p.moveX();
            direction = "right";
        }
        if (keys[KeyEvent.VK_LEFT]) {
            p.setDx(-3);
            p.moveX();
            direction = "left";
        }
    }
    
}