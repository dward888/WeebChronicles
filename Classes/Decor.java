//Decor.java
//jim ji and edawrd yang
//Class that tracks all the decoration in each level


import javax.swing.*;
import java.awt.*;

public class Decor {
    private Image pic;
    private int x,y;

    public Decor (int x1,int y1, String f){
        pic =  new ImageIcon("decorPics/" + f + ".png").getImage();
        x = x1;
        y = y1;
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    public Image getImage(){
        return pic;
    }

}
