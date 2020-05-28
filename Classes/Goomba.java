import javax.swing.*;
import java.awt.*;

public class Goomba {
    private int x,y,dist;
    private double sy,sx;
    private Image pic;
    private int maxR, maxL;

    private int direction;
    private int left = 1;
    private int right = 2;

    private Rectangle rect;

    public static final double FRICTION = 0.99;
    public static final double GRAVITY = 0.4;
    public static final double SPEED = 2;

    public Goomba(int x1, int y1, int maxLeft, int maxRight, String f){
        x = x1;
        y = y1;
        maxL = maxLeft;
        maxR = maxRight;
        dist = 0;
        direction = right;
        pic =  new ImageIcon("badPics/" + f + ".png").getImage();
        rect = new Rectangle(x1,y1,pic.getWidth(null),pic.getHeight(null));//pic.getWidth(null), pic.getHeight(null));
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
    public int getDirection(){
        return direction;
    }
    public void setDirection(int n){
        direction = n;
    }
    public int getMaxR(){
        return maxR;
    }
    public int getMaxL(){
        return maxL;
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
        if (y > 590){
            sy = 0;
            y = 590;
        }
    }
    public void moveR(){
        move(SPEED, 0);
    }
    public void moveL(){
        move(-SPEED, 0);
    }
    public Rectangle getRect(){
        return rect;
    }
    public Image getImage(){
        return pic;
    }
}
