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
	int gPanel;

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
		start = new startScreen(this);
		cards = new JPanel(cLayout);


		//***Following code --> change order so that the start screen appears
		//cards.add(start, "start");
		//cards.add(game, "game");
		cards.add(game, "game");
		cards.add(start, "start");


		add(cards);

		startScreen = -1;
		//selectScreen = 0;
		gPanel = 1;
		//status = startScreen;
		status = gPanel;

		try {
			game.loadPlats("plat1.txt",1);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			game.loadGoombs("goomba1.txt",1);
		} catch (IOException e) {
			e.printStackTrace();
		}

		setVisible(true);
	}
	public void start(){
		myTimer.start();
	}
	public void actionPerformed(ActionEvent evt) {

		//Following code moves the player to the next stage of the game
		if(start.getLoaded()){
			cLayout.show(cards,"game");
			status = gPanel;
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
		else if(status == gPanel){
			game.requestFocus();
			game.move();
			game.playerUpdate();//applying physics
			game.badMove();

            game.badUpdate();
			game.checkCollisions();
			game.repaint();
		}
	}
	public static void main(String[] arguments) throws IOException, FontFormatException {
		WeebChronicles frame = new WeebChronicles(); //starting the entire game
    }
}