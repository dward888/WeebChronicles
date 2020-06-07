//Edward Yang and Jim Ji Final Project

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class WeebChronicles extends JFrame implements ActionListener {
	Timer myTimer;
	int status;
	int startScreen;
	int gPanel;
	int cutScene1;

	startScreen start;
	GamePanel game;
	CutScene1 cScene1;

	JPanel cards;
	CardLayout cLayout = new CardLayout();

	public WeebChronicles() {
		super("The Weeb Chronicles");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1200,650);
		myTimer = new Timer(15, this);	 // trigger every 10 ms
		setResizable(false);
		game = new GamePanel(this);
		start = new startScreen(this);
		cScene1 = new CutScene1(this);
		cards = new JPanel(cLayout);

		//***Following code --> change order so that the start screen appears
		//cards.add(start, "start");
		cards.add(game, "game");
		cards.add(start, "start");
		cards.add(cScene1, "cutScene1");
		//cards.add(game, "game");

		add(cards);

		startScreen = -1;
		gPanel = 1;
		cutScene1 = 2;
		//status = startScreen;
		status = gPanel;
		//status = cutScene1;

		try {
			game.loadPlats("plat1.txt",1);
			game.loadDecor("decor1.txt",1);
			game.loadGoombs("goomba1.txt",1);
			game.loadCoins("coin1.txt",1);
			game.loadPLives();
			game.loadLvlLives("life1.txt",1);
		}catch (IOException e) {
			e.printStackTrace();
		}

		/*try {
			game.loadGoombs("goomba1.txt",1);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			game.loadDecor("decor1.txt",1);
		} catch (IOException e) {
			e.printStackTrace();
		}*/

		setVisible(true);
	}
	public void start(){
		myTimer.start();
	}
	public void actionPerformed(ActionEvent evt) {
		//Following code moves the player to the next stage of the game
		if(start.getLoaded()){
			cLayout.show(cards,"cutScene1");
			status = cutScene1;
		}
		if(cScene1.finished()){
			cLayout.show(cards,"game");
			status = gPanel;
		}

		//Following code draws the elements of each stage
		if(status == startScreen){
			start.requestFocus();
			start.repaint();
		}
		if(status == cutScene1){
			cScene1.requestFocus();
			cScene1.repaint();
		}
		else if(status == gPanel){
			game.requestFocus();
			game.move();
			game.playerUpdate();//applying physics
			game.badMove();

            game.badUpdate();
			game.checkCollisions();
			game.repaint();
			game.removeCoins();
			game.checkRun();
			game.removeGoombs();
			game.removeDGoombs();
			game.removeLvlLives();
			game.removePLives();
			//game.checkLife();
		}
	}

	public static void main(String[] arguments) throws IOException, FontFormatException {
		WeebChronicles frame = new WeebChronicles(); //starting the entire game
    }
}