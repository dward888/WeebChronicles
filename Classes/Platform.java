import javax.swing.*;
import java.awt.*;

public class Platform {
    private Rectangle rect;
    private Image pic;
    private int adjustment;



    public Platform(int x1,int y1, String f, int a){
        pic =  new ImageIcon("platPics/" + f + ".png").getImage();
        rect = new Rectangle(x1,y1,pic.getWidth(null), pic.getHeight(null));
        adjustment = a;
    }

    public int getX(){
        return rect.x;
    }
    public int getY(){
        return rect.y;
    }

    public int getAdjust(){
        return adjustment;
    }
    public Rectangle getRect(){
        return rect;
    }

    public Image getImage(){
        return pic;
    }

}
