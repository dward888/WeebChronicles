import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.io.*;
import javax.imageio.*;
import javax.sound.midi.*;
import java.applet.*;

public class Player {
	private int x,y,bx,bx2,hp,sp;

    public Player() {
    	x = 120;
		y = 400;
		bx = 1185;//variable for scrolling background 
		bx2 = 0;
    }
    public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public int getBx(){
		return bx;
	}
	public void setBx(int n){
		bx = n;
	}
	public int getBx2(){
		return bx2;
	}
	public void setBx2(int n){
		bx2 = n;
	}
	
	public int getHP(){
		return hp;
	}
    public void moveX(int num){
    	x += num;
    	bx += num;
    	bx2 += num;
    }
    public void moveY(int num){
    	y += num;
    }
    
    
    
    
}