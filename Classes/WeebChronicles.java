//Edward Yang and Jim Ji Final Project

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class WeebChronicles extends JFrame implements ActionListener {
	Timer myTimer;
	int status;
	int startScreen;
	int selectScreen;
	int level1;
	int level2;
	int level3;
	int level4;

	startScreen start;
	selectScreen select;
	GamePanel game;

	JPanel cards;
	CardLayout cLayout = new CardLayout();

	public WeebChronicles() throws IOException, FontFormatException {
		super("The Weeb Chronicles");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1200,650);
		myTimer = new Timer(15, this);	 // trigger every 10 ms
		setResizable(false);
		game = new GamePanel(this);
		select = new selectScreen(this);
		start = new startScreen(this);
		cards = new JPanel(cLayout);
		cards.add(start, "start");
		cards.add(select, "select");
		cards.add(game, "game");

		add(cards);

		startScreen = -1;
		selectScreen = 0;
		level1 = 1;
		level2 = 2;
		level3 = 3;
		level4 = 4;
		status = startScreen;

		setVisible(true);
	}
	public void start(){
		myTimer.start();
	}
	public void actionPerformed(ActionEvent evt) {
		//Following code moves the player to the next stage of the game
		if(start.getLoaded()){
			cLayout.show(cards,"select");
			status = selectScreen;
		}
		if(select.checkSelect()){
			cLayout.show(cards,"game");
			status = level1;
		}
		//Following code draws the elements of each stage
		if(status == startScreen){
			start.requestFocus();
			start.repaint();
		}
		else if(status == selectScreen){
			select.requestFocus();
			select.repaint();
		}
		else if(status == level1){
			game.requestFocus();
			game.move();
			game.playerUpdate();//applying physics
			game.checkCollisions();
			game.repaint();
		}
	}
	public static void main(String[] arguments) throws IOException, FontFormatException {
		WeebChronicles frame = new WeebChronicles(); //starting the entire game
    }
}