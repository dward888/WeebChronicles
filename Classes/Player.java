//Player.java
//Jim Ji and Edward Yang
//Class that keeps track of al the players' aspects in the level

import java.awt.*;
public class Player{
	private int x,y,width,height;
	private double sy,sx;
	private String name;
	private boolean gotHit;
	private int currentF;
	private int score;
	private int lives;

	//physics variables
	public static final double GRAVITY = 0.4;
	public static final double SPEED = 5;
	public static final double JUMPSTRENGTH = -10;

    public Player() {
		name = "Ryan Funyanjiwan";
		x = 140;
		y = 340;
		width = 40;
		height = 40;
		currentF = 0;
		score = 0;
		lives = 5;
		gotHit = false;
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
	public void setSy(double n){
		sy = n;
	}
	public void knockback(int n){x = x + n;};
	public void setX(int n){
    	x = n;
	}
	public void setY(int n){
    	y = n;
	}
	public int getScore(){
    	return score;
	}
	public void addScore(int n){
    	score += n;
	}

	public int getHeight(){
		return height;
	}

	public void move(double xDelta, double yDelta){
		x += xDelta;
		y += yDelta;
	}
	public void accelerate(double accelX, double accelY){//allows for the acceleration of gravity to apply onto the player
		sx += accelX;
		sy += accelY;
	}
	public int direct(int direct){
		return direct;
	}
	public void update(){
		move(sx,sy);
		accelerate(0, GRAVITY);//starting the gravity
		if (y > 590){//if they are under the base platforms
			sy = 0;
			y = 590;
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
	public Rectangle getRect(){
		return new Rectangle(x+5,y+12,40,45);
	}

	public Rectangle getRHitRect(){//rect that is slightly larger to track the attack range
    	return new Rectangle(x+5,y+12,100,55);
	}
	public Rectangle getLHitRect(){
    	return new Rectangle(x-50,y+12,100,55);
	}

	public int getLives() {
		return lives;
	}
	public void loseLife(){
    	lives --;
	}
	public void gainLife(){
    	lives ++;
	}
	public boolean checkHit(){
    	return gotHit;
	}
	public void setHit(boolean n) {
		gotHit = n;
	}

}

    
