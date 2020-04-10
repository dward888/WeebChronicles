import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.*;


public class Character {
    private String name;
    private int attack, defense, speed;
    private Image[]runRight;
    private Image[]runLeft;

    public Character(String n, int a,  int d, int s){
        name = n;
        attack = a;
        defense = d;
        speed = s;
        runRight  = new Image[6];
        runLeft = new Image[6];
    }
    public void loadSprite(Image[]actionRight, Image[]actionLeft, String motion, String directory) {
        try{
            for(int i = 0; i < actionRight.length; i++) {
                Image img = ImageIO.read(new File(directory + motion + i + ".gif"));
                actionRight[i] = img;
            }
        }
        catch(IOException e ){
            e.printStackTrace();
        }
    }
}
