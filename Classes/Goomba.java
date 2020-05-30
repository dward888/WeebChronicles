import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Goomba {
    private int x,y,dist;
    private double sy,sx;
    private Image pic;
    private int maxR, maxL;

    private int direction;
    private int left = 1;
    private int right = 2;
    private int adjustment;

    private Rectangle rect;

    public static final double FRICTION = 0.99;
    public static final double GRAVITY = 0.4;
    public static final double SPEED = 1;

    private Image[]rightF;
    private Image[]leftF;
    private int f;

    public Goomba(int x1, int y1, int maxLeft, int maxRight, String file, int a, int size){

        x = x1;
        y = y1;
        maxL = maxLeft;
        maxR = maxRight;
        adjustment = a;
        dist = 0;
        direction = right;
        pic =  new ImageIcon("badPics/" + file + ".png").getImage();
        rect = new Rectangle(x1,y1,pic.getWidth(null),pic.getHeight(null));//pic.getWidth(null), pic.getHeight(null));


        rightF = new Image[size];
        leftF = new Image[size];
        loadSprite(rightF,leftF,"badFrames/"+file+"/tile");

    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getAdjust(){
        return adjustment;
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
    public int getMaxR(){
        return maxR;
    }
    public int getMaxL(){
        return maxL;
    }
    public Image getFrame() {

        if (f >= (rightF.length-1)*10) {
            f = -1;

        }

        f ++;



        if (direction == left) {
            return rightF[f/12];
        }
        else {
            return leftF[f/12];
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
        //accelerate(0, GRAVITY);
        if (y > 590){
            sy = 0;
            y = 590;
        }
    }
    public void moveR(){
        move(SPEED, 0);
    }
    public void moveL(){
        move(-SPEED, 0);
    }
    public Rectangle getRect(){
        return rect;
    }
    public Image getImage(){
        return pic;
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

