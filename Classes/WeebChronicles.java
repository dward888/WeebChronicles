//Edward Yang and Jim Ji Final Project

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.io.*;
import javax.imageio.*;
import javax.sound.midi.*;
import java.applet.*;
//yo

public class WeebChronicles extends JFrame implements ActionListener {
	Timer myTimer;
	GamePanel game;

	JPanel cards;
	CardLayout cLayout = new CardLayout();

	JButton playBtn = new JButton("Play");
    public WeebChronicles(){
    	super("The Weeb Chronicles");
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1200,650);
		myTimer = new Timer(15, this);	 // trigger every 10 ms
		game = new GamePanel(this);
		add(game);
		playBtn.addActionListener(this);


		ImageIcon startBack = new ImageIcon("Pictures/leaf.jpg");
		JLabel backLabel = new JLabel(startBack);
		JLayeredPane start = new JLayeredPane();
		setResizable(false);
		setVisible(true);
		//JPanel start = new JPanel();


		//JLayered Panes
		//JLayeredPane start = new JLayeredPane();

		start.setLayout(null);
		backLabel.setSize(800,600);
		backLabel.setLocation(0,0);
		//adding cards
		start.add(backLabel,1);
		cards = new JPanel(cLayout);
		cards.add(start, "start");
		cards.add(game, "game");
		add(cards);

		//play button
		playBtn.addActionListener(this);
		playBtn.setSize(100,30);
		playBtn.setLocation(350,450);
		start.add(playBtn,2);


    }//yop
    public void start(){
		myTimer.start();
	}
	
	public void actionPerformed(ActionEvent evt){
		Object source = evt.getSource();
		if(source==playBtn){ //if the play button is clicked, the game starts
		    cLayout.show(cards,"game");//switch from the start menu card to the game card
		    myTimer.start();
		    game.requestFocus();
		}
		
		if(source==myTimer){//if the game is running
			//myTimer.start();
		    game.requestFocus();
		    game.move();
			game.playerUpdate();//applying physics
			game.checkCollisions();


		    game.repaint();
		}
	}
	public static void main(String[] arguments) {
		WeebChronicles frame = new WeebChronicles(); //starting the entire game
    }
    
    
	
}