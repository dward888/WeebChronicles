import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Bullet {
    private int x,y;
    private int right;
    private int left;
    private int direction;
    private int frame;
    private String type;
    private Image[]rightF;
    private Image[]leftF;
    private Image pic;
    private int f;
    private Rectangle rect;
    private boolean hit;

    public Bullet(int gx, int gy, String t){
        x = gx;
        y = gy;
        left = 2;
        right = 1;
        frame = 0;
        type = t;
        pic =  new ImageIcon("Pictures/"+t+".png").getImage();
        rect = new Rectangle(gx,gy,pic.getWidth(null), pic.getHeight(null));
        hit = false;

    }

    public void setDirection(int n){direction = n;}
    public int getDirection(){return direction;}
    //public int getFrame(){return frame;}
    public void nextFrame(){frame++;}
    public void resetF(){frame = 0;}
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public void move(){
        if (direction == left){
            x -= 3;
        }
        else if (direction == right){
            x += 3;
        }
    }
    public Rectangle getRect(){
        return new Rectangle(x,y,30,30);//return rect;
    }
    public boolean checkHit(){
        return hit;
    }
    public void setHit(boolean n){
        hit = n;
    }

}
