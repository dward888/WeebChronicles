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
	private Image player;

    public GamePanel(WeebChronicles m) {
    	keys = new boolean[KeyEvent.KEY_LAST+1];
		mainFrame = m;
		p = new Player();
		addKeyListener(this);
		
		//loading images//
		back = new ImageIcon("back.jpg").getImage();
		player = new ImageIcon("player.jpg").getImage();
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
    	/*if ((p.getX() - 1000) % 2400 == 0){
    		p.setBx2(0);
    	}
    	if ((p.getX() - 2200) % 2400 == 0){
    		p.setBx(0);
    		
    	}*/
    	g.drawImage(back,1185-p.getBx(),0,null);
    	if ((p.getX() - 830) % 3840 == 0){
    		p.setBx2(0);
    	}
    	if ((p.getX() - 2750) % 3840 == 0){ /*(830 + the length of the background png*/
    		p.setBx(0);
    	}
    	
    	if (p.getX() >= 830){
    		g.drawImage(back,1185-p.getBx(),0,null);
    	}
    	System.out.println(p.getX());
    	/*if (p.getX() >= 1000){
    		g.drawImage(back,1185-p.getBx2(),0,null);
    	}*/
    	g.drawImage(player,120/*p.getX()*/,p.getY(),null);
    	
    	
    	
    }
    
    public void move(){
    	
    	if(keys[KeyEvent.VK_RIGHT]){
			p.moveX(1);
		}
    	
    	//if(p.getX() > 0){
			if(keys[KeyEvent.VK_LEFT]){
				p.moveX(-1);
			}
    	//}
    }
    
}