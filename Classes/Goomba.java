//Goomba.java
//jim ji and edward yang
//class that keeps track of the goombas in the levels


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Goomba {

    private int x,y,dist,width,height;
    private double sy,sx;
    private Image pic;
    private Image lHitPic;
    private Image rHitPic;
    private int maxR, maxL;//nums to keep track of the maximum left and right of the goomba

    private int direction;
    private int left = 1;
    private int right = 2;
    private int xAdjust;
    private int yAdjust;
    private int wAdjust;
    private int hAdjust;
    private int dAdjust; //adjustment for dead frames
    private int hp;

    private boolean checkHit;
    private boolean dead;
    private boolean drawHitPic;

    private String type;

    private Rectangle rect;

    public static final double SPEED = 1;

    private Image[]rightF;
    private Image[]leftF;
    private Image[]rightFDead;
    private Image[]leftFDead;
    private int f;

    public Goomba(int x1, int y1, int maxLeft, int maxRight, String file, int xa,int ya, int wa, int ha, int da, int size,int dSize, int h){

        x = x1;
        y = y1;

        maxL = maxLeft;
        maxR = maxRight;
        dist = 0;
        direction = right;
        pic =  new ImageIcon("badPics/" + file + ".png").getImage();
        lHitPic = new ImageIcon("badHitPics/" + file + "HitL.png").getImage();
        rHitPic = new ImageIcon("badHitPics/" + file + "HitR.png").getImage();

        hp = h;
        type = file;

        width = pic.getWidth(null) + wa;
        height = pic.getHeight(null) + ha;

        xAdjust = xa;
        yAdjust = ya;
        wAdjust = wa;
        hAdjust = ha;
        dAdjust = da; //adjustment for the death frames (sometimes the frames would be higher than desired)

        rightF = new Image[size];
        leftF = new Image[size];
        rightFDead = new Image[dSize];
        leftFDead = new Image[dSize];
        loadSprite(rightF,leftF,"badFrames/"+file+"/tile");
        loadSprite(rightFDead,leftFDead,"badFrames/"+file+"Dead/tile");

        drawHitPic = false;

    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
    public int getDAdjust(){
        return dAdjust;
    }
    public void loseHp(int n){
        hp -= n;
    }
    public void setX(int n){
        x = n;
    }
    public void setY(int n){
        y = n;
    }
    public int getDirection(){
        return direction;
    }
    public void setDirection(int n){
        direction = n;
    }
    public int getMaxR(){
        return maxR;
    }
    public int getMaxL(){
        return maxL;
    }
    public Image getFrame() {
        if (f >= (rightF.length-1)*12) {
            f = -1;
        }
        f ++;

        if (direction == right) {
            return rightF[f/12];
        }
        else {
            return leftF[f/12];
        }
    }
    public Image getDeadFrame() {
        if (f >= (rightFDead.length-1)*20) {
            f = -1;
        }
        f ++;
        if (f / 20 != rightFDead.length-1) {
            if (direction == right) {
                return rightFDead[f / 20];
            } else {
                return leftFDead[f / 20];
            }
        }
        else{
            return null;
        }
    }

    public void move(double xDelta, double yDelta){
        x += xDelta;
        y += yDelta;
    }
    public void accelerate(double accelX, double accelY){
        sx += accelX;
        sy += accelY;
    }
    public void update(){
        move(sx,sy);
        if (y > 590){
            sy = 0;
            y = 590;
        }
        if (hp <= 0){
            dead = true;
        }
    }



    public boolean checkDead(){
        return dead;
    }
    public boolean drawHitPic(){
        return drawHitPic;
    }
    public void setDrawHitPic(boolean n){
        drawHitPic = n;
    }

    public void moveR(){
        move(SPEED, 0);
    }
    public void moveL(){
        move(-SPEED, 0);
    }
    public Rectangle getRect(){
        return new Rectangle(x+xAdjust,y+yAdjust,width,height);
    }
    public Image getRHitImage(){
        return rHitPic;
    }
    public Image getLHitImage(){
        return lHitPic;
    }

    public void setHit(boolean n){
        checkHit = n;
    }

    public boolean checkHit(){
        return checkHit;
    }

    public void loadSprite(Image[]actionRight, Image[]actionLeft, String directory){
        try{
            for(int i = 0; i < actionRight.length; i++) {
                Image img = ImageIO.read(new File(directory + i + ".png"));
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

