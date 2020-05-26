import javax.swing.*;
import java.awt.*;

public class Platform {
    private Rectangle rect;
    private Image pic;



    public Platform(int x1,int y1,String f){
        pic =  new ImageIcon("platPics/" + f + ".png").getImage();
        rect = new Rectangle(x1,y1,pic.getWidth(null), pic.getHeight(null));
    }

    public int getX(){
        return rect.x;
    }
    public int getY(){
        return rect.y;
    }

    public Rectangle getRect(){
        return rect;
    }

    public Image getImage(){
        return pic;
    }

}
