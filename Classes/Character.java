import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.*;
import javax.swing.*;


public class Character {
    private String name;
    private int attack, defense, speed;

    //Frame Arrays
    private Image[]runRight;
    private Image[]runLeft;
    private Image standing;
    private Image[]jumpRight;
    private Image[]jumpLeft;
    private Image[]fallRight;
    private Image[]fallLeft;



    public Character(String n, int a,  int d, int s, int rightSize, int leftSize){
        name = n;
        attack = a;
        defense = d;
        speed = s;
        runRight  = new Image[rightSize];
        runLeft = new Image[leftSize];
        jumpRight = new Image[2];
        jumpLeft = new Image[2];
        fallRight = new Image[2];//oy0oa
        fallLeft = new Image[2];

        loadSprite();
        //hi
    }
    public void loadSprite(){
            loadSprite(runRight, runLeft, "run", name + "/run/", ".gif");
            loadSprite(jumpRight, jumpLeft, "jump", name + "/jump/", ".png");
            loadSprite(fallRight, fallLeft, "fall", name + "/fall/", ".png");
            standing = new ImageIcon(name + "/standing.gif").getImage();
    }
    public void loadSprite(Image[]actionRight, Image[]actionLeft, String motion, String directory, String type){
        try{
            for(int i = 1; i < actionRight.length + 1; i++) {
                Image img = ImageIO.read(new File(directory + motion + i + type));
                actionRight[i-1] = img;
                actionLeft[i-1] = flipImage(img);
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
        mirror.translate(-bImg.getWidth(null),0);
        AffineTransformOp mirrorOp = new AffineTransformOp(mirror, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        bImg = mirrorOp.filter(bImg, null);
        return bImg;
    }

    //GET METHODS
    public Image getStanding(){return standing;}
    public Image[] getRunLeft(){ return runLeft;}
    public Image[] getRunRight(){return runRight;}
    public Image[] getJumpLeft() {return jumpLeft;}
    public Image[] getJumpRight(){return jumpRight;}
    public Image[] getFallLeft(){return fallLeft;}
    public Image[] getFallRight(){return fallRight;}
}
