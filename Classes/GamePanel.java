import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

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
    private ArrayList<Rectangle>plats = new ArrayList<Rectangle>();
    private ArrayList<Bullet>bList = new ArrayList<Bullet>(); //array list for the projectiles (bullets)
    private ArrayList<Bullet>bRemove = new ArrayList<Bullet>(); //Records all the bullets that have hit an object
    private int f = 0;
    private int offset;
	//images//
	private Image back;
	private Image star;
	private Image platPic;

    public GamePanel(WeebChronicles m) {
    	keys = new boolean[KeyEvent.KEY_LAST+1];
		mainFrame = m;
		chars = new Character[10];
		loadCharacters();
        p = new Player(chars[0]);
        b = new Goomba(500, 400,500,600);

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
		back = new ImageIcon("Pictures/back.jpg").getImage();
		star = new ImageIcon("Pictures/star.png").getImage();
		platPic = new ImageIcon("Pictures/plat pic.png").getImage();

        //initilizing the platforms as rects
        Rectangle plat1 = new Rectangle(500,525,1000,40);
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

    public void loadCharacters(){
        Character itachi = new Character("Itachi", 100, 100, 100, 6 , 6, 5);
        chars[0] = itachi;
    }

    public void deleteBullets(){
        for(int i= 0; i < bRemove.size(); i++){//yo
            bList.remove(bRemove.get(i));
        }
    }

    public void paintComponent(Graphics g){
        //background
        g.drawImage(back, 0, 0, null);
        f++;
        System.out.println(f);


        //bullets
        if(bList.size() > 0) {
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
        }
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
                //Going Up
                if(p.getSy() < 0) {
                    if (p.getCurrentF() > 5) {
                        g.drawImage(p.getJumpL()[0], p.getX()-offset, p.getY(), null);
                    }
                    else {
                        g.drawImage(p.getJumpL()[1], p.getX()-offset, p.getY(), null);
                    }
                    p.addCurrentF();
                }
                //Going Down
                if(p.getSy() > 0){
                    g.drawImage(p.getFallL()[0], p.getX()-offset, p.getY(), null);
                }
            }
            if(direction == right){
                //Going Up
                if(p.getSy() < 0){
                    if (p.getCurrentF() > 5) {
                        g.drawImage(p.getJumpR()[0], p.getX() - offset, p.getY(), null);
                    }
                    else {
                        g.drawImage(p.getJumpR()[1], p.getX() - offset, p.getY(), null);
                    }
                    p.addCurrentF();
                }
                //Going Down
                if(p.getSy() > 0){
                    g.drawImage(p.getFallR()[0], p.getX()-offset, p.getY(), null);
                }
            }
        }

        frame++;

        //drawing the rects
        g.setColor(Color.blue);
        g.drawRect(b.getX(), b.getY()+8, 20, 20);
        g.drawImage(platPic,500-offset,500,null);

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
        if (b.getX() == b.getMaxL()){
            b.setDirection(right);
        }
        if (b.getX() == b.getMaxR() + 20){
            b.setDirection(left);
        }
        if (b.getDirection() == right){
            b.moveR();
        }
        if (b.getDirection() == left){
            b.moveL();
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
        b.update();

    }

    public void checkCollisions(){
        Rectangle player = p.getRect();//the players rect to check collision
        //Rectangle test = new Rectangle(500,500,1000,40);
        /*if (player.intersects(test)){
            System.out.println("hi");
            p.setSy(0);
            p.setY(440);
            midAir = false;
            p.resetCurrentF();
        }*/
        for(Rectangle plat : plats){//checking collision for each platform in the arraylist
            if (player.intersects(plat)){
                p.setSy(0);//because the player is on a platform, the speed in the y component is zero
                p.setY(plat.y-60);
                midAir = false;
            }
        }
    }
}