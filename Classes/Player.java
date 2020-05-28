import java.awt.*;

public class Player{
	private int x,y,width,height,hp,sp;
	private int bx,bx2;
	private double sy,sx;
	private String name;
	private boolean scroll;
	private int currentF;

	public static final double FRICTION = 0.99;
	public static final double GRAVITY = 0.4;
	public static final double SPEED = 5;
	public static final double JUMPSTRENGTH = -10;

    public Player() {
		name = "Ryan Funyanjiwan";
		x = 120;
		y = 400;
		width = 40;
		height = 40;
		scroll = false;
		currentF = 0;
		bx2 = 1185;
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
		if (y > 590){
			sy = 0;
			y = 590;
		}
		if (x < 20){
			x = 20;
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
		return new Rectangle(x+5,y+8,40,55);
	}
}

    
