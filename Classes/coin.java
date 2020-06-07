import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Coin {
    private Rectangle rect;
    private Image pic;
    //private int adjustment;

    private int direction;
    private int left = 1;
    private int right = 2;
    private Image[]rightF;
    private Image[]leftF;
    private int f;


    public Coin(int x1,int y1, String file, int frames){
        pic =  new ImageIcon("Pictures/" + file + ".png").getImage();
        direction = right;
        rect = new Rectangle(x1,y1,pic.getWidth(null), pic.getHeight(null));
        //adjustment = a;
        rightF = new Image[frames];
        leftF = new Image[frames];
        //loadSprite(rightF,leftF,"coinFrames/coin/tile");
        loadSprite(rightF,leftF,"coinFrames/tile");
    }

    public int getX(){
        return rect.x;
    }
    public int getY(){
        return rect.y;
    }

    //public int getAdjust(){
        //return adjustment;
    //}
    public Rectangle getRect(){
        return rect;
    }


    public Image getImage(){
        return pic;
    }

    public Image getFrame() {

        if (f >= (rightF.length-1)*5) {
            f = -1;

        }

        f ++;



        if (direction == left) {
            return rightF[f/5];
        }
        else {
            return leftF[f/5];
        }
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
