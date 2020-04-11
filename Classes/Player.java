import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.io.*;
import javax.imageio.*;
import javax.sound.midi.*;
import java.applet.*;

public class Player{
	private int x,y,sx,sy,hp,sp;
	private Image player;
	private boolean scroll;
	private Character weeb;
	private int currentF;

	//Direction
	private int still;
	private int right;
	private int left;
	private int fallRight;
	private int fallLeft;
	private int fallDown;
	private int jumpRight;
	private int jumpLeft;
	private int jumpUp;

	public static final double FRICTION = 0.99;
	public static final double GRAVITY = 0.01;
	public static final double SPEED = 5;
	public static final double JUMPSTRENGTH = 5;

    public Player(Character w) {
		weeb = w;
    	x = 120;
		y = 400;

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


	public void setSx(int n){
    	sx = n;
	}
	public void setSy(int n){
		sy = n;
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

	public void move(double xDelta, double yDelta){
		x += xDelta;
		y += yDelta;
		//do collision detection here, if collide set the speeds to 0
	}

	public void accelerate(double accelX, double accelY){
		sx += accelX;
		sy += accelY;
	}
	public void update(){
		move(sx,sy);
		sx *= FRICTION;
		sy *= FRICTION;
		accelerate(0, -GRAVITY);
	}
	public void runR(){
		move(SPEED, 0);
	}
	public void runL(){
		move(-SPEED, 0);
	}
	public void jump(){
		move(0,-20);
		accelerate(0,JUMPSTRENGTH);
	}

    public void resetCurrentF(){
    	currentF = 0;
	}

}

    
