//Shooter.java
//Edward yand and Jim Ji
//Class that tracks all the shooters in the levels


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Shooter {
    private int x,y,dist,width,height;
    private double sy,sx;
    private Image pic;
    private Image lHitPic;
    private Image rHitPic;
    private int maxHeight, minHeight;

    private int direction;
    private int left = 1;
    private int right = 2;
    private int hp;

    private boolean checkHit;
    private boolean dead;
    private boolean drawHitPic;
    private boolean canShoot;

    private String type;

    private Rectangle rect;

    public static final double FRICTION = 0.99;
    public static final double GRAVITY = 0.4;
    public static final double SPEED = 1;

    private Image[]rightF;
    private Image[]leftF;
    private int f;

    public Shooter(int x1, int y1, int dist, String file, int size, int h){

        x = x1;
        y = y1;

        maxHeight = y-dist;
        minHeight = y+dist;
        pic =  new ImageIcon("badPics/" + file + ".png").getImage();
        lHitPic = new ImageIcon("badHitPics/" + file + "HitL.png").getImage();
        rHitPic = new ImageIcon("badHitPics/" + file + "HitR.png").getImage();

        hp = h;
        type = file;

        width = pic.getWidth(null);
        height = pic.getHeight(null);


        rightF = new Image[size];
        leftF = new Image[size];
        loadSprite(leftF,rightF,"badFrames/"+file+"/tile");
        //loadSprite(rightFDead,leftFDead,"badFrames/"+file+"Dead/tile");

        drawHitPic = false;
        canShoot =false;
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
    public int getHp(){
        return hp;
    }
    public void loseHp(int n){
        hp -= n;
    }
    public void addHp(int n){
        hp += n;
    }
    public double getSy(){
        return sy;
    }
    public double getSx(){
        return sx;
    }
    public int getDist(){
        return dist;
    }
    public void setDist(int n){
        dist = n;
    }
    public void setSx(double n){
        sx = n;
    }
    public void setSy(double n){
        sy = n;
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
    public int getMaxH(){
        return maxHeight;
    }
    public int getMinH(){
        return minHeight;
    }
    public Image getFrame() {
        if (f >= (rightF.length-1)*12) {
            f = -1;
        }

        f ++;

        return leftF[f/12];

    }

    public String getType(){
        return type;
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
        //accelerate(0, GRAVITY);
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
    public void setShoot(boolean n){
        canShoot = n;
    }
    public boolean checkShoot(){
        return canShoot;
    }

    public void moveD(){
        move(0, SPEED);
    }
    public void moveU(){
        move(0, -SPEED);
    }
    public Rectangle getRect(){
        return new Rectangle(x,y,width,height);
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

