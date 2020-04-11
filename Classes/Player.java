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
	private int currentF;

	//Directions
	private int still;
	private int right;
	private int left;
	private int fallRight;
	private int fallLeft;
	private int fallDown;
	private int jumpRight;
	private int jumpLeft;
	private int jumpUp;

    public Player(Character w) {
		weeb = w;
    	x = 120;
		y = 400;
		bx2 = 1185;//variable for scrolling background
		bx = 0;
		dx = 0;
		scroll = false;
		currentF = 0;

		//Direction
		still = 0;
		left = 1;
		right = 2;
		jumpLeft= 3;
		jumpRight = 4;
		jumpUp = 5;
		fallLeft = 6;
		fallRight = 7;
		fallDown = 8;
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
		scroll = n;
	}
	public int getHP(){
		return hp;
	}

	public void moveX(){
    	if (scroll){
			bx += dx;
			bx2 += dx;
		}
    	else{
    		x += dx;
		}
	}
	public Image getFrame(int motion){
    	System.out.println(currentF);
		if(motion == left){
			if(currentF + 1 == weeb.getRunLeft().length){
				currentF = 0;
			}
			else{
				currentF ++;
			}
			return weeb.getRunLeft()[currentF];
		}
		if(motion == right){
			if(currentF + 1 == weeb.getRunRight().length){
				currentF = 0;
			}
			else{
				currentF ++;
			}
			return weeb.getRunRight()[currentF];
		}
		if(motion == fallLeft){
			currentF ++;
			if(currentF < 5){
				return weeb.getFallStartLeft();
			}
			else{
				return weeb.getFallLeft();
			}
		}
		if(motion == fallRight || motion == fallDown){
			currentF ++;
			if(currentF < 8){
				return weeb.getFallStartRight();
			}
			else{
				return weeb.getFallRight();
			}
		}
		if(motion == jumpLeft){
			currentF ++;
			if(currentF < 8){
				return weeb.getJumpStartLeft();
			}
			else{
				return weeb.getJumpLeft();
			}
		}
		if(motion == jumpRight || motion == jumpUp){
			currentF ++;
			if(currentF < 8){
				return weeb.getJumpStartRight();
			}
			else{
				return weeb.getJumpRight();
			}
		}
		//motion == still
		else{
			currentF = 0;
			return weeb.getStanding();
		}
	}
    public void moveY(int num){
    	y += num;
    }
    public void resetCurrentF(){
    	currentF = 0;
	}

}

    
