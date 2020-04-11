import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.*;
import javax.swing.*;


public class Character {
    private String name;
    private int attack, defense, speed;
    private Image[]runRight;
    private Image[]runLeft;
    private Image standing;
    private Image jumpStartLeft;
    private Image jumpStartRight;
    private Image jumpLeft;
    private Image jumpRight;
    private Image fallStartLeft;
    private Image fallStartRight;
    private Image fallLeft;
    private Image fallRight;

    public Character(String n, int a,  int d, int s){
        name = n;
        attack = a;
        defense = d;
        speed = s;
        runRight  = new Image[6];
        runLeft = new Image[6];
        loadSprite();
    }
    public void loadSprite(){
            loadSprite(runRight, runLeft, "run", name + "/run/", ".gif");
            jumpStartRight = new ImageIcon(name + "/jump/jump1.png").getImage();
            jumpStartLeft = flipImage(jumpStartRight);
            jumpRight = new ImageIcon(name + "/jump/jump2.png").getImage();
            jumpLeft = flipImage(jumpRight);
            fallStartRight = new ImageIcon(name + "/fall/fall1.png").getImage();
            fallStartLeft = flipImage(fallStartRight);
            fallRight = new ImageIcon(name + "/fall/fall2.png").getImage();
            fallLeft = flipImage(fallRight);
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
    public Image getJumpLeft() {return jumpLeft;}
    public Image getJumpRight(){return jumpRight;}
    public Image getJumpStartLeft() {return jumpStartLeft;}
    public Image getJumpStartRight(){return jumpStartRight;}
    public Image getFallLeft(){return fallLeft;}
    public Image getFallRight(){return fallRight;}
    public Image getFallStartLeft(){return fallStartLeft;}
    public Image getFallStartRight(){return fallStartRight;}
}
