//GamePanel.java
//Jim Ji and Edward Yang
//Class that loads everything in for the first level

//imports
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
import java.util.ArrayList;
import java.util.Scanner;


public class GamePanel extends JPanel implements KeyListener{

    //fields
	private boolean []keys;
	private WeebChronicles mainFrame;
	private Player p;
	private int frame;//counter to allow for animation in the cutscenes
	private int right;
	private int left;
	private int direction;
	private int currentF;//counter used for the sprites allowing for animation
	private int footCount;//counter used for the footsteps of the player
	private int gDeadCount;//counter used for animating the death of any dead goombas
	private int gHitPic;//counter used for the frame at which the player hits a goomba to allow it to glow red
    private int lifeCounter;//counter used so that the player won't lose all his lives when hit by an enemy
    private int fireCount;//counter used for the fire blast in the first cutscene
    private int offset;/*this variable increases when the player moves past a certain point on the screen. This allows for the "side scrolling" effect. Whenever
    I want an object to scroll with the player, I subtract this number from the objects' X value.*/

    //arraylists to store the aspects of the level (platforms, coins, enemies, etc)
    private ArrayList<Platform>plats = new ArrayList<Platform>();//storing the platforms in the first level
    private ArrayList<Life>pLives = new ArrayList<Life>();//storing the player's lives in the first level
    private ArrayList<Life>pLivesRemove = new ArrayList<Life>();//arraylist that stores when the player loses a life so that it can be removed from the arraylist above
    private ArrayList<Life>lvlLives = new ArrayList<Life>();//storing the lives that the player can pick up to gain a life in the first level
    private ArrayList<Life>lvlLivesRemove = new ArrayList<Life>();//storing the lives when the player picks up a life so that i can be removed in the level
    private ArrayList<Goomba>goombs = new ArrayList<Goomba>();//storing the goombas
    private ArrayList<Goomba>gDead = new ArrayList<Goomba>();//storing the goombas that have died
    private ArrayList<Goomba>gDeadRemove = new ArrayList<Goomba>();//storing the dead goombas so that that their death animation will be drawn
    private ArrayList<Decor>decor = new ArrayList<Decor>();//storing the decoration
    private ArrayList<Coin>coins = new ArrayList<Coin>();//storing the coins and their positions
    private ArrayList<Coin>cRemove = new ArrayList<Coin>(); //Records all the coins that the player has collected
    private ArrayList<Bullet>bList = new ArrayList<Bullet>(); //array list for the projectiles (bullets)
    private ArrayList<Bullet>bRemove = new ArrayList<Bullet>(); //Records all the bullets that have hit an object
    //



	//images//
	private Image back1;
	private Image deadBoss;
    private Image jumpRight;
    private Image jumpLeft;
    private Image text;
    private Image plat;
    private Image door;
    //

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
    private Image[]badBulletR;
    private Image[]badBulletL;
    //

    //boolean values
    private boolean attack;//boolean that tracks whether or not the player has attacked
    private boolean attackDone;//boolean that tracks if the player has finished the attack animation
    private boolean walking;
    private boolean hitBadGuy;//if the player has hit an enemy
    private boolean bossBattle;//boolean indicating that the boss battle will start
    private boolean finish;//boolean indicating if the player has finished the level
    private boolean midAir;//this boolean will make sure the user can't double jump
    private boolean falling;//checks if the player is falling
    private boolean playRun;//checks if the footstep sound can be played
    private boolean miniscene1;//keeps track of which scene needs to be played
    private boolean miniscene2;
    //

    //sound files
    private Sound coinSound;
    private Sound run;
    private Sound runLeaf;
    private Sound hit;
    private Sound oof;
    private Sound heal;
    //

    //bosses
    private Abobo abobo;//the first level boss object. We have a class that tracks his movement, attacks, and health. (Abobo.java)
    //

    //fonts
    Font fontLocal=null;
    Font newyork;
    //

    public GamePanel(WeebChronicles m) {
    	keys = new boolean[KeyEvent.KEY_LAST+1];
		mainFrame = m;
        p = new Player();
        frame = 0;
        fireCount = 0;
        //Direction
        right = 1;
        left = 2;
        //

        direction = right;
        walking = false;
        falling = true;
        playRun = false;
        attack = false;
        attackDone = true;
        miniscene1 = false;
        miniscene2 = false;
        lifeCounter = 0;
        finish = false;

        offset = 0;
        currentF = 0;
        footCount = 0;
        gDeadCount = 0;

		addKeyListener(this);

		//loading images//
		back1 = new ImageIcon("Pictures/back.png").getImage();
        text = new ImageIcon("Pictures/text bubble.png").getImage();
        deadBoss = new ImageIcon("Abobo/died/blownback17.png").getImage();
        plat = new ImageIcon("platPics/doorPlat.png").getImage();
        door = new ImageIcon("decorPics/door.png").getImage();
        //

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
        //

        abobo = new Abobo();

        loadSprite();

        //loading in fonts
        try {
            fontLocal = Font.createFont(Font.TRUETYPE_FONT, new File("font/naruto1.ttf"));
            fontLocal = fontLocal.deriveFont(30f);
            newyork = Font.createFont(Font.TRUETYPE_FONT, new File("font/newyorkescape.ttf"));
            newyork = newyork.deriveFont(20f);
        }
        catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        //

        //loading in sounds
        try {
            coinSound = new Sound("coin.wav",false, 80);
            run = new Sound("run.wav",false, 80);
            runLeaf = new Sound("runLeaf.wav",false, 90);
            hit = new Sound("hit.wav", false, 80);
            oof = new Sound("oof.wav",false, 80);
            heal = new Sound("heal.wav",false, 80);
        }
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e){
            e.printStackTrace();
        }
        //
    }
    public void addNotify() {
        super.addNotify();
        requestFocus();
        mainFrame.start();
    }

    public void keyTyped(KeyEvent e){
    }
    public void keyPressed(KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_W && !keys[e.getKeyCode()] && !p.checkHit() && !miniscene1 && !miniscene2){//checking if the user hits the "jump" button
           if(!midAir){//if the player is on the ground
               p.jump();
               midAir = true;
           }
        }
        if(e.getKeyCode() == KeyEvent.VK_ENTER && !keys[e.getKeyCode()] && attackDone && !p.checkHit() && !miniscene1 && !miniscene2){//checking if the user hits the "attack" button
            attack = true;
            currentF = 0;
            attackDone = false;
            hit.play();//playing the punch sound effect
            if(!midAir){
                if(direction == right){
                    att = attackPickRight[randint(0,2)];//randomely picks an attack from the idle attack frames
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


    public void loadPlats() throws IOException{//loading in the platforms from a text file
        Scanner inFile = new Scanner (new BufferedReader(new FileReader("plat1.txt")));
        while (inFile.hasNext()){//while there are lines to be read
            String line = inFile.nextLine();
            String[]data = line.split(" ");//splitting up each value to be able to keep track of the x,y, type, and adjustment
            int x = Integer.parseInt(data[0]);
            int y = Integer.parseInt(data[1]);
            String p = data[2];
            int a = Integer.parseInt(data[3]);//this value in the text file represents an adjustment. sometimes the platform's rect would not be exactly where we wanted it
            Platform tmp = new Platform(x,y,p,a);
            plats.add(tmp);
        }
    }

    public void loadGoombs() throws IOException{//loading in the goombas from a text file
        Scanner inFile = new Scanner (new BufferedReader(new FileReader("goomba1.txt")));
        while (inFile.hasNext()) {//while there are lines to be read
            String line = inFile.nextLine();
            String[] data = line.split(" ");//splitting up each value to be able to keep track of the x,y,max R, max L, type, adjustments, number of frames, and health
            int x = Integer.parseInt(data[0]);
            int y = Integer.parseInt(data[1]);
            int mL = Integer.parseInt(data[2]);//max left
            int mR = Integer.parseInt(data[3]);//max right
            String b = data[4];
            int xa = Integer.parseInt(data[5]);//x adjustment
            int ya = Integer.parseInt(data[6]);//y adjustment
            int wa = Integer.parseInt(data[7]);//width adjustment
            int ha = Integer.parseInt(data[8]);//height adjustment
            int da = Integer.parseInt(data[9]);//death adjustment
            int num = Integer.parseInt(data[10]);//number of walking frames
            int dNum = Integer.parseInt(data[11]);//num of death frames
            int hp = Integer.parseInt(data[12]);//health

            Goomba tmp = new Goomba(x, y, mL, mR, b, xa, ya, wa, ha, da, num, dNum, hp);
            goombs.add(tmp);

        }
    }

    public void loadDecor() throws IOException {
        Scanner inFile = new Scanner(new BufferedReader(new FileReader("decor1.txt")));
        while (inFile.hasNext()) {//while there are lines to be read
            String line = inFile.nextLine();
            String[] data = line.split(" ");//splitting up each value to be able to keep track of the x,y, and type
            int x = Integer.parseInt(data[0]);
            int y = Integer.parseInt(data[1]);
            String b = data[2];
            Decor tmp = new Decor(x,y,b);
            decor.add(tmp);
        }
    }

    public void loadPLives(){//loading in the players' lives
        Life tmp1 = new Life (0,-5,16);
        Life tmp2 =  new Life (60,-5,16);
        Life tmp3 = new Life(120,-5,16);
        Life tmp4 = new Life (180,-5,16);
        Life tmp5 = new Life(240,-5,16);

        pLives.add(tmp1);//adding to the arraylist that stores them
        pLives.add(tmp2);
        pLives.add(tmp3);
        pLives.add(tmp4);
        pLives.add(tmp5);
    }

    public void loadLvlLives() throws IOException {//loading in the attainable lives in the level, the player will heal one life if he has is not at full lives
        Scanner inFile = new Scanner(new BufferedReader(new FileReader("life1.txt")));
        while (inFile.hasNext()) {//while there are lines to be read
            String line = inFile.nextLine();
            String[] data = line.split(" ");//splitting up each value to be able to keep track of the x,y, number of frames
            int x = Integer.parseInt(data[0]);
            int y = Integer.parseInt(data[1]);
            int frames = Integer.parseInt(data[2]);
            Life tmp = new Life(x,y,frames);
            lvlLives.add(tmp);
        }
    }

    public void loadCoins()throws IOException{//loading coins
        Scanner inFile = new Scanner(new BufferedReader(new FileReader("coin1.txt")));
        while (inFile.hasNext()) {//while there are lines to be read
            String line = inFile.nextLine();
            String[] data = line.split(" ");//splitting up each value to be able to keep track of the x,y, file, and num of frames
            int x = Integer.parseInt(data[0]);
            int y = Integer.parseInt(data[1]);
            String b = data[2];
            int frames = Integer.parseInt(data[3]);
            Coin tmp = new Coin(x,y,b,frames);
            coins.add(tmp);

        }
    }

    public void paintComponent(Graphics g){
        g.drawImage(back1, 0, 0, null);//background

        for (Platform p : plats){//drawing platform images
            g.drawImage(p.getImage(),p.getX() - offset,p.getY()+p.getAdjust(),null);//i subtract offset from the platform's x because I want the illusion of the player running through the level
        }

        for (Goomba b : goombs){
            if (b.drawHitPic()){//if the indication of being hit needs to be drawn
                if (b.getDirection() == left){
                    g.drawImage(b.getLHitImage(), b.getX()-offset, b.getY(),null);
                }
                else{
                    g.drawImage(b.getRHitImage(), b.getX()-offset, b.getY(),null);
                }

                if (gHitPic % 20 == 0){//we added a counter here because the picture that indicated that the goomba was hit was drawn too fast, we wanted to prolong the number of frames
                    b.setDrawHitPic(false);
                }
                b.setHit(false);
            }
            else{
                g.drawImage(b.getFrame(), b.getX()-offset, b.getY(),null);//drawing the goomba normally if they are not being hit
            }
        }

        for (Goomba b : gDead){//drawing the dying frames when a goomba dies
            g.drawImage(b.getDeadFrame(), b.getX()-offset, b.getY()+b.getDAdjust(), null);
        }

        for (Decor d : decor){//drawing the decoration
            g.drawImage(d.getImage(), d.getX()-offset, d.getY(),null);
        }
        for(int i = 0; i < coins.size(); i++){//drawing coins
            g.drawImage(coins.get(i).getFrame(),coins.get(i).getX()-offset,coins.get(i).getY(),null);
        }

        for (int i=0; i < lvlLives.size(); i++){//drawing the level lives that the player can heal from
            g.drawImage(lvlLives.get(i).getFrame(),lvlLives.get(i).getX()-offset,lvlLives.get(i).getY(),null);
        }

        if (direction == right && p.checkHit()){//if the player is facing right and has been hit by an enemy
            if(currentF >= (gotHitR.length -1 )*9){//drawing the getting hit frames
                currentF = 0;
                p.setHit(false);
                lifeCounter = 0;//we use a counter here because we had a problem where the player would lose all of his lives when hit by an enemy. this allows it to only take one life when hit
            }
            p.knockback(-2);//knockback function from player class that simulates being hit back. the player cannot move while being knocked back
            g.drawImage(gotHitR[currentF/9], p.getX()-offset,p.getY(),null);

        }
        if (direction == left && p.checkHit()){//same as above but for the left side
            if(currentF >= (gotHitL.length - 1)*9){
                currentF = 0;
                p.setHit(false);
                lifeCounter = 0;
            }
            p.knockback(2);//knocking back in the opposite direction as the right side
            g.drawImage(gotHitL[currentF/9], p.getX()-offset,p.getY(),null);

        }

        //Following code draws player sprites
        if(direction == right && !midAir && !attack && !p.checkHit()){
            if(!walking){ //Standing Right
                if(currentF >= (idleRight.length - 1) * 8){
                    currentF = 0;
                }
                g.drawImage(idleRight[currentF/8], p.getX()-offset, p.getY(), null);//we divide the currentF by a number to act as a buffer so that the frames don't change every loop
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

        if(attack && !p.checkHit()){//checking if the player has hit an enemy
            if(!midAir && !walking) {//making sure they are on the ground
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

        if(midAir && !p.checkHit()){//if in the air and not hit by an enemy
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

        if (footCount % 15 == 0){//this counter was used a a delay when playing the footstep sound effect. we had a problem where the footsteps were way too fast and we wanted to slow it down
            playRun();
        }

        //adding onto the counters
        footCount++;
        currentF++;
        gDeadCount++;
        gHitPic++;
        //


        //TEXT
        g.setColor(new Color(0,0,0,125));//setting a transparent rectangle at the top of the screen
        g.fillRect(0,0,1500,50);
        g.setColor(new Color(255,215,0,255));
        g.setFont(fontLocal);
        g.drawString("SCORE " + " " + p.getScore(),1000,40);//player's score


        for(int i = 0; i<p.getLives(); i++){//drawing the players' lives
            g.drawImage(pLives.get(i).getFrame(),pLives.get(i).getX(),pLives.get(i).getY(),null);
        }

        if (bossBattle){//if the boss battle is about to begin
            g.drawImage(abobo.getFrame(),abobo.getX()-offset,abobo.getY(),null);
        }
        if(abobo.getStart() && bossBattle){
            //following code draws an hp bar when fighting the boss
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
            //
        }

        if(miniscene1){//starting the mini scene to introduce the first boss
            frame++;//counter used for the cut scenes
            if(frame > 100) {
                g.drawImage(text, 630, 340, null);
                g.setColor(Color.black);
                g.setFont(newyork);
                if (frame < 230) {//text from the boss
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
                abobo.start();//starting the bosses movement
                frame = 0;
            }
        }
        if(miniscene2){//the scene when the player clears the first level
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
                finish = true;//advancing to the second level
            }
        }

    }

    //user moving the character
    public void move(){
        if (keys[KeyEvent.VK_D] && !p.checkHit() && !miniscene1 && !miniscene2){
            if (p.getX() >= 7300){//once the player has reached this certain point, the scrolling will stop and the boss battle will start. we didn't want the player to scroll while fighting the bos
                p.setX(p.getX());
                if (p.getX() >= 7820){
                    p.setX(7820);
                }
            }
            else{
                if (p.getX() >= 600 + offset) {
                    offset += p.SPEED;//when the player is at half way in the screen's coordinates, start adding to the offset variable to allow scrolling
                }
            }
            p.direct(right);
            p.runR();//player moves right because the user if holding the "d" key
            direction = right;
            walking = true;
            if (p.getX() >= 6500){
                bossBattle = true;//starting the boss battle
            }
            if(p.getX() >= 7290 && frame < 530 && !abobo.getStart()){
                miniscene1 = true;
            }
        }
        if (keys[KeyEvent.VK_A] && !p.checkHit() && !miniscene1 && !miniscene2) {
            p.direct(left);
            p.runL();
            direction = left;
            walking = true;
        }
    }

    public void badMove(){//we moved the goombas by giving them a max left and a max right. once they reached a max point, they would change directions and loop back and forth
        for (Goomba b : goombs){
            if (b.getX() == b.getMaxL()){
                b.setDirection(right);//switching directions
            }
            if (b.getX() == b.getMaxR()){
                b.setDirection(left);
            }
            if (b.getDirection() == right){
                b.moveR();//actually moving the goombas
            }
            if (b.getDirection() == left){
                b.moveL();
            }
        }
    }


    public void playerUpdate(){
        p.update();
        if(p.getSy() == 0){//if the player is not accelerating upwards or downwards
            midAir = false;//on the goround
            p.resetCurrentF();
        }
        if (p.getX() - offset < 20){//making sure the player can't run off the screen on the left
            p.setX(20 + offset);
        }
    }

    public void badUpdate(){//updating the goombas to move
        for (Goomba b : goombs){
            b.update();
        }
    }

    public void checkRun(){//checking if the footstep sound can be played

        if (walking && p.getSy() < 1 && !falling && !midAir){//this checks to see if the player is on a surface
            playRun = true;
        }
        else{
            playRun = false;
        }
    }

    public void playRun(){//playing the footstep sound
        if (playRun) {
            for (Platform plat : plats) {
                if (!midAir) {
                    if (plat.getRect().intersects(p.getRect())) {
                        if (p.getRect().y - p.getSy() + p.getHeight() <= plat.getY()) {/*this operation checks the frame before the current one. i subtract the player's current vertical
                            speed from the y position. i then check if the frame before the current one is above any platform to decide whether or not to play the footstep sound.
                            I also add the player's height because I want to make sure i'm checking for their feet and not their head*/
                            if (walking && p.getSy() < 1 && !falling && !midAir) {
                                if (plat.platType().equals("long") || plat.platType().equals("air")) {//we have two different footstep sounds, one for the grass, one for everything else
                                    runLeaf.play();
                                    playRun = false;
                                } else {
                                    run.play();
                                }
                            }
                        }
                    }

                }
            }
        }
        else{
            run.stop();
            runLeaf.stop();
        }
    }


    public void checkCollisions(){
        for(Platform plat : plats){//checking collision for each platform in the arraylist
            if (plat.getRect().intersects(p.getRect())){
                falling = false;
                if (p.getRect().y-p.getSy()+p.getHeight() <= plat.getY()){//checking to make sure that the player is above the platform in order to land on it
                    p.setSy(0);//because the player is on a platform, the speed in the y component is zero
                    p.setY(plat.getRect().y-55);//setting the y position to be ontop of the platform
                    midAir = false;
                }
            }
        }

        for (int i=0; i < goombs.size(); i++){
            if (goombs.get(i).checkDead()) {//adding the dead goombas to the dead goombas array list so that they can be removed from the level
                gDead.add(goombs.get(i));
            }
        }

        for (int i = 0; i < gDead.size(); i++){
            if (gDeadCount % 50 == 0){//this counter is used to animate the death of the goombas
                gDeadRemove.add(gDead.get(i));
                gDeadCount = 0;
                p.addScore(30);
            }
        }

        for (Goomba bad : goombs){//checking if the player hits any goombas
            if (p.getRect().intersects(bad.getRect()) && !attack){//making sure that they are intersecting and also not attacking
                p.setHit(true);
                lifeCounter++;//counter used to make sure that the player doesn't lose all his lives when hit by an enemy
                oof.play();//playing the getting hit sound
            }
        }


        for (Goomba bad : goombs) {
            if(hitBadGuy){//checking if the player has hit any goombas
                if (direction == right){
                    if (bad.getRect().intersects(p.getRHitRect())){//i check the intersection on the player's hitRect which is just a rectangle that is slightly larger because of the length of the fist/kick when he attacks
                        bad.setHit(true);
                        bad.setDrawHitPic(true);//drawing the indicator that the goomba has lost health
                        bad.loseHp(50);
                        hitBadGuy = false;
                    }
                }
                else if (direction == left){
                    if (bad.getRect().intersects(p.getLHitRect())){
                        bad.setHit(true);
                        bad.setDrawHitPic(true);
                        bad.loseHp(50);
                        hitBadGuy = false;
                    }
                }
            }
        }

        if(abobo.getRect().intersects(p.getRect())){
            if(hitBadGuy && !abobo.getAttack()){
                abobo.gotHit();//checking if the player has hit the first boss
            }
            if(!attack && abobo.getAttack()){
                if(abobo.getDirection() == right){
                    if(p.getX() > abobo.getX()){//if the player has been hit by the first boss
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

        for (int i=0; i < coins.size(); i++){//removing the coins from the level when the player grabs them
            if (coins.get(i).getRect().intersects(p.getRect())){
                p.addScore(25);
                coinSound.play();
                cRemove.add(coins.get(i));
            }
        }

        for (int i = pLives.size()-1; i > -1; i--){
            if (p.checkHit()){
                if (lifeCounter == 1){//using the counter so that the player only loses one life when hit by an enemy
                    p.loseLife();
                    pLivesRemove.add(pLives.get(i));
                    p.setHit(false);
                }
            }
        }

        for (int i=0; i < lvlLives.size(); i++) {
            if (lvlLives.get(i).getRect().intersects(p.getRect())) {
                //following code ensures that the player doesn't go over 5 lives
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

        if (abobo.getHits() <= 0){
            miniscene2 = true;
            bossBattle = false;//if the player kills the boss
        }
    }

    public void drawAddedLife(int num){//drawing the lives that the player gains from grabbing
        Life tmp = new Life(60*num,-5,16);
        pLives.add(tmp);
    }

    public void removeGoombs(){//removing initiating the dead frames to play for the goombas
        for (int i = 0; i < gDead.size(); i++){
            goombs.remove(gDead.get(i));
        }
    }

    public void removeDGoombs(){//removing the goombas from the level once they have finished their death animation
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

    public boolean checkFinish(){
        return finish;
    }

    public void loadSprite(){//loading the sprites in from the frames
		loadSprite(runRight, runLeft, "Ryan Funyanjiwan/Run/run");
		loadSprite(idleRight, idleLeft, "Ryan Funyanjiwan/Idle/idle");
		loadSprite(kickRight, kickLeft, "Ryan Funyanjiwan/Side Kick/side kick");
		loadSprite(punchRight, punchLeft, "Ryan Funyanjiwan/Punch/punch");
		loadSprite(uppercutRight, uppercutLeft, "Ryan Funyanjiwan/Elbow Upercut/uppercut");
		loadSprite(airPunchRight, airPunchLeft, "Ryan Funyanjiwan/Jump Punch/jump punch");
		loadSprite(gotHitR, gotHitL, "Ryan Funyanjiwan/Gets Hit/hit");
		loadSprite(badBulletR, badBulletL, "bulletFrames/ice/tile");
        loadSprite(fireBoom, fi,"fire boom/fire");

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