import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.io.*;
import javax.imageio.*;
import javax.sound.midi.*;
import java.applet.*;

public class GamePanel extends JPanel implements KeyListener{
	private boolean []keys;
	private WeebChronicles mainFrame;
	private Player p;
	private Character[]chars;
	private String direction;
	private int frame;
	//images//
	private Image back;
	//private Image player;

    public GamePanel(WeebChronicles m) {
    	keys = new boolean[KeyEvent.KEY_LAST+1];
		mainFrame = m;
		chars = new Character[10];
		loadCharacters();
        p = new Player(chars[0]);
        direction = "still";
        frame = 0;
        scrollX = 850;


		addKeyListener(this);

		//loading images//
		back = new ImageIcon("Pictures/back.jpg").getImage();

    }
    
    public void addNotify() {
        super.addNotify();
        requestFocus();
        mainFrame.start();
    }

    public void keyTyped(KeyEvent e){
    }
    public void keyPressed(KeyEvent e){
        keys[e.getKeyCode()] = true;
    }
    public void keyReleased(KeyEvent e){
        keys[e.getKeyCode()] = false;
        direction = "still";
    }

    public void loadCharacters(){
        Character itachi = new Character("Itachi", 100, 100, 100 );
        chars[0] = itachi;
    }

    public void paintComponent(Graphics g){


        //attempt at infinite scrolling background (w.i.p.)
        if ((p.getX() - 850) %  3840 == 0){
            p.setBx(0);
        }
        if ((p.getX() - 2770) % 3840 == 0){
            p.setBx2(0);
        }
		g.drawImage(back,1185-p.getBx2(),0,null);
        if (p.getX() >= 850){
            g.drawImage(back,1185-p.getBx(),0,null);
        }
    	//g.drawImage(p.getImage(),70p.getX()*p.getY(),null);*/




        //the code below allows the player to side scroll past a certain point and only past that point
        if (p.getX() >= 850) {
            if (direction.equals("right")) {
                //scrollX2 = p.getX();
                p.setScroll(true);
                g.drawImage(back, 1185 - p.getBx2(), 0, null);
                //g.drawImage(p.getImage(), 850, p.getY(), null);
                System.out.println(p.getX());
            }
            else if(direction.equals("left") || direction.equals("still")){
                p.setScroll(false);
                g.drawImage(back, 1185 - p.getBx2(), 0, null);
                //g.drawImage(p.getImage(), p.getX(), p.getY(), null);
                System.out.println(p.getX());

            }
        }
        //if (p.getX() <= 850){
            //p.setScroll(false);

            //g.drawImage(p.getImage(),p.getX(),p.getY(),null);

        //}
        else if (p.getX() < 850){
            p.setScroll(false);
            g.drawImage(back, 0, 0,null);
            //g.drawImage(p.getImage(),p.getX(),p.getY(),null);
        }

        //drawing the sprites for their respective direction
        if(direction.equals("still")){
            g.drawImage(p.getFrame("standing"), p.getX(), p.getY(), null);
        }
        if(direction.equals("right")){
            if (p.checkScroll()){
                g.drawImage(p.getFrame("run right"), 850, p.getY(), null);
            }
            else if (!p.checkScroll()){
                g.drawImage(p.getFrame("run right"), p.getX()-10, p.getY(), null);
            }
        }
        if(direction.equals("left")){
            g.drawImage(p.getFrame("run left"), p.getX()-10, p.getY(), null);
        }

        frame ++;
    }

    //user moving the character
    public void move(){
        if (keys[KeyEvent.VK_RIGHT]) {
            p.setDx(10);
            p.moveX();
            direction = "right";
        }
        if (keys[KeyEvent.VK_LEFT]) {
            p.setDx(-10);
            p.moveX();
            direction = "left";
        }
    }
    
}