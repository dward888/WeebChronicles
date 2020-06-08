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
	int gPanel2;
	int cutScene1;
	int level;

	startScreen start;
	GamePanel game;
	GamePanel2 game2;
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
		game2 = new GamePanel2(this);
		start = new startScreen(this);
		cScene1 = new CutScene1(this);
		cards = new JPanel(cLayout);
		level = game.getLevel();
		//***Following code --> change order so that the start screen appears
		//cards.add(start, "start");

		//cards.add(game, "game");

		cards.add(game, "game");
		cards.add(game2,"game2");
		//cards.add(game2,"game2");
		//cards.add(start, "start");
		cards.add(cScene1, "cutScene1");
		//cards.add(game, "game");

		add(cards);

		startScreen = -1;
		gPanel = 1;
		cutScene1 = 2;

		//status = startScreen;
		status = gPanel;

		//status = startScreen;
		//status = gPanel2;

		//status = cutScene1;

		try {
			game.loadPLives();
			game.loadPlats();
			game.loadDecor();
			game.loadGoombs();
			game.loadCoins();
			game.loadLvlLives();
			game2.loadPLives();
			game2.loadPlats();
			game2.loadDecor();
			game2.loadGoombs();
			game2.loadCoins();
			game2.loadLvlLives();
			//game2.loadShooters();

			//game.loadPlats("plat2.txt",2);
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
		if (game.checkFinish()){
			cLayout.show(cards, "game2");
			status = gPanel2;
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

		if(status == gPanel){
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
			game.removeBullets();
			game.removePLives();
		}
		else if (status == gPanel2){
			game2.requestFocus();
			game2.move();
			game2.playerUpdate();//applying physics
			game2.badMove();

			game2.badUpdate();
			game2.checkCollisions();
			game2.repaint();
			game2.removeCoins();
			game2.checkRun();
			game2.removeGoombs();
			game2.removeDGoombs();
			game2.removeLvlLives();
			game2.addBBullets();
			game2.moveBullets();
			game2.removeBullets();
			game2.removePLives();
		}

	}

	public static void main(String[] arguments) throws IOException, FontFormatException {
		WeebChronicles frame = new WeebChronicles(); //starting the entire game
    }
}