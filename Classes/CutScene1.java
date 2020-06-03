import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CutScene1 extends JPanel {
    private WeebChronicles mainFrame;
    private int frame;
    private int fCount1;
    private int fCount2;
    private boolean watched;

    private Font font;
    private Font initialFont;
    private Font weebFont;
    private Font smallerFont;
    private Font miniFont;

    private Image city;
    private Image textBubble;
    private Image yellBubble;
    private Image forest;
    private Image suprised;
    private Image ryanStand;
    private Image maiStand;
    private Image maiSuprised;
    private Image scream;
    private Image cage;
    private Image dragonLow;
    private Image dragonHigh;
    private Image plat;
    private Image door;
    private Image[]road;
    private Image[]ryanRun;
    private Image[]maiRun;
    private Image[]fireBoom;
    private Image[]dazed;

    public CutScene1(WeebChronicles m){
        mainFrame = m;
        frame = 0;
        fCount1 = 0;
        fCount2 = 0;
        watched = false;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("font/newyorkescape.ttf"));
            initialFont = font.deriveFont(35f);
            weebFont = font.deriveFont(90f);
            smallerFont = font.deriveFont(20f);
            miniFont = font.deriveFont(13f);
        }
        catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        city = new ImageIcon("Pictures/city.jpg").getImage();
        textBubble = new ImageIcon("Pictures/text bubble.png").getImage();
        yellBubble = new ImageIcon("Pictures/yelling bubble.png").getImage();
        forest = new ImageIcon("Pictures/forestBack.png").getImage();
        suprised = new ImageIcon("Ryan Funyanjiwan/Gets Hit/hit8.png").getImage();
        ryanStand = new ImageIcon("Ryan Funyanjiwan/standing.png").getImage();
        maiStand = new ImageIcon("Mai-san/standing.png").getImage();
        maiSuprised = new ImageIcon("Mai-san/suprised.png").getImage();
        scream = new ImageIcon("Pictures/scream bubble with no arm.png").getImage();
        cage = new ImageIcon("Pictures/cage.png").getImage();
        dragonHigh = new ImageIcon("dragon/high stance.png").getImage();
        dragonLow = new ImageIcon("dragon/low stance.png").getImage();
        plat = new ImageIcon("platPics/doorPlat.png").getImage();
        door = new ImageIcon("decorPics/door.png").getImage();
        road = new Image[5];
        ryanRun = new Image[10];
        maiRun = new Image[16];
        fireBoom = new Image[15];
        dazed = new Image[4];
        loadSprite(road, "Road/road");
        loadSprite(ryanRun, "Ryan Funyanjiwan/Run/run");
        loadSprite(maiRun, "Mai-san/Run/run");
        loadSprite(fireBoom, "fire boom/fire");
        loadSprite(dazed, "Ryan Funyanjiwan/Dazed/dazed");
    }
    public void addNotify() {
        super.addNotify();
        requestFocus();
        mainFrame.start();
    }
    public void paintComponent(Graphics g){
        if(frame < 600) {
            g.setColor(Color.black);
            g.fillRect(0, 0, 1200, 650);
        }
        if(frame > 120 && frame < 300){
            g.setColor(Color.white);
            g.setFont(initialFont);
            g.drawString("This is the story of a boy...", 250,305);
        }
        if(frame > 320 && frame < 450){
            g.setColor(Color.white);
            g.setFont(initialFont);
            g.drawString("more specifically...",350,305);
        }
        if(frame > 470 && frame < 600){
            g.setColor(Color.red);
            g.setFont(weebFont);
            g.drawString("A  WEEB",350,315);
        }
        if(frame > 600 && frame < 1320){
            g.drawImage(city,0,0,null);
            g.drawImage(road[fCount1/8],0,570,null);
            g.drawImage(ryanRun[fCount2/4],250,525,null);
            if(fCount1 >= (road.length-1)*8){
                fCount1 = -1;
            }
            if(fCount2 >= (ryanRun.length-1)*4){
                fCount2 = -1;
            }
            fCount1 ++;
            fCount2 ++;
        }
        if(frame > 650 && frame < 1320){
            g.setColor(Color.white);
            g.setFont(smallerFont);
            g.drawString("Ryan Funyanjiwan, a 17 year old school boy,", 20, 35);
        }
        if(frame > 800 && frame < 1320){
            g.drawString("has lived a life filled with misfortune.", 20, 70);
        }
        if(frame > 950 && frame < 1320){
            g.drawString("He ran away, cursed the 3d world and retreated into the 2d world of anime.", 20, 105);
        }
        if(frame > 970 && frame < 1320){
            g.drawImage(flipImage(textBubble), 235,370,null);
            g.setColor(Color.black);
            g.setFont(initialFont);
            g.drawString("#*@^%$", 355,450);
        }
        if(frame == 1320){
            fCount1 = 0;
            fCount2 = 0;
        }
        if(frame > 1320){
            g.setColor(Color.white);
            g.fillRect(0,0,1200, 650);
            g.setColor(Color.black);
            g.setFont(initialFont);
            if(frame < 1490){
                g.drawString("But something unexpected happened...", 110, 305);
            }
        }
        if(frame > 1510 && frame < 1660){
            g.drawString("The anime world in his mind...", 220, 305);
        }
        if(frame > 1680 && frame < 1830){
            g.setColor(Color.red);
            g.setFont(weebFont);
            g.drawString("Manifested", 240, 315);
        }
        if(frame > 1830 && frame < 6325) {
            g.drawImage(forest, 0, 0, null);
            if (frame < 2100){
                g.drawImage(dazed[fCount2 / 25], 260, 530, null);
                if (fCount2 >= (dazed.length - 1) * 25) {
                    fCount2 = -1;
                }
                fCount2++;
            }
            if(frame < 1940) {
                g.drawImage(fireBoom[fCount1/8], 190,370,null);
                if (fCount1 >= (fireBoom.length - 1) * 8) {
                    fCount1 = -1;
                }
                fCount1++;
            }
        }
        if(frame > 1920 && frame < 2200){
            g.setColor(Color.white);
            g.setFont(smallerFont);
            g.drawString("Ryan was brought into a mystical land.",20, 35);
        }
        if(frame == 2100){
            fCount1 = 0;
            fCount2 = 1200;
        }
        if(frame > 2100 && frame < 2729){
            g.drawImage(suprised, 260, 520, null);
            if(frame < 2300) {
                g.drawImage(flipImage(yellBubble), 280, 250, null);
                g.setColor(Color.black);
                g.setFont(smallerFont);
                g.drawString("HUH?!?", 330, 360);
                g.drawString("WHERE AM I?!?", 330, 395);
            }
        }
        if(frame > 2200 && frame < 2300){
            g.drawImage(flipImage(maiRun[fCount1/2]), fCount2,545,null);
            if(fCount1 >= (maiRun.length-1)*2){
                fCount1 = -1;
            }
            fCount1++;
            fCount2-=4;
        }
        if(frame == 2300){
            fCount1 = 0;
            fCount2 = -170;
        }
        if(frame > 2299 && frame < 3995){
            g.drawImage(flipImage(maiStand), 800,530,null);
            if(frame < 2760) {
                g.setColor(Color.black);
                g.setFont(miniFont);
                g.drawImage(textBubble, 480, 380, null);
                g.drawString("I heard a loud scream.", 565, 430);
                g.drawString("Are you ok?", 570, 450);
                g.drawString("Are you hurt?", 570, 470);
            }
        }
        if(frame > 2430 && frame < 2880){
            g.setColor(Color.white);
            g.setFont(smallerFont);
            g.drawString("Ryan was greeted by a girl", 20, 35);
        }
        if(frame > 2530 && frame < 2880){
            g.drawString("As expected...", 20,70);
        }
        if(frame > 2630 && frame < 2880){
            g.drawString("Ryan immediately fell in love with her.", 20, 105);
        }
        if(frame > 2730 && frame < 3995){
            g.drawImage(ryanStand, 260, 520, null);
        }
        if(frame > 2780 && frame < 3100){
            g.setColor(Color.black);
            g.setFont(miniFont);
            g.drawImage(flipImage(textBubble),220, 380,null);
            if(frame < 2930) {
                g.drawString("I'm fine, just surprised.", 300, 455);
            }
        }
        if(frame > 2950 && frame < 3100){
            g.drawString("I do seem to be", 340, 445);
            g.drawString("lost though.", 340, 465);
        }
        if(frame > 3120 && frame < 3610){
            g.setColor(Color.black);
            g.setFont(miniFont);
            g.drawImage(textBubble, 480, 380, null);
            if(frame < 3270) {
                g.drawString("Hmmm, I see...", 615, 455);
            }
        }
        if(frame > 3290 && frame < 3440){
            g.drawString("I'll help you", 605, 445);
            g.drawString("out with that.", 605, 465);
        }
        if(frame > 3460 && frame < 3610){
            g.drawString("My name's Mai.", 595, 445);
            g.drawString("Let's be friends!!", 595, 465);
        }
        if(frame > 3630 && frame < 3760){
            g.setColor(Color.black);
            g.setFont(miniFont);
            g.drawImage(flipImage(textBubble),220, 380,null);
            g.drawString("My name's Ryan,", 350,435);
            g.drawString("and i'd love to", 350,455);
            g.drawString("be friends :)", 350,475);
        }
        if(frame > 3780 && frame < 3930){
            g.drawImage(scream, 700,70, null);
            g.setColor(Color.black);
            g.setFont(initialFont);
            g.drawString("NOT SO", 750, 175);
            g.drawString("FAST!!", 765, 210);
        }
        if(frame > 3950 && frame < 3985){
            g.drawImage(cage,760,fCount1,null);//y500
            g.drawImage(dragonLow, 820,fCount2, null);
            fCount1+=15;
            fCount2+=15;
        }
        if(frame == 4000){
            fCount1 = 0;
            fCount2 = 0;
        }
        if(frame > 3995 && frame < 5830){
            if(frame < 5550) {
                g.drawImage(maiSuprised, 785, 530, null);
            }
            g.drawImage(suprised, 260, 520, null);
        }
        if(frame > 3982 && frame < 5550){
            g.drawImage(cage, 760, 500,null);
            g.drawImage(dragonHigh, 820, 350,null);
        }
        if(frame > 4050 && frame < 4200){
            g.drawImage(yellBubble, 520,200,null);
            g.setColor(Color.black);
            g.setFont(smallerFont);
            g.drawString("It is I,", 600, 310);
            g.setColor(Color.red);
            g.drawString("Draconius!!!!", 600,340);
        }
        if(frame > 4220 && frame < 4940){
            g.drawImage(textBubble, 500,320,null);
            g.setFont(miniFont);
            if(frame < 4370) {
                g.drawString("You don't smell", 610, 380);
                g.drawString("of this world, boy", 610, 400);
            }
        }
        if(frame > 4390 && frame < 4540){
            g.setFont(miniFont);
            g.drawString("I am thoroughly", 600, 380);
            g.drawString("intrigued.", 600, 400);
        }
        if(frame > 4560 && frame < 4710){
            g.drawString("I am taking this girl.", 600, 390);
        }
        if(frame > 4730 && frame < 4940){
            g.drawString("If you want to save her,",580,380);
            g.drawString("Defeat my henchmen,",600,400);
            g.drawString("and face me head on.",600,420);
        }
        if(frame > 4960 && frame < 5110){
            g.setFont(smallerFont);
            g.drawImage(flipImage(yellBubble), 280, 250, null);
            g.drawString("GIVE HER BACK!!", 320, 380);
        }
        if(frame > 5130 && frame < 5550){
            g.drawImage(textBubble, 500,320,null);
            g.setFont(miniFont);
            if(frame < 5380) {
                g.drawString("Enter the door, boy.", 600, 390);
            }
        }
        if(frame > 5250){
            g.drawImage(plat, 340,440,null);
            g.drawImage(door,395,350,null);
        }
        if(frame > 5250 & frame < 5360){
            g.drawImage(fireBoom[fCount1 / 8], 325, 260, null);
            if(fCount1 >= (fireBoom.length - 1) * 8) {
                fCount1 = -1;
            }
            fCount1++;
        }
        if(frame > 5400 && frame < 5550){
            g.drawString("Prove your worth.", 610, 390);
        }
        if(frame > 5549 && frame < 5600){
            g.fillRect(0,0,1200,650);
        }
        if(frame > 5680 && frame < 5830){
            g.setFont(smallerFont);
            g.drawImage(flipImage(yellBubble), 280, 250, null);
            g.drawString("MAI-SAN!!!", 350, 380);
        }
        if(frame > 5829 && frame < 6000){
            g.drawImage(ryanStand, 260, 520, null);
            g.setFont(miniFont);
            g.drawImage(flipImage(textBubble),220, 380,null);
            g.drawString("I have to save her,", 320, 445);
            g.drawString("no matter what.", 320, 465);
        }
        if(frame > 5999 && frame < 6050){
            g.fillRect(0,0,1200,650);
        }
        if(frame > 6049 && frame < 6325){
            g.drawImage(ryanStand, 410, 390, null);
        }
        if(frame > 6125 && frame < 6325){
            g.setColor(Color.red);
            g.setFont(smallerFont);
            g.drawImage(flipImage(textBubble),370,250,null);
            g.drawString("LET'S DO THIS!!!", 465, 325);
        }
        if(frame > 6324){
            g.fillRect(0,0,1200,650);
        }
        if(frame > 6375){
            g.setColor(Color.white);
            g.setFont(weebFont);
            g.drawString("Controls", 310,85);
        }
        if(frame > 6575){
            watched = true;
        }
        frame ++;
    }
    public boolean finished(){return watched;}
    public void loadSprite(Image[]action, String directory){
        try{
            for(int i = 0; i < action.length; i++) {
                Image img = ImageIO.read(new File(directory +  i + ".png"));
                action[i] = img;
            }
        }
        catch(IOException e ){
            e.printStackTrace();
        }
    }
    public Image flipImage(Image image){
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
