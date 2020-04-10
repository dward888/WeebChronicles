import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.io.*;
import javax.imageio.*;
import javax.sound.midi.*;
import java.applet.*;

public class Player extends JPanel implements KeyListener{
	private int x,y,bx,bx2,dx,hp,sp;
	private Image player;
	private boolean []keys;

    public Player() {
		keys = new boolean[KeyEvent.KEY_LAST+1];
		addKeyListener(this);
		player = new ImageIcon("Pictures/player.jpg").getImage();
    	x = 120;
		y = 400;
		bx = 1185;//variable for scrolling background 
		bx2 = 0;

    }

    public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public int getBx(){
		return bx;
	}
	public void setBx(int n){
		bx = n;
	}
	public int getBx2(){
		return bx2;
	}
	public void setBx2(int n){
		bx2 = n;
	}
	
	public int getHP(){
		return hp;
	}

    public void moveX(int num){
    	x += num;
    	bx += num;
    	bx2 += num;
    }
    public void moveY(int num){
    	y += num;
    }

    public Image getImage(){
    	return player;
	}
	public void keyTyped(KeyEvent e){
	}
	public void keyPressed(KeyEvent e){
		keys[e.getKeyCode()] = true;
	}
	public void keyReleased(KeyEvent e){
		keys[e.getKeyCode()] = false;
	}


    
}