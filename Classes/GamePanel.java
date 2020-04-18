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
	private boolean walking;
	private int stillRight;
	private int stillLeft;
	private int right;
	private int left;
	private int direction;
	private int frame;
	private boolean midAir;//this boolean will make sure the user can't double jump
    private ArrayList<Rectangle>plats = new ArrayList<Rectangle>();


	//images//
	private Image back;


    public GamePanel(WeebChronicles m) {
    	keys = new boolean[KeyEvent.KEY_LAST+1];
		mainFrame = m;
		chars = new Character[10];
		loadCharacters();
        p = new Player(chars[0]);

        //Direction
        stillRight = 0;
        left = 1;
        right = 2;
        stillLeft = 3;
        direction = right;
        walking = false;

        frame = 0;

		addKeyListener(this);

		//loading images//
		back = new ImageIcon("Pictures/back.jpg").getImage();

        Rectangle pRect = new Rectangle(p.getX(), p.getY()+8, 40, 60);
        Rectangle plat1 = new Rectangle(500,500,1000,40);
        plats.add(plat1);

    }
    
    public void addNotify() {
        super.addNotify();
        requestFocus();
        mainFrame.start();
    }

    public void keyTyped(KeyEvent e){
    }
    public void keyPressed(KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_UP && !keys[e.getKeyCode()]){
           if(!midAir){
               p.jump();
               midAir = true;
           }
        }
        keys[e.getKeyCode()] = true;
    }
    public void keyReleased(KeyEvent e){
        keys[e.getKeyCode()] = false;
        if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT){
            walking = false;
        }

        if(!keys[e.getKeyCode()] && !midAir){
            p.resetCurrentF();
        }
    }

    public void loadCharacters(){
        Character itachi = new Character("Itachi", 100, 100, 100, 6 , 6);
        chars[0] = itachi;
    }

    public void paintComponent(Graphics g){
        //background
        g.drawImage(back, 0, 0, null);

        //drawing the sprites for their respective direction
        if(direction == right && !midAir){
            if(!walking){//Standing Right
                g.drawImage(p.getFrame(stillRight), p.getX(), p.getY(), null);
            }
            else{ //Run Right
                g.drawImage(p.getFrame(right), p.getX()-10, p.getY(), null);
            }
        }
        if(direction == left && !midAir){
            if(!walking){ //Standing Left
                g.drawImage(p.getFrame(stillLeft), p.getX(), p.getY(), null);
            }
            else{ //Run Left
                g.drawImage(p.getFrame(left), p.getX()-10, p.getY(), null);
            }
        }

        if(midAir){
            if(direction == left){
                //Going Up
                if(p.getSy() < 0) {
                    if (p.getCurrentF() > 5) {
                        g.drawImage(p.getJumpL()[0], p.getX() - 10, p.getY(), null);
                    }
                    else {
                        g.drawImage(p.getJumpL()[1], p.getX() - 10, p.getY(), null);
                    }
                    p.addCurrentF();
                }
                //Going Down
                if(p.getSy() > 0){
                    g.drawImage(p.getFallL()[0], p.getX()-10, p.getY(), null);
                }
            }
            if(direction == right){
                //Going Up
                if(p.getSy() < 0){
                    if (p.getCurrentF() > 5) {
                        g.drawImage(p.getJumpR()[0], p.getX() - 10, p.getY(), null);
                    }
                    else {
                        g.drawImage(p.getJumpR()[1], p.getX() - 10, p.getY(), null);
                    }
                    p.addCurrentF();
                }
                //Going Down
                if(p.getSy() > 0){
                    g.drawImage(p.getFallR()[0], p.getX()-10, p.getY(), null);
                }
            }

        }
        frame ++;
        g.setColor(Color.blue);
        g.drawRect(p.getX(), p.getY()+8, 40, 60);
        g.fillRect(500,500,1000,40);
        //g.drawRect(500, 500, 1920,40);

    }

    //user moving the character f
    public void move(){
        if (keys[KeyEvent.VK_RIGHT]) {
            p.update(right);
            p.runR();
            direction = right;
            walking = true;
        }
        if (keys[KeyEvent.VK_LEFT]) {
            p.update(left);
            p.runL();
            direction = left;
            walking = true;
        }
        if(keys[KeyEvent.VK_UP]){
            //p.jump();
            //direction = jumpUp;
            //direction = p.update(jumpUp);
        }
    }

    public void playerUpdate(){
        p.update2();
        if(p.getSy() == 0){
            midAir = false;
            p.resetCurrentF();
        }
    }
    public void checkCollisions(){
        Rectangle player = new Rectangle(p.getX(),p.getY()+8,40,60);
        //Rectangle test = new Rectangle(500,500,1000,40);
        /*if (player.intersects(test)){
            System.out.println("hi");
            p.setSy(0);
            p.setY(440);
            midAir = false;
            p.resetCurrentF();
        }*/
        for(Rectangle plat : plats){
            if (player.intersects(plat)){
                p.setSy(0);
                p.setY(plat.y-60);
                midAir = false;
                p.resetCurrentF();
            }
        }
//yoo
    }
    /*public void playerCheckC(){
        p.checkCollisions();
    }*/

}