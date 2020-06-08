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
	private int frame;
	private int frame2;
	private int stillRight;
	private int stillLeft;
	private int right;
	private int left;
	private int down;
	private int up;
	private int direction;
	private int bulletRight;
	private int bulletLeft;
	private int currentF;
	private int footCount;
	private int gDeadCount;
	private int gHitPic;
	private int level;
	private boolean midAir;//this boolean will make sure the user can't double jump
    private boolean onPlat;
    private boolean falling;
    private boolean playRun;
    private boolean miniscene1;
    private boolean miniscene2;
    private int lifeCounter;
    private int fireCount;

    private Rectangle badRect;
    private boolean enemyHit;
    private boolean playerHit;
    //rivate boolean drawGHitPic;
    //private ArrayList<Rectangle>platRects = new ArrayList<Rectangle>();
    //private ArrayList<Rectangle>badRects = new ArrayList<Rectangle>();
    private ArrayList<Platform>plats = new ArrayList<Platform>();
    private ArrayList<Life>pLives = new ArrayList<Life>();
    private ArrayList<Life>pLivesRemove = new ArrayList<Life>();

    private ArrayList<Life>lvlLives = new ArrayList<Life>();
    private ArrayList<Life>lvlLivesRemove = new ArrayList<Life>();

    //private ArrayList<Platform>plats1 = new ArrayList<Platform>();
    //private ArrayList<Platform>plats2 = new ArrayList<Platform>();
    //private ArrayList<Platform>plats3 = new ArrayList<Platform>();
    //yo
    private ArrayList<Goomba>goombs = new ArrayList<Goomba>();
    private ArrayList<Goomba>gDead = new ArrayList<Goomba>();
    private ArrayList<Goomba>gDeadRemove = new ArrayList<Goomba>();

    private ArrayList<Shooter>shooters = new ArrayList<Shooter>();
    private ArrayList<Shooter>sDead = new ArrayList<Shooter>();
    private ArrayList<Shooter>sDeadRemove = new ArrayList<Shooter>();

    private ArrayList<Decor>decor = new ArrayList<Decor>();

    private ArrayList<Coin>coins = new ArrayList<Coin>();
    private ArrayList<Coin>cRemove = new ArrayList<Coin>(); //Records all the coins that the player has collected
    //private ArrayList<Coin>cCurrent = new ArrayList<Coin>();

    private ArrayList<Bullet>bList = new ArrayList<Bullet>(); //array list for the projectiles (bullets)
    private ArrayList<Bullet>bRemove = new ArrayList<Bullet>(); //Records all the bullets that have hit an object

    private ArrayList<Bullet>badBList = new ArrayList<Bullet>();

    private int f = 0;
    private int offset;
	//images//
	private Image back1;
	private Image back2;
	private Image back3;
	private Image star;
	//private Image platPic;
	private Image longPlat;
	private Image airPlat;
	private Image deadBoss;

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
    private Image[] gotHitR;
    private Image[]gotHitL;
    private Image[]fireBoom;
    private Image[]fi;
    private Image plat;
    private Image door;

    private Image[]badBulletR;
    private Image[]badBulletL;

    private boolean attack;
    private boolean attackDone;
    private boolean hitBadGuy;
    private boolean loseLife;
    private boolean bossBattle;
    private boolean finish;
    //dfgsdfhsdfh

	private Image jumpRight;
	private Image jumpLeft;
	private Image text;
	//private Image heart;

	private Image flowerHit;

    private Sound coinSound;
    private Sound run;
    private Sound runLeaf;
    private Sound hit;
    private Sound oof;
    private Sound heal;
    private Sound ice;

    private Abobo abobo;

    Font fontLocal=null;
    Font newyork;

    public GamePanel(WeebChronicles m) {
    	keys = new boolean[KeyEvent.KEY_LAST+1];
		mainFrame = m;
        p = new Player();
        //b = new Goomba(500, 400,500,600);
        frame = 0;
        frame2 = 0;
        //Direction
        stillRight = 0;
        right = 1;
        left = 2;
        up = 6;
        down = 7;
        stillLeft = 3;
        fireCount = 0;

        direction = right;
        walking = false;
        onPlat = false;
        falling = true;

        playRun = false;

        //getLife = false;
        loseLife = false;

        offset = 0;
        currentF = 0;
        footCount = 0;
        gDeadCount = 0;
        bulletLeft = 4;
        bulletRight = 5;

		addKeyListener(this);

		//loading images//
		back1 = new ImageIcon("Pictures/back.png").getImage();
		back2 = new ImageIcon("Pictures/space.png").getImage();
		star = new ImageIcon("Pictures/star.png").getImage();
		//platPic = new ImageIcon("Pictures/plat pic.png").getImage();
		longPlat =  new ImageIcon("platPics/longPlat.png").getImage();
		airPlat = new ImageIcon("platPics/airPlat.png").getImage();
        text = new ImageIcon("Pictures/text bubble.png").getImage();
		flowerHit = new ImageIcon("badHitPics/flowerHit.png").getImage();
        deadBoss = new ImageIcon("Abobo/died/blownback17.png").getImage();
        plat = new ImageIcon("platPics/doorPlat.png").getImage();
        door = new ImageIcon("decorPics/door.png").getImage();
		//heart = new ImageIcon("heart.png").getImage();

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
        gotHitR = new Image[9];
        gotHitL = new Image[9];
        fireBoom = new Image[15];
        fi = new Image[15];

        badBulletR = new Image[30];
        badBulletL = new Image[30];

        //bigHeartR = new Image[16];
        //bigHeartL =  new Image[16];

        attack = false;
        attackDone = true;
        miniscene1 = false;
        miniscene2 = false;
        lifeCounter = 0;

        finish = false;

        abobo = new Abobo();

        loadSprite();

        try {
            fontLocal = Font.createFont(Font.TRUETYPE_FONT, new File("font/naruto1.ttf"));
            fontLocal = fontLocal.deriveFont(30f);
            newyork = Font.createFont(Font.TRUETYPE_FONT, new File("font/newyorkescape.ttf"));
            newyork = newyork.deriveFont(20f);
        }
        catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        try {
            coinSound = new Sound("coin.wav",false, 80);
            run = new Sound("run.wav",false, 80);
            runLeaf = new Sound("runLeaf.wav",false, 90);
            hit = new Sound("hit.wav", false, 80);
            oof = new Sound("oof.wav",false, 80);
            heal = new Sound("heal.wav",false, 80);
            ice = new Sound("iceShot.wav",false,80);
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
        if (e.getKeyCode() == KeyEvent.VK_W && !keys[e.getKeyCode()] && !p.checkHit() && !miniscene1 && !miniscene2){
           if(!midAir){
               p.jump();
               midAir = true;
           }
        }
        if(e.getKeyCode() == KeyEvent.VK_SPACE && !keys[e.getKeyCode()] && !p.checkHit() && !miniscene1 && !miniscene2){
            //Bullet b = new Bullet(p.getX()-offset, p.getY());
            //b.setDirection(direction);
            //bList.add(b);
        }
        if(e.getKeyCode() == KeyEvent.VK_ENTER && !keys[e.getKeyCode()] && attackDone && !p.checkHit() && !miniscene1 && !miniscene2){
            attack = true;
            currentF = 0;
            attackDone = false;
            //hit.setVol((float) 40);
            hit.play();
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

    public void loadPlats() throws IOException{
        Scanner inFile = new Scanner (new BufferedReader(new FileReader("plat1.txt")));
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

            plats.add(tmp);
            //platRects.add(tmp.getRect());

        }
    }

    public void loadGoombs() throws IOException{
        Scanner inFile = new Scanner (new BufferedReader(new FileReader("goomba1.txt")));
        while (inFile.hasNext()) {//while there are lines to be read
            String line = inFile.nextLine();
            String[] data = line.split(" ");//splitting up each value to be able to keep track of the x,y,max R, max L, and picture
            int x = Integer.parseInt(data[0]);
            int y = Integer.parseInt(data[1]);
            int mL = Integer.parseInt(data[2]);
            int mR = Integer.parseInt(data[3]);
            String b = data[4];
            int xa = Integer.parseInt(data[5]);
            int ya = Integer.parseInt(data[6]);
            int wa = Integer.parseInt(data[7]);
            int ha = Integer.parseInt(data[8]);
            int da = Integer.parseInt(data[9]);
            int num = Integer.parseInt(data[10]);
            int dNum = Integer.parseInt(data[11]);
            int hp = Integer.parseInt(data[12]);

            Goomba tmp = new Goomba(x, y, mL, mR, b, xa, ya, wa, ha, da, num, dNum, hp);

            goombs.add(tmp);

        }
    }

    public void loadShooters() throws IOException{
        Scanner inFile = new Scanner (new BufferedReader(new FileReader("shooter1.txt")));
        while (inFile.hasNext()) {//while there are lines to be read
            String line = inFile.nextLine();
            String[] data = line.split(" ");//splitting up each value to be able to keep track of the x,y,max R, max L, and picture
            int x = Integer.parseInt(data[0]);
            int y = Integer.parseInt(data[1]);
            int dist = Integer.parseInt(data[2]);
            String b = data[3];
            int num = Integer.parseInt(data[4]);
            int hp = Integer.parseInt(data[5]);

            Shooter tmp = new Shooter(x, y, dist, b, num, hp);

            shooters.add(tmp);

        }
    }



    public void loadDecor() throws IOException {
        Scanner inFile = new Scanner(new BufferedReader(new FileReader("decor1.txt")));
        while (inFile.hasNext()) {//while there are lines to be read
            String line = inFile.nextLine();
            String[] data = line.split(" ");//splitting up each value to be able to keep track of the x,y,max R, max L, and picture
            int x = Integer.parseInt(data[0]);
            int y = Integer.parseInt(data[1]);
            String b = data[2];


            Decor tmp = new Decor(x,y,b);


            decor.add(tmp);

        }
    }

    public void loadPLives(){

        Life tmp1 = new Life (0,-5,16);
        Life tmp2 =  new Life (60,-5,16);
        Life tmp3 = new Life(120,-5,16);
        Life tmp4 = new Life (180,-5,16);
        Life tmp5 = new Life(240,-5,16);

        pLives.add(tmp1);
        pLives.add(tmp2);
        pLives.add(tmp3);
        pLives.add(tmp4);
        pLives.add(tmp5);
    }

    public void loadLvlLives() throws IOException {
        Scanner inFile = new Scanner(new BufferedReader(new FileReader("life1.txt")));
        while (inFile.hasNext()) {//while there are lines to be read
            String line = inFile.nextLine();
            String[] data = line.split(" ");//splitting up each value to be able to keep track of the x,y,max R, max L, and picture
            int x = Integer.parseInt(data[0]);
            int y = Integer.parseInt(data[1]);
            int frames = Integer.parseInt(data[2]);
            Life tmp = new Life(x,y,frames);

            lvlLives.add(tmp);

        }
    }

    public void loadCoins()throws IOException{
        Scanner inFile = new Scanner(new BufferedReader(new FileReader("coin1.txt")));

        while (inFile.hasNext()) {//while there are lines to be read
            String line = inFile.nextLine();
            String[] data = line.split(" ");//splitting up each value to be able to keep track of the x,y,max R, max L, and picture
            int x = Integer.parseInt(data[0]);
            int y = Integer.parseInt(data[1]);
            String b = data[2];
            int frames = Integer.parseInt(data[3]);

            Coin tmp = new Coin(x,y,b,frames);


            coins.add(tmp);

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
        //g.drawImage(back+level, 0, 0, null);

        g.drawImage(back1, 0, 0, null);

        //g.drawImage(heart,200, 0, null);
        //+12,100,55
        //-40,y+12,90,55
        //g.drawRect(p.getX()-50-offset,p.getY()+12,100,55);
        //System.out.println(p.getX() + "," + p.getY());
        //System.out.println(p.getLives());

        for (Platform p : plats){
            g.drawImage(p.getImage(),p.getX() - offset,p.getY()+p.getAdjust(),null);

        }
        for (Goomba b : goombs){
            if (b.drawHitPic()){//b.checkHit()){
                if (b.getDirection() == 1){
                    g.drawImage(b.getLHitImage(), b.getX()-offset, b.getY(),null);
                    //b.setX(b.getX()-(int)b.getSx());
                }
                else{
                    g.drawImage(b.getRHitImage(), b.getX()-offset, b.getY(),null);
                    //b.setX(b.getX()-(int)b.getSx());
                }

                if (gHitPic % 20 == 0){
                    b.setDrawHitPic(false);
                }
                b.setHit(false);
            }
            else{
                g.drawImage(b.getFrame(), b.getX()-offset, b.getY(),null);
            }
            //else{

            //g.drawRect(b.getX()-offset+b.getXAdjust(),b.getY()+b.getYAdjust(), b.getWidth(), b.getHeight());

            //g.drawRect(b.getX()-offset,b.getY(),32,32);
        }


        for (Goomba b : gDead){
            g.drawImage(b.getDeadFrame(), b.getX()-offset, b.getY()+b.getDAdjust(), null);
        }

        for (Shooter s : shooters){
            g.drawImage(s.getFrame(),s.getX()-offset,s.getY(),null);
        }

        for (Decor d : decor){
            g.drawImage(d.getImage(), d.getX()-offset, d.getY(),null);
            //g.drawRect(b.getX()-offset,b.getY(),32,32);
        }
        for(int i = 0; i < coins.size(); i++){
            g.drawImage(coins.get(i).getFrame(),coins.get(i).getX()-offset,coins.get(i).getY(),null);
            //g.setColor()
        }

        for (Bullet b : badBList){
            g.drawImage(badBulletL[currentF/30], b.getX()-offset,b.getY(),null);
        }


        for (int i=0; i < lvlLives.size(); i++){
            g.drawImage(lvlLives.get(i).getFrame(),lvlLives.get(i).getX()-offset,lvlLives.get(i).getY(),null);
        }


        if (direction == right && p.checkHit()){
            if(currentF >= (gotHitR.length -1 )*9){
                currentF = 0;
                p.setHit(false);
                lifeCounter = 0;
            }
            p.knockback(-2);
            g.drawImage(gotHitR[currentF/9], p.getX()-offset,p.getY(),null);

        }
        if (direction == left && p.checkHit()){
            if(currentF >= (gotHitL.length - 1)*9){
                currentF = 0;
                p.setHit(false);
                lifeCounter = 0;
            }
            p.knockback(2);
            g.drawImage(gotHitL[currentF/9], p.getX()-offset,p.getY(),null);

        }

        //Following code draws player sprites
        if(direction == right && !midAir && !attack && !p.checkHit()){
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
        if(direction == left && !midAir && !attack && !p.checkHit()){
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
        hitBadGuy = false;
        if(attack && !p.checkHit()){
            if(!midAir && !walking) {
                if (direction == right) {
                    if (currentF >= (att.length - 1) * 5) {
                        currentF = 0;
                        attack = false;
                        attackDone = true;
                        hitBadGuy = true;
                    }
                    g.drawImage(att[currentF / 5], p.getX() - offset, p.getY(), null);
                }
                if (direction == left) {
                    if (currentF >= (att.length - 1) * 5) {
                        currentF = 0;
                        attack = false;
                        attackDone = true;
                        hitBadGuy = true;
                    }
                    g.drawImage(att[currentF / 5], p.getX() - offset, p.getY(), null);
                }
            }
            else{
                if(direction == right){
                    if(currentF >= (airPunchRight.length-1)*5){
                        currentF = 0;
                        attack = false;
                        attackDone = true;
                        hitBadGuy = true;
                    }
                    g.drawImage(airPunchRight[currentF/5],p.getX()-offset, p.getY(), null);
                }
                if(direction == left){
                    if(currentF >= (airPunchLeft.length-1)*5){
                        currentF = 0;
                        attack = false;
                        attackDone = true;
                        hitBadGuy = true;
                    }
                    g.drawImage(airPunchLeft[currentF/5],p.getX()-offset, p.getY(), null);
                }
            }
        }
        if(midAir && !p.checkHit()){
            if(direction == right){
                if(!attack){
                    g.drawImage(jumpRight, p.getX()-offset, p.getY(), null);
                }
            }
            if(direction == left){
                if(!attack) {
                    g.drawImage(jumpLeft, p.getX() - offset, p.getY(), null);
                }
            }
        }

        if (footCount % 15 == 0){
            playRun();
        }


        footCount++;
        currentF++;
        gDeadCount++;
        gHitPic++;

        //drawing the rects
        g.setColor(Color.blue);
        //g.drawRect(p.getX()+5-offset, p.getY()+12,40,55);
        //x+5,y+12,80,45
        //g.drawRect(b.getX()-offset, b.getY()+8, 20, 20);

        //TEXT
        g.setColor(new Color(0,0,00,125));
        //g.fillRect(1000,0,500,75);
        g.fillRect(0,0,1500,50);
        g.setColor(new Color(255,215,0,255));
        g.setFont(fontLocal);
        g.drawString("SCORE " + " " + " " + p.getScore(),1000,40);

        //g.setColor(Color.WHITE);


        /*for(int i = 0; i<p.getLives(); i++){
            for (Life l : pLives){
                g.drawImage(l.getFrame(),l.getX(),l.getY(),null);
            }
        }*/

        for(int i = 0; i<p.getLives(); i++){
            g.drawImage(pLives.get(i).getFrame(),pLives.get(i).getX(),pLives.get(i).getY(),null);
        }

        if (bossBattle){
            g.drawImage(abobo.getFrame(),abobo.getX()-offset,abobo.getY(),null);
        }
        if(abobo.getStart() && bossBattle){
            g.setColor(Color.white);
            g.fillRect(295,95,610,60);
            g.setColor(Color.gray);
            g.fillRect(300,100,600,50);
            g.setColor(Color.white);
            g.fillRect(360, 110,520, 30);
            g.setFont(newyork);
            g.drawString("HP", 310,130);
            g.setColor(Color.black);
            g.fillRect(364, 114, 512, 22);
            g.setColor(Color.green);
            g.fillRect(364, 114, 32*abobo.getHits(), 22);
        }

        if(miniscene1){
            frame++;
            if(frame > 100) {
                g.drawImage(text, 630, 340, null);
                g.setColor(Color.black);
                g.setFont(newyork);
                if (frame < 230) {
                    g.drawString("AHAHAHAHA!", 750, 415);
                }
            }
            if(frame > 250 && frame < 380){
                g.drawString("Just as expected!", 702,415);
            }
            if(frame > 400){
                g.drawString("Prove your worth", 705,415);
            }
            if(frame > 530){
                miniscene1 = false;
                abobo.start();
                frame = 0;
            }
        }
        if(miniscene2){
            frame++;
            g.drawImage(deadBoss, abobo.getX()-offset, abobo.getY()+70, null);
            if(frame > 70){
                g.drawImage(plat, 340,440,null);
                g.drawImage(door,395,350,null);
                if(frame < 180){
                    g.drawImage(fireBoom[fireCount / 8], 325, 260, null);
                    if(fireCount >= (fireBoom.length - 1) * 8) {
                        fireCount = -1;
                    }
                    fireCount++;
                }
            }
            if(frame > 270){
                g.setColor(Color.white);
                g.setFont(newyork);
                g.drawString("Ryan was determined. Nothing was to stop him.", 150,190);
            }
            if(frame > 570){
                g.setColor(Color.black);
                g.fillRect(0,0,1200,650);
            }
            if(frame > 600){
                finish = true;
            }
        }

        /*for (Life l : pLives){
            g.drawImage(l.getFrame(),l.getX(),l.getY(),null);
        }*/

        /*for (int i = 0; i < pLives.size(); i++){
            g.drawImage(pLives.get(i).getFrame(),pLives.get(i).getX(),pLives.get(i).getY(),null);
        }*/

        //g.drawString(""+p.getScore(),500,500);


        //g.drawImage(platPic,500-offset,500,null);+

        //g.fillRect(500 - offset, 510, 1000, 40);
        //g.drawRect(500, 500, 1920,40);
    }

    //user moving the character f
    public void move(){
        if (keys[KeyEvent.VK_D] && !p.checkHit() && !miniscene1 && !miniscene2){
            if (p.getX() >= 7300){
                p.setX(p.getX());
                if (p.getX() >= 7820){
                    p.setX(7820);
                }
            }
            else{
                if (p.getX() >= 600 + offset) {
                    offset += p.SPEED;
                }
            }
            p.update(right);

            p.runR();
            direction = right;
            walking = true;
            if (p.getX() >= 6500){
                bossBattle = true;
            }
            if(p.getX() >= 7290 && frame < 530 && !abobo.getStart()){
                miniscene1 = true;
            }
        }
        if (keys[KeyEvent.VK_A] && !p.checkHit() && !miniscene1 && !miniscene2) {
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
        for (Shooter s : shooters){
            if (s.getY() == s.getMaxH()) {
                s.setShoot(true);
                s.setDirection(down);
            }
            if (s.getY() == s.getMinH()){
                s.setShoot(true);
                s.setDirection(up);
            }
            if (s.getDirection() == up){
                s.moveU();
            }
            if (s.getDirection() == down){
                s.moveD();
            }
        }
    }


    public void playerUpdate(){
        p.update2();
        if(p.getSy() == 0){
            midAir = false;
            p.resetCurrentF();
        }
        if (p.getX() - offset < 20){//making sure the player can't run off the screen on the left
            p.setX(20 + offset);
        }
    }

    public void badUpdate(){
        for (Goomba b : goombs){
            b.update();
        }
        for (Shooter s : shooters){
            s.update();
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
        //System.out.println(p.checkHit());
        //
        // System.out.println(lifeCounter);
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

        }

        for (int i=0; i < goombs.size(); i++){
            if (goombs.get(i).checkDead()) {
                gDead.add(goombs.get(i));
            }
        }

        for (int i = 0; i < gDead.size(); i++){
            if (gDeadCount % 50 == 0){
                gDeadRemove.add(gDead.get(i));
                gDeadCount = 0;
                p.addScore(30);
            }
        }


        //TURNED OFF FOR NOW
        for (Goomba bad : goombs){
            if (p.getRect().intersects(bad.getRect()) && !attack){
                p.setHit(true);
                lifeCounter++;
                oof.play();

                //p.loseLife();
            }
        }


        for (Goomba bad : goombs) {
            if(hitBadGuy){
                if (direction == right){
                    if (bad.getRect().intersects(p.getRHitRect())){
                        bad.setHit(true);
                        bad.setDrawHitPic(true);
                        bad.loseHp(50);
                        hitBadGuy = false;
                    }
                }
                else if (direction == left){
                    if (bad.getRect().intersects(p.getLHitRect())){
                        bad.setHit(true);
                        bad.setDrawHitPic(true);
                        //drawGHitPic = true;
                        bad.loseHp(50);
                        hitBadGuy = false;
                    }
                }
            }
        }
        if(abobo.getRect().intersects(p.getRect())){
            if(hitBadGuy && !abobo.getAttack()){
                abobo.gotHit();
            }
            if(!attack && abobo.getAttack()){
                if(abobo.getDirection() == right){
                    if(p.getX() > abobo.getX()){
                        p.setHit(true);
                        oof.play();
                        lifeCounter++;
                    }
                }
                if(abobo.getDirection() == left){
                    if(p.getX() < abobo.getX()){
                        p.setHit(true);
                        oof.play();
                        lifeCounter++;
                    }
                }
            }
            if(hitBadGuy && abobo.getAttack()){
                abobo.gotHit();
            }
        }
        for (Bullet b : badBList){
            if (b.getRect().intersects(p.getRect())){
                b.setHit(true);
            }
        }

        for (int i=0; i < coins.size(); i++){
            if (coins.get(i).getRect().intersects(p.getRect())){
                p.addScore(25);
                coinSound.play();
                cRemove.add(coins.get(i));
            }
        }

        for (int i = pLives.size()-1; i > -1; i--){
            if (p.checkHit()){
                if (lifeCounter == 1){

                    p.loseLife();
                    pLivesRemove.add(pLives.get(i));

                    p.setHit(false);


                }
            }
        }



        for (int i=0; i < lvlLives.size(); i++) {
            if (lvlLives.get(i).getRect().intersects(p.getRect())) {
                if (p.getLives() == 4) {
                    p.gainLife();
                    heal.play();
                    drawAddedLife(4);
                    lvlLivesRemove.add(lvlLives.get(i));
                }
                if (p.getLives() == 3) {
                    p.gainLife();
                    heal.play();
                    drawAddedLife(3);
                    lvlLivesRemove.add(lvlLives.get(i));
                }
                if (p.getLives() == 2) {
                    p.gainLife();
                    heal.play();
                    drawAddedLife(2);
                    lvlLivesRemove.add(lvlLives.get(i));
                }
                if (p.getLives() == 1) {
                    p.gainLife();
                    heal.play();
                    drawAddedLife(1);
                    lvlLivesRemove.add(lvlLives.get(i));
                }
            }
        }

        for (int i = 0; i < badBList.size(); i++){
            if (badBList.get(i).checkHit()) {
                bRemove.add(badBList.get(i));
                oof.play();
                p.loseLife();
                p.setHit(true);
                p.knockback(-2);
            }
            if (badBList.get(i).getX() - offset < 100 )
                bRemove.add(badBList.get(i));
            }

        if (abobo.getHits() <= 0){
            miniscene2 = true;
            bossBattle = false;
        }
    }


    public void drawAddedLife(int num){
        Life tmp = new Life(60*num,-5,16);
        pLives.add(tmp);
    }

    public void removeGoombs(){
        for (int i = 0; i < gDead.size(); i++){
            goombs.remove(gDead.get(i));
        }
    }

    public void removeDGoombs(){
        for (int i = 0; i < gDeadRemove.size(); i++){
            gDead.remove(gDeadRemove.get(i));
        }
    }

    public void removeCoins(){
        for (int i = 0; i < cRemove.size(); i++){
            coins.remove(cRemove.get(i));
        }
    }

    public void removePLives(){
        for (int i = 0; i < pLivesRemove.size(); i++){
            pLives.remove(pLivesRemove.get(i));
        }
    }

    public void removeLvlLives(){
        for (int i = 0; i < lvlLivesRemove.size(); i++){
            lvlLives.remove(lvlLivesRemove.get(i));
        }
    }

    public void removeBullets(){
        for (int i = 0; i < bRemove.size(); i ++){
            badBList.remove(bRemove.get(i));
        }
    }

    public int getLevel(){
        return level;
    }

    public boolean checkFinish(){
        return finish;
    }

    /*public void reset() throws IOException {
        //offset = 0;
        p.setX(140);
        p.setY(340);
        plats = new ArrayList<Platform>();
        goombs = new ArrayList<Goomba>();
        decor = new ArrayList<Decor>();
        coins = new ArrayList<Coin>();
        lvlLives = new ArrayList<Life>();
        shooters = new ArrayList<Shooter>();
        loadPlats("plat" + level + ".txt");
        loadGoombs("goomba" + level + ".txt");
        loadDecor("decor" + level + ".txt");
        loadCoins("coin" + level + ".txt");
        loadLvlLives("life" + level + ".txt");
        loadShooters("shooter"+level+".txt");

    }*/


    public void loadSprite(){
		loadSprite(runRight, runLeft, "Ryan Funyanjiwan/Run/run");
		loadSprite(idleRight, idleLeft, "Ryan Funyanjiwan/Idle/idle");
		loadSprite(kickRight, kickLeft, "Ryan Funyanjiwan/Side Kick/side kick");
		loadSprite(punchRight, punchLeft, "Ryan Funyanjiwan/Punch/punch");
		loadSprite(uppercutRight, uppercutLeft, "Ryan Funyanjiwan/Elbow Upercut/uppercut");
		loadSprite(airPunchRight, airPunchLeft, "Ryan Funyanjiwan/Jump Punch/jump punch");
		loadSprite(gotHitR, gotHitL, "Ryan Funyanjiwan/Gets Hit/hit");
		loadSprite(badBulletR, badBulletL, "bulletFrames/ice/tile");
        loadSprite(fireBoom, fi,"fire boom/fire");
        //loadSprite(artR, bigHeartL, "heartFrames/tile");

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