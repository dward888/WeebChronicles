import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class bossAction {
    private Image[]framesRight;
    private Image[]framesLeft;
    private int frameDivide;
    private int moveX;
    private int moveY;
    private int currentF;
    private int right;
    private int left;

    public bossAction(String directory, int len, int fDivide, int mX, int mY){
        framesRight = new Image[len];
        framesLeft = new Image[len];
        loadSprite(framesRight, framesLeft, directory);
        frameDivide = fDivide;
        moveX = mX;
        moveY = mY;
        currentF = -1;
        left = 1;
        right = 2;
    }
    public Image f(){
        return framesRight[0];
    }
    public Image getFrame(int direction){
        currentF++;
        if(direction == left) {
            if (currentF >= (framesLeft.length - 1) * frameDivide) {
                resetCurrentF();
            }
            return framesLeft[currentF / frameDivide];
        }
        else{
            if(currentF >= (framesRight.length - 1) * frameDivide){
                resetCurrentF();
            }
            return framesRight[currentF / frameDivide];
        }
    }
    public int getmX(int direction){
        if(direction == left){
            return 0 - moveX;
        }
        else{
            return moveX;
        }
    }
    public int getmY(){
        return moveY;
    }
    public void resetCurrentF(){
        currentF = 0;
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
