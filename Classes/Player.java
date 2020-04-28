import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.io.*;
import javax.imageio.*;
import javax.sound.midi.*;
import java.applet.*;

public class Player{
	private int x,y,width,height,hp,sp;
	private int bx,bx2;
	private double sy,sx;
	private Image player;
	private boolean scroll;
	private Character weeb;
	private int currentF;

	//Direction pen
	private int stillRight;
	private int right;
	private int left;
	private int stillLeft;
	private int bulletRight;
	private int bulletLeft;

	public static final double FRICTION = 0.99;
	public static final double GRAVITY = 0.4;
	public static final double SPEED = 5;
	public static final double JUMPSTRENGTH = -10;

    public Player(Character w) {
		weeb = w;
    	x = 120;
		y = 400;
		width = 40;
		height = 40;
		scroll = false;
		currentF = 0;
		bx2 = 1185;
		bulletLeft = 4;
		bulletRight = 5;

		//Direction
		stillRight = 0;
		left = 1;
		right = 2;
		stillLeft = 3;

    }

    public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public double getSy(){
    	return sy;
	}
	public double getSx(){
    	return sx;
	}
	public void setSx(double n){
    	sx = n;
	}
	public void setSy(double n){
		sy = n;
	}
	public int getBx() {
		return bx;
	}
	public int getBx2(){
    	return bx2;
	}
	public boolean checkScroll(){
    	if (x >= 650){
    		scroll = true;
		}
    	return scroll;
	}
	public void setScroll(boolean n){
		scroll = n;
	}
	public int getHP(){
		return hp;
	}
	public void setX(int n){
    	x = n;
	}
	public void setY(int n){
    	y = n;
	}

	public Image getFrame(int motion){
    	//System.out.println(currentF);
		if(motion == left){
			if(currentF + 1 >= weeb.getRunLeft().length){
				currentF = 0;
			}
			else{
				currentF ++;
			}
			return weeb.getRunLeft()[currentF];
		}
		if(motion == right){
			if(currentF + 1 >= weeb.getRunRight().length){
				currentF = 0;
			}
			else{
				currentF ++;
			}
			return weeb.getRunRight()[currentF];
		}
		if(motion == stillLeft){
			currentF = 0;
			return weeb.getStandingLeft();
		}
		//motion == stillRight
		else{
			currentF = 0;
			return weeb.getStandingRight();//yo
		}
	}

	public Image getBulFrame(Bullet b){
    	if(b.getDirection() == right){
    		if(b.getFrame() + 1 >= weeb.getSpARight().length){
    			b.resetF();
			}
    		else{
    			b.nextFrame();
			}
    		return weeb.getSpARight()[b.getFrame()];
		}
    	//b.getDirection() == left
		else{
			if(b.getFrame() + 1 >= weeb.getSpALeft().length){
				b.resetF();
			}
			else{
				b.nextFrame();
			}
			return weeb.getSpALeft()[b.getFrame()];
		}
	}
	public Image[]getJumpR(){
    	return weeb.getJumpRight();
	}
	public Image[]getJumpL(){
    	return weeb.getJumpLeft();
	}
	public Image[]getFallL(){
		return weeb.getFallLeft();
	}
	public Image[]getFallR(){
		return weeb.getFallRight();
	}
	public void move(double xDelta, double yDelta){
		x += xDelta;
		y += yDelta;
	}
	public void accelerate(double accelX, double accelY){
		sx += accelX;
		sy += accelY;
	}
	public int update(int direct){
		//ove(sx,sy);
		//sx *= FRICTION;
		//sy *= FRICTION;
		//accelerate(0, GRAVITY);
		//if (y > 500){
		//	sy = 0;
		//	y = 500;
		//}
		return direct;
	}
	public void update2(){
		move(sx,sy);
		//sx *= FRICTION;
		//sy *= FRICTION;
		accelerate(0, GRAVITY);
		if (y > 500){
			sy = 0;
			y = 500;
		}
	}
	public void runR(){
		move(SPEED, 0);
	}
	public void runL(){
		move(-SPEED, 0);
	}
	public void jump(){
		accelerate(0,JUMPSTRENGTH);
	}
	public void resetCurrentF(){
		currentF = 0;
	}
	public int getCurrentF(){
		return currentF;
	}
	public void addCurrentF(){
		currentF ++;
	}
	public Rectangle getRect(){
		return new Rectangle(x,y+8,40,60);
	}
}

    
