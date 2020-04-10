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
            loadSprite(runRight, runLeft, "run", name + "/run/");
            standing = new ImageIcon(name + "/standing.gif").getImage();
    }
    public void loadSprite(Image[]actionRight, Image[]actionLeft, String motion, String directory){
        try{
            for(int i = 1; i < actionRight.length + 1; i++) {
                Image img = ImageIO.read(new File(directory + motion + i + ".gif"));
                actionRight[i-1] = img;
                //Following code mirrors the image
                AffineTransform mirror = AffineTransform.getScaleInstance(-1, 1);
                mirror.translate(- img.getWidth(null), 0);
                AffineTransformOp mirrorOp = new AffineTransformOp(mirror, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
                img = mirrorOp.filter((BufferedImage) img, null);
                actionLeft[i-1] = img;
            }
        }
        catch(IOException e ){
            e.printStackTrace();
        }
    }
    public Image getStanding(){return standing;}
    public Image getRunLeft(int n){ return runLeft[n];}
    public Image getRunRight(int n){return runRight[n];}
}
