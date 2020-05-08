import java.awt.*;

public class badGuy {
    private int x,y,dist;
    private double sy,sx;
    private Image badGuy;

    public static final double FRICTION = 0.99;
    public static final double GRAVITY = 0.4;
    public static final double SPEED = 5;

    public badGuy(){
        x = 120;
        y = 400;
        dist = 0;

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
    public int getDist(){
        return dist;
    }
    public void setDist(int n){
        dist = n;
    }
    public void setSx(double n){
        sx = n;
    }
    public void setSy(double n){
        sy = n;
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
    public void update(){
        move(sx,sy);
        accelerate(0, GRAVITY);
        /*if (y > 500){
            sy = 0;
            y = 500;
        }*/
    }
    public void runR(){
        move(SPEED, 0);
    }
    public void runL(){
        move(-SPEED, 0);
    }
    public Rectangle getRect(){
        return new Rectangle(x,y+8,20,20);
    }
}
