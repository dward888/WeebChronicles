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

    public Bullet(int gx, int gy){
        x = gx;
        y = gy;
        left = 1;
        right = 2;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public void move(int n){
        if (n == left){
            x -= 5;
        }
        else if (n == right){//yo
            x += 5;
        }

    }
}
