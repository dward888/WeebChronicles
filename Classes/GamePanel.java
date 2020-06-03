import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;
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
	private int currentF;
	private int footCount;
	private boolean midAir;//this boolean will make sure the user can't double jump
    private boolean onPlat;
    private boolean falling;
    private boolean playRun;
    //private ArrayList<Rectangle>platRects = new ArrayList<Rectangle>();
    //private ArrayList<Rectangle>badRects = new ArrayList<Rectangle>();
    private ArrayList<Platform>plats = new ArrayList<Platform>();

    //private ArrayList<Platform>plats1 = new ArrayList<Platform>();
    //private ArrayList<Platform>plats2 = new ArrayList<Platform>();
    //private ArrayList<Platform>plats3 = new ArrayList<Platform>();
    //yo
    private ArrayList<Goomba>goombs = new ArrayList<Goomba>();
    private ArrayList<Decor>decor = new ArrayList<Decor>();

    private ArrayList<Coin>coins = new ArrayList<Coin>();
    private ArrayList<Coin>cRemove = new ArrayList<Coin>(); //Records all the coins that the player has collected
    //private ArrayList<Coin>cCurrent = new ArrayList<Coin>();

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

    //Frame Arrays
	private Image[]runRight;
	private Image[]runLeft;
	private Image[]idleRight;
	private Image[]idleLeft;
	private Image[]kickRight;
	private Image[]kickLeft;
	private Image[]punchRight;
	private Image[]punchLeft;
	private Image[]uppercutRight;
	private Image[]uppercutLeft;
	private Image[]airPunchRight;
	private Image[]airPunchLeft;
    private Image[][]attackPickRight;
    private Image[][]attackPickLeft;
    private Image[]att;
    private boolean attack;


	private Image jumpRight;
	private Image jumpLeft;

    private Sound coinSound;
    private Sound run;
    private Sound runLeaf;

    Font fontLocal=null;

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
        onPlat = false;
        falling = true;

        playRun = false;

        offset = 0;
        currentF = 0;
        footCount = 0;
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

        //Frame Arrays
        runRight  = new Image[10];
        runLeft = new Image[10];
        idleRight = new Image[8];
        idleLeft = new Image[8];
        kickRight = new Image[7];
        kickLeft = new Image[7];
        punchRight = new Image[7];
        punchLeft = new Image[7];
        uppercutRight = new Image[7];
        uppercutLeft = new Image[7];
        airPunchRight = new Image[6];
        airPunchLeft = new Image[6];

        attack = false;
        loadSprite();

        try {
            fontLocal = Font.createFont(Font.TRUETYPE_FONT, new File("font/naruto1.ttf"));
            fontLocal = fontLocal.deriveFont(30f);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        try {
            coinSound = new Sound("coin.wav",false);
            run = new Sound("run.wav",false);
            runLeaf = new Sound("runLeaf.wav",false);
        }
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e){
            e.printStackTrace();
        }
    }
    public void addNotify() {
        super.addNotify();
        requestFocus();
        mainFrame.start();
    }

    public void keyTyped(KeyEvent e){
    }
    public void keyPressed(KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_W && !keys[e.getKeyCode()]){
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
        if(e.getKeyCode() == KeyEvent.VK_ENTER && !keys[e.getKeyCode()]){
            attack = true;
            currentF = 0;
            if(!midAir){
                if(direction == right){
                    att = attackPickRight[randint(0,2)];
                }
                if(direction == left){
                    att = attackPickLeft[randint(0,2)];
                }
            }
        }
        keys[e.getKeyCode()] = true;
    }
    public void keyReleased(KeyEvent e){
        keys[e.getKeyCode()] = false;
        if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_A){
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
            //int w = Integer.parseInt(data[2]);
            //int h = Integer.parseInt(data[3]);

            String p = data[2];
            int a = Integer.parseInt(data[3]);
            //int w = Integer.parseInt(data[2]);
            //int h = Integer.parseInt(data[3]);
            Platform tmp = new Platform(x,y,p,a);
            if (lvl == 1){
                //plats1.add(tmp);
                plats.add(tmp);
                //platRects.add(tmp.getRect());
            }
            if (lvl == 2){
                //plats2.add(tmp);
                plats = new ArrayList<Platform>();
                //platRects = new ArrayList<Rectangle>();
                plats.add(tmp);
                //platRects.add(tmp.getRect());
            }
            if (lvl == 3){
                //plats3.add(tmp);
                plats = new ArrayList<Platform>();
                //platRects = new ArrayList<Rectangle>();
                plats.add(tmp);
                //platRects.add(tmp.getRect());
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
            int a = Integer.parseInt(data[5]);
            int num = Integer.parseInt(data[6]);

            Goomba tmp = new Goomba(x, y, mL, mR, b, a, num);
            if (lvl == 1) {
                //plats1.add(tmp);
                goombs.add(tmp);
                //badRects.add(tmp.getRect());
            }
            if (lvl == 2){
                goombs = new ArrayList<Goomba>();
                goombs.add(tmp);
            }
            if (lvl == 3){
                goombs = new ArrayList<Goomba>();
                goombs.add(tmp);
            }
        }
    }

    public void loadDecor(String file,int lvl) throws IOException {
        Scanner inFile = new Scanner(new BufferedReader(new FileReader(file)));
        while (inFile.hasNext()) {//while there are lines to be read
            String line = inFile.nextLine();
            String[] data = line.split(" ");//splitting up each value to be able to keep track of the x,y,max R, max L, and picture
            int x = Integer.parseInt(data[0]);
            int y = Integer.parseInt(data[1]);
            String b = data[2];


            Decor tmp = new Decor(x,y,b);

            if (lvl == 1){
                decor.add(tmp);
            }
            if (lvl == 2){
                decor = new ArrayList<Decor>();
                decor.add(tmp);
            }
            if (lvl == 3){
                decor = new ArrayList<Decor>();
                decor.add(tmp);
            }
        }
    }

    public void loadCoins(String file, int lvl)throws IOException{
        Scanner inFile = new Scanner(new BufferedReader(new FileReader(file)));
        while (inFile.hasNext()) {//while there are lines to be read
            String line = inFile.nextLine();
            String[] data = line.split(" ");//splitting up each value to be able to keep track of the x,y,max R, max L, and picture
            int x = Integer.parseInt(data[0]);
            int y = Integer.parseInt(data[1]);
            String b = data[2];
            int frames = Integer.parseInt(data[3]);

            Coin tmp = new Coin(x,y,b,frames);

            if (lvl == 1){
                coins.add(tmp);
            }
            if (lvl == 2){
                coins = new ArrayList<Coin>();
                coins.add(tmp);
            }
            if (lvl == 3){
                coins = new ArrayList<Coin>();
                coins.add(tmp);
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
        //mx = (int) mousePos.getX();
        //my = (int) mousePos.getY();
        g.drawImage(back, 0, 0, null);
        f++;
        //System.out.println(p.getX() + "," + p.getY());
        //System.out.println(walking + ","+ onPlat +"," + "," +falling + "," + midAir);
        //System.out.println(midAir);

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
        for (Platform p : plats){
            g.drawImage(p.getImage(),p.getX() - offset,p.getY()+p.getAdjust(),null);
            g.drawRect(p.getX()-offset,p.getY()+p.getAdjust(),p.getRect().width,p.getRect().height);
        }
        for (Goomba b : goombs){
            g.drawImage(b.getFrame(), b.getX()-offset, b.getY()+b.getAdjust(),null);
            //g.drawRect(b.getX()-offset,b.getY(),32,32);
        }
        for (Decor d : decor){
            g.drawImage(d.getImage(), d.getX()-offset, d.getY(),null);
            //g.drawRect(b.getX()-offset,b.getY(),32,32);
        }
        for(int i = 0; i < coins.size(); i++){
            g.drawImage(coins.get(i).getFrame(),coins.get(i).getX()-offset,coins.get(i).getY(),null);
            //g.setColor()
            g.drawRect(coins.get(i).getX()-offset,coins.get(i).getY(),coins.get(i).getRect().width,coins.get(i).getRect().height);
        }
        //Following code draws player sprites
        if(direction == right && !midAir && !attack){
            if(!walking){ //Standing Right
                if(currentF >= (idleRight.length - 1) * 8){
                    currentF = 0;
                }
                g.drawImage(idleRight[currentF/8], p.getX()-offset, p.getY(), null);
            }
            else{
                if(currentF >= (runRight.length - 1) * 3){
                    currentF = 0;
                }
                g.drawImage(runRight[currentF/3], p.getX()-offset, p.getY(), null);
            }
        }
        if(direction == left && !midAir && !attack){
            if(!walking){ //Standing Left
                if(currentF >= (idleLeft.length - 1) * 8){
                    currentF = 0;
                }
                g.drawImage(idleLeft[currentF/8], p.getX()-offset, p.getY(), null);
            }
            else{
                if(currentF >= (runLeft.length - 1) * 3){
                    currentF = 0;
                }
                g.drawImage(runLeft[currentF/3], p.getX()-offset, p.getY(), null);
            }
        }
        if(!midAir && attack){
            if(direction == right){
                if(currentF >= (att.length-1)*5){
                    currentF = 0;
                    attack = false;
                }
                g.drawImage(att[currentF/5],p.getX()-offset, p.getY(),null);
            }
            if(direction == left){
                if(currentF >= (att.length-1)*5){
                    currentF = 0;
                    attack = false;
                }
                g.drawImage(att[currentF/5],p.getX()-offset, p.getY(),null);
            }
        }
        if(midAir){
            if(direction == right){
                if(!attack){
                    g.drawImage(jumpRight, p.getX()-offset, p.getY(), null);
                }
                if(attack){
                    if(currentF >= (airPunchRight.length-1)*5){
                        currentF = 0;
                        attack = false;
                    }
                    g.drawImage(airPunchRight[currentF/5],p.getX()-offset, p.getY(), null);
                }
            }
            if(direction == left){
                if(!attack) {
                    g.drawImage(jumpLeft, p.getX() - offset, p.getY(), null);
                }
                if(attack){
                    if(currentF >= (airPunchLeft.length-1)*5){
                        currentF = 0;
                        attack = false;
                    }
                    g.drawImage(airPunchLeft[currentF/5],p.getX()-offset, p.getY(), null);
                }
            }
        }

        if (footCount % 15 == 0){
            playRun();
        }


        footCount++;
        currentF++;

        //drawing the rects
        g.setColor(Color.blue);
        g.drawRect(p.getX()+5-offset, p.getY()+12,40,55);
        //g.drawRect(b.getX()-offset, b.getY()+8, 20, 20);

        //TEXT
        g.setColor(new Color(0,0,00,125));
        //g.fillRect(1000,0,500,75);
        g.fillRect(0,0,1500,50);
        g.setColor(new Color(255,215,0,255));

        //g.setColor(Color.WHITE);
        g.setFont(fontLocal);
        g.drawString("SCORE  "+p.getScore(),975,35);

        //g.drawString(""+p.getScore(),500,500);


        //g.drawImage(platPic,500-offset,500,null);+

        //g.fillRect(500 - offset, 510, 1000, 40);
        //g.drawRect(500, 500, 1920,40);
    }

    //user moving the character f
    public void move(){
        if (keys[KeyEvent.VK_D]){
            if (p.getX() >= 600 + offset){
                offset += p.SPEED;

            }
            p.update(right);
            p.runR();
            direction = right;
            walking = true;

        }
        if (keys[KeyEvent.VK_A]) {
            p.update(left);
            p.runL();
            direction = left;
            walking = true;

        }
    }

    public void badMove(){
        for (Goomba b : goombs){
            if (b.getX() == b.getMaxL()){
                b.setDirection(right);
            }
            if (b.getX() == b.getMaxR()){
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

    public void checkRun(){
        //int f = 0;

        if (walking && p.getSy() < 1 && !falling && !midAir){
            //System.out.println("hi");
            //run.play();
            playRun = true;

        }
        else{
            //un.stop();
            playRun = false;
        }
    }

    public void playRun(){
        if (playRun) {
            for (Platform plat : plats) {
                if (!midAir) {
                    if (plat.getRect().intersects(p.getRect())) {
                        if (p.getRect().y - p.getSy() + p.getHeight() <= plat.getY()) {
                            if (walking && p.getSy() < 1 && !falling && !midAir) {
                                if (plat.platType().equals("long") || plat.platType().equals("air")) {
                                    runLeaf.play();
                                    playRun = false;
                                } else {
                                    run.play();
                                }
                            }
                        }

                    }

                }
                //run.play();
                //playRun = false;
            }
        }
        else{
            run.stop();
            runLeaf.stop();
        }

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
                falling = false;
                if (p.getRect().y-p.getSy()+p.getHeight() <= plat.getY()){//checking to make sure that the player is above the platform in order to land on it
                    //System.out.println("hi");
                    p.setSy(0);//because the player is on a platform, the speed in the y component is zero
                    p.setY(plat.getRect().y-55);
                    midAir = false;
                    onPlat = true;

                }

            }
            else{
                onPlat = false;
            }
            /*for (Goomba bad : goombs){
                if (bad.getRect().intersects(plat.getRect())){
                    bad.setSy(0);
                    bad.setY(plat.getRect().y-30);
                }
            }*/
        }
        for (int i=0; i < coins.size(); i++){
            if (coins.get(i).getRect().intersects(p.getRect())){
                p.addScore(25);
                coinSound.play();
                cRemove.add(coins.get(i));
            }
        }
    }

    public void removeCoins(){
        for (int i = 0; i < cRemove.size(); i++){
            coins.remove(cRemove.get(i));

        }
    }

    public void loadSprite(){
		loadSprite(runRight, runLeft, "Ryan Funyanjiwan/Run/run");
		loadSprite(idleRight, idleLeft, "Ryan Funyanjiwan/Idle/idle");
		loadSprite(kickRight, kickLeft, "Ryan Funyanjiwan/Side Kick/side kick");
		loadSprite(punchRight, punchLeft, "Ryan Funyanjiwan/Punch/punch");
		loadSprite(uppercutRight, uppercutLeft, "Ryan Funyanjiwan/Elbow Upercut/uppercut");
		loadSprite(airPunchRight, airPunchLeft, "Ryan Funyanjiwan/Jump Punch/jump punch");

        attackPickRight = new Image[][]{kickRight, punchRight, uppercutRight};
        attackPickLeft = new Image[][]{kickLeft, punchLeft, uppercutLeft};

		jumpRight = new ImageIcon("Ryan Funyanjiwan/jump.png").getImage();
		jumpLeft = flipImage(jumpRight);
	}
    public static int randint(int low, int high){
        return(int)(Math.random()*(high-low+1)+low);
    }
	public void loadSprite(Image[]actionRight, Image[]actionLeft, String directory){
		try{
			for(int i = 0; i < actionRight.length; i++) {
				Image img = ImageIO.read(new File(directory +  i + ".png"));
				actionRight[i] = img;
				actionLeft[i] = flipImage(img);
			}
		}
		catch(IOException e ){
			e.printStackTrace();
		}
	}
	public Image flipImage(Image image) {
		BufferedImage bImg = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g = (Graphics2D) bImg.getGraphics();
		g.drawImage(image, 0, 0, null);
		AffineTransform mirror = AffineTransform.getScaleInstance(-1, 1);
		mirror.translate(-bImg.getWidth(null), 0);
		AffineTransformOp mirrorOp = new AffineTransformOp(mirror, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		bImg = mirrorOp.filter(bImg, null);
		return bImg;
	}
}