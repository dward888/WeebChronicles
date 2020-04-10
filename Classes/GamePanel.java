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
	
	//images//
	private Image back;
	//private Image player;

    public GamePanel(WeebChronicles m) {
    	keys = new boolean[KeyEvent.KEY_LAST+1];
		mainFrame = m;
		p = new Player();
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

    	//hi hi
    	
    }

    public void move(){
        if (keys[KeyEvent.VK_RIGHT]) {
            p.setDx(3);
            p.moveX();
        }
        if (keys[KeyEvent.VK_LEFT]) {
            p.setDx(-3);
            p.moveX();
        }
    }
    
}