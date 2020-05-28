import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

public class GamePanel extends JPanel implements KeyListener{
	private boolean []keys;
	private WeebChronicles mainFrame;
	private Player p;
	private Goomba b;
	private Character[]chars;
	private boolean walking;
	private int stillRight;
	private int stillLeft;
	private int right;
	private int left;
	private int direction;
	private int bulletRight;
	private int bulletLeft;
	private int frame;
	private boolean midAir;//this boolean will make sure the user can't double jump
    private ArrayList<Rectangle>platRects = new ArrayList<Rectangle>();
    private ArrayList<Rectangle>badRects = new ArrayList<Rectangle>();
    private ArrayList<Platform>plats = new ArrayList<Platform>();

    //private ArrayList<Platform>plats1 = new ArrayList<Platform>();
    //private ArrayList<Platform>plats2 = new ArrayList<Platform>();
    //private ArrayList<Platform>plats3 = new ArrayList<Platform>();

    private ArrayList<Goomba>goombs = new ArrayList<Goomba>();

    private ArrayList<Bullet>bList = new ArrayList<Bullet>(); //array list for the projectiles (bullets)
    private ArrayList<Bullet>bRemove = new ArrayList<Bullet>(); //Records all the bullets that have hit an object
    private int f = 0;
    private int offset;
	//images//
	private Image back;
	private Image star;
	//private Image platPic;
	private Image longPlat;
	private Image airPlat;

    private int mx;
    private int my;

    public GamePanel(WeebChronicles m) {
    	keys = new boolean[KeyEvent.KEY_LAST+1];
		mainFrame = m;
        p = new Player();
        //b = new Goomba(500, 400,500,600);

        //Direction
        stillRight = 0;
        left = 1;
        right = 2;
        stillLeft = 3;

        direction = right;
        walking = false;
        offset = 0;
        frame = 0;
        bulletLeft = 4;
        bulletRight = 5;

		addKeyListener(this);

		//loading images//
		back = new ImageIcon("Pictures/back.png").getImage();
		star = new ImageIcon("Pictures/star.png").getImage();
		//platPic = new ImageIcon("Pictures/plat pic.png").getImage();
		longPlat =  new ImageIcon("platPics/longPlat.png").getImage();
		airPlat = new ImageIcon("platPics/airPlat.png").getImage();

        //addMouseListener(this);
        //initilizing the platforms as rects
        //Rectangle plat1 = new Rectangle(500,525,1000,40);
        //plats1.add(plat1);
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
        if(e.getKeyCode() == KeyEvent.VK_SPACE && !keys[e.getKeyCode()]){
            Bullet b = new Bullet(p.getX()-offset, p.getY());
            b.setDirection(direction);
            bList.add(b);
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

    public void loadPlats(String file, int lvl) throws IOException{
        Scanner inFile = new Scanner (new BufferedReader(new FileReader(file)));
        while (inFile.hasNext()){//while there are lines to be read
            String line = inFile.nextLine();
            String[]data = line.split(" ");//splitting up each value to be able to keep track of the x,y, width, height
            int x = Integer.parseInt(data[0]);
            int y = Integer.parseInt(data[1]);
            String p = data[2];
            //int w = Integer.parseInt(data[2]);
            //int h = Integer.parseInt(data[3]);
            Platform tmp = new Platform(x,y,p);
            if (lvl == 1){
                //plats1.add(tmp);
                plats.add(tmp);
                platRects.add(tmp.getRect());
            }
            if (lvl == 2){
                //plats2.add(tmp);
                plats = new ArrayList<Platform>();
                platRects = new ArrayList<Rectangle>();
                plats.add(tmp);
                platRects.add(tmp.getRect());
            }
            if (lvl == 3){
                //plats3.add(tmp);
                plats = new ArrayList<Platform>();
                platRects = new ArrayList<Rectangle>();
                plats.add(tmp);
                platRects.add(tmp.getRect());
            }
        }
    }

    public void loadGoombs(String file, int lvl) throws IOException{
        Scanner inFile = new Scanner (new BufferedReader(new FileReader(file)));
        while (inFile.hasNext()) {//while there are lines to be read
            String line = inFile.nextLine();
            String[] data = line.split(" ");//splitting up each value to be able to keep track of the x,y,max R, max L, and picture
            int x = Integer.parseInt(data[0]);
            int y = Integer.parseInt(data[1]);
            int mL = Integer.parseInt(data[2]);
            int mR = Integer.parseInt(data[3]);
            String b = data[4];

            Goomba tmp = new Goomba(x, y, mL, mR, b);
            if (lvl == 1) {
                //plats1.add(tmp);
                goombs.add(tmp);
                badRects.add(tmp.getRect());
            }
        }
    }

    public void deleteBullets(){
        for(int i= 0; i < bRemove.size(); i++){//yo
            bList.remove(bRemove.get(i));
        }
    }

    public void paintComponent(Graphics g){
        //background
        Point mousePos = getMousePosition();
        mx = (int) mousePos.getX();
        my = (int) mousePos.getY();
        g.drawImage(back, 0, 0, null);
        f++;
        //System.out.println(p.getY());
        //System.out.println(my);
        //System.out.println(mx+","+ my);



        //bullets
        /*if(bList.size() > 0) {
            for (int i = 0; i < bList.size(); i++) {
                //if(bList.get(i).getDirection() == right){
                    //g.drawImage(p.getFrame(bulletRight), bList.get(i).getX(), bList.get(i).getY(), null);
                //}
                //if(bList.get(i).getDirection() == left){
                    //g.drawImage(p.getFrame(bulletLeft), bList.get(i).getX(), bList.get(i).getY(), null);
                //}
                g.drawImage(p.getBulFrame(bList.get(i)), bList.get(i).getX(), bList.get(i).getY(),null);
                bList.get(i).move();
                //Removing when out of bounds
            }
        }*/

        //drawing the sprites for their respective direction
        if(direction == right && !midAir){
            if(!walking){//Standing Right
                g.drawImage(p.getFrame(stillRight), p.getX()-offset, p.getY(), null);
            }
            else{
                g.drawImage(p.getFrame(right), p.getX()-offset, p.getY(), null);
            }
        }
        if(direction == left && !midAir){
            if(!walking){ //Standing Left
                g.drawImage(p.getFrame(stillLeft), p.getX()-offset, p.getY(), null);

                }
            else{ //Run Left
                g.drawImage(p.getFrame(left), (p.getX())-offset, p.getY(), null);

            }
        }

        if(midAir){
            if(direction == left){
                if(p.getSy() < 0){
                    g.drawImage(p.getJumpL(), p.getX()-offset, p.getY(), null);
                    p.addCurrentF();
                }
                if(p.getSy() > 0){
                    g.drawImage(p.getJumpL(), p.getX()-offset, p.getY(), null);
                    p.addCurrentF();
                }

            }
            if(direction == right){
                if(p.getSy() < 0){
                    g.drawImage(p.getJumpR(), p.getX() - offset, p.getY(), null);
                    p.addCurrentF();
                }
                if(p.getSy() > 0){
                    g.drawImage(p.getJumpR(), p.getX() - offset, p.getY(), null);
                    p.addCurrentF();
                }
            }
        }

        frame++;

        //drawing the rects
        g.setColor(Color.blue);
        g.drawRect(p.getX()+5-offset, p.getY()+8,40,55);
        //g.drawRect(b.getX()-offset, b.getY()+8, 20, 20);
        for (Platform p : plats){
            g.drawImage(p.getImage(),p.getX() - offset,p.getY(),null);
        }
        for (Goomba b : goombs){
            g.drawImage(b.getImage(), b.getX()-offset, b.getY(),null);
            g.drawRect(b.getX()-offset,b.getY(),32,32);
        }



        //g.drawImage(platPic,500-offset,500,null);+

        //g.fillRect(500 - offset, 510, 1000, 40);
        //g.drawRect(500, 500, 1920,40);
    }

    //user moving the character f
    public void move(){
        if (keys[KeyEvent.VK_RIGHT]){
            if (p.getX() >= 600 + offset){
                offset += p.SPEED;
            }
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
            //direction = p.update(jumpUp); yo
        }
    }

    public void badMove(){
        for (Goomba b : goombs){
            if (b.getX() == b.getMaxL()){
                b.setDirection(right);
            }
            if (b.getX() == b.getMaxR() + 80){
                b.setDirection(left);
            }
            if (b.getDirection() == right){
                b.moveR();
            }
            if (b.getDirection() == left){
                b.moveL();
            }

        }

    }


    public void playerUpdate(){
        p.update2();
        if(p.getSy() == 0){
            midAir = false;
            p.resetCurrentF();
        }
    }

    public void badUpdate(){
        for (Goomba b : goombs){
            b.update();
        }
        //b.update();

    }

    public void checkCollisions(){
        //Rectangle player = p.getRect();//the players rect to check collision

        //Rectangle test = new Rectangle(500,500,1000,40);
        /*if (player.intersects(test)){
            System.out.println("hi");
            p.setSy(0);
            p.setY(440);
            midAir = false;
            p.resetCurrentF();
        }*/
        for(Platform plat : plats){//checking collision for each platform in the arraylist
            if (plat.getRect().intersects(p.getRect())){
                p.setSy(0);//because the player is on a platform, the speed in the y component is zero
                p.setY(plat.getRect().y-60);
                midAir = false;
            }
            for (Goomba bad : goombs){
                if (plat.getRect().intersects(bad.getRect())){
                    bad.setSy(0);
                    bad.setY(plat.getRect().y-30);
                }
            }
        }
    }
}