import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.io.*;
import javax.imageio.*;
import javax.sound.midi.*;
import java.applet.*;

public class Bullet {
    private int x,y;

    public Bullet(int gx, int gy){
        x = gx;
        y = gy;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public void move(int n){
        if (n == 1){//if the player is facing left
            x -= 5;
        }
        else if (n == 2){//if the player is facing right
            x += 5;
        }

    }
}
