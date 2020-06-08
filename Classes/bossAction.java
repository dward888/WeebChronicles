//bossAction.java
//Jim Ji and Edward Yange
//This class loads in all the images of the actions of the boss and takes in account of how much the action will
//move the boss. It also will output its frames

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class bossAction {
    //Image Arrays - Frames
    private Image[]framesRight;
    private Image[]framesLeft;
    private boolean done; //variable used to signal that the action was completed
    private int frameDivide; //used to as a buffer so that the frame is not changed with every loop
    //variables used to move the boss while its doing this specific action
    private int moveX;
    private int moveY;
    private int currentF; //variable used to get the desired frame
    //directional variables
    private int right;
    private int left;
    private int length;

    public bossAction(String directory, int len, int fDivide, int mX, int mY){
        framesRight = new Image[len];
        framesLeft = new Image[len];
        length = len;
        loadSprite(framesRight, framesLeft, directory);
        done = false;
        frameDivide = fDivide;
        moveX = mX;
        moveY = mY;
        currentF = -1;
        left = 2;
        right = 1;
    }
    //method used to get the frames of the action
    public Image getFrame(int direction){
        currentF++;
        if(direction == left) {
            //following code resets the currentF variable once it reaches the length of the array
            if (currentF >= (framesLeft.length - 1) * frameDivide) {
                resetCurrentF();
                done = true;
            }
            return framesLeft[currentF / frameDivide];
        }
        //following code resets the currentF variable once it reaches the length of the array
        else{
            if(currentF >= (framesRight.length - 1) * frameDivide){
                resetCurrentF();
                done = true;
            }
            return framesRight[currentF / frameDivide];
        }
    }
    //Following method returns the movement value, and is negative according to the direction of the boss
    public int getmX(int direction){
        if(direction == left){
            return 0 - moveX;
        }
        else{
            return moveX;
        }
    }
    //Following code returns the movement value
    public int getmY(){
        return moveY;
    }
    //Following method resets the currentF variable to be used for the next time
    public void resetCurrentF(){ currentF = 0; }
    //Following method resets the done variable to be used for the next time
    public void resetMove(){done = false;}
    public boolean isDone(){return done;}
    public int getLen(){return length;};
    //Following code loads in frames and flips them as well
    public void loadSprite(Image[]actionRight, Image[]actionLeft, String directory){
        try{
            for(int i = 0; i < actionRight.length; i++) {
                Image img = ImageIO.read(new File(directory +  i + ".png")); //reads in from file
                actionRight[i] = img;
                actionLeft[i] = flipImage(img);
            }
        }
        catch(IOException e ){
            e.printStackTrace();
        }
    }
    //Following code flips/mirrors an image - code found online
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
