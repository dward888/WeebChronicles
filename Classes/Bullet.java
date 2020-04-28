import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.io.*;
import javax.imageio.*;
import javax.sound.midi.*;
import java.applet.*;

public class Bullet {
    private int x,y;
    private int right;
    private int left;
    private int direction;
    private int frame;

    public Bullet(int gx, int gy){
        x = gx;
        y = gy;
        left = 1;
        right = 2;
        frame = 0;
    }
    public void setDirection(int n){direction = n;}
    public int getDirection(){return direction;}
    public int getFrame(){return frame;}
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
}
