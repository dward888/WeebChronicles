import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.*;
import javax.swing.*;

public class Character {
    private String name;
    private int attack, defense, speed;
    private Image[]run;

    public Character(String n, int a,  int d, int s){
        name = n;
        attack = a;
        defense = d;
        speed = s;
        run  = new Image[6];
    }
    public void loadSprite(Image[]action, String motion, String directory) {
        try{
            for(int i = 0; i < action.length; i++) {
                Image img = ImageIO.read(new File(directory + motion + i + ".gif"));
                action[i] = img;
            }
        }
        catch(IOException e ){
            e.printStackTrace();
        }
    }
}
