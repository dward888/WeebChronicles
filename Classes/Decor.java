import javax.swing.*;
import java.awt.*;

public class Decor {
    //private Rectangle rect;
    private Image pic;
    private int x,y;
    //private int adjustment;



    public Decor (int x1,int y1, String f){
        pic =  new ImageIcon("decorPics/" + f + ".png").getImage();
        x = x1;
        y = y1;
        //rect = new Rectangle(x1,y1,pic.getWidth(null), pic.getHeight(null));
        //adjustment = a;
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
