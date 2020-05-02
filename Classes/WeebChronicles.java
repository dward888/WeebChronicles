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
	selectScreen select;

	JPanel cards;
	CardLayout cLayout = new CardLayout();

	JButton playBtn = new JButton("Play");
	JButton confirmBtn = new JButton("Confirm");
    public WeebChronicles(){
    	super("The Weeb Chronicles");
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1200,650);
		myTimer = new Timer(15, this);	 // trigger every 10 ms
		game = new GamePanel(this);
		select = new selectScreen(this);
		//add(game);
		add(select);
		playBtn.addActionListener(this);
		confirmBtn.addActionListener(this);



		ImageIcon startBack = new ImageIcon("Pictures/title.png");
		//ImageIcon selectBack = new ImageIcon()

		JLabel startBackLabel = new JLabel(startBack);
		//JLabel selectBackLabel = new JLabel(selectBack);

		JLayeredPane start = new JLayeredPane();
		setResizable(false);
		setVisible(true);
		//JPanel start = new JPanel();


		//JLayered Panes
		//JLayeredPane start = new JLayeredPane();

		start.setLayout(null);
		startBackLabel.setSize(1200,650);
		startBackLabel.setLocation(0,0);
		//adding cards
		start.add(startBackLabel,1);
		cards = new JPanel(cLayout);
		cards.add(start, "start");
		cards.add(game, "game");
		cards.add(select, "select");
		add(cards);

		//play button
		playBtn.addActionListener(this);
		playBtn.setSize(205,60);
		playBtn.setLocation(500,450);
		start.add(playBtn,2);

		confirmBtn.addActionListener(this);

		playBtn.setSize(205,60);
		playBtn.setLocation(500,450);
		select.add(confirmBtn,0);
		confirmBtn.setLocation(600,300);
//yo

    }//yop
    public void start(){
		myTimer.start();
	}
	
	public void actionPerformed(ActionEvent evt){
    	/*if (select.checkSelect()){
    		add(game);
		}*/
		Object source = evt.getSource();
		if(source==playBtn){ //if the play button is clicked, the game starts
		    cLayout.show(cards,"select");//switch from the start menu card to the game card
			select.requestFocus();

		}
		if (source == confirmBtn){
			cLayout.show(cards,"game");
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