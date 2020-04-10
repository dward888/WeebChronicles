import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.io.*;
import javax.imageio.*;
import javax.sound.midi.*;
import java.applet.*;

//yo boobs vagi

public class WeebChronicles extends JFrame implements ActionListener {
	Timer myTimer;
	GamePanel game;
	Player p;
    public WeebChronicles(){
    	super("The Weeb Chronicles");
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1200,650);
		myTimer = new Timer(10, this);	 // trigger every 10 ms
		game = new GamePanel(this);
		p = new Player();
		add(game);
		setResizable(false);
		setVisible(true);
    }
    public void start(){
		myTimer.start();
	}
	
	public void actionPerformed(ActionEvent evt){
		Object source = evt.getSource();
		/*if(source==playBtn){ //if the play button is clicked, the game starts
		    cLayout.show(cards,"game");//switch from the start menu card to the game card
		    myTimer.start();
		    game.requestFocus();
		}*/
		
		if(source==myTimer){//if the game is running
			myTimer.start();
		    game.requestFocus();
		    game.move();
		    game.repaint();
		}
	}
	public static void main(String[] arguments) {
		WeebChronicles frame = new WeebChronicles(); //starting the entire game
    }
    
    
	
}