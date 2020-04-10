import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.io.*;
import javax.imageio.*;
import javax.sound.midi.*;
import java.applet.*;

public class Player extends JPanel{
	private int x,y,bx,bx2,dx,hp,sp;
	private Image player;
	private boolean scroll;
	private Character weeb;

    public Player(Character w) {
		player = new ImageIcon("Pictures/player.jpg").getImage();
		weeb = w;
    	x = 120;
		y = 400;
		bx2 = 1185;//variable for scrolling background
		bx = 0;
		dx = 0;
		scroll = false;
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
	public void setDx(int n){
    	dx = n;
	}
	public boolean checkScroll(){
    	return scroll;
	}
	public void setScroll(boolean n){
		if (n) scroll = true;
		else scroll = false;
	}
	public int getHP(){
		return hp;
	}
	//yo
	public void moveX(){
    	x += dx;
    	if (scroll){
			bx += dx;
			bx2 += dx;
		}
	}
	public Image getFrame(String motion, int num){
		if(motion.equals("run left")){
			return weeb.getRunLeft(num);
		}
		if(motion.equals("run right")){
			return weeb.getRunRight(num);
		}
		else{
			return null;
		}
	}
    public void moveY(int num){
    	y += num;
    }

    public Image getImage(){
    	return player;
	}
}

    
