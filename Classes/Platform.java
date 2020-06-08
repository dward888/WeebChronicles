//Platform.java
//Jim Ji and Edward Yang
//Class that tracks the position of the platforms


import javax.swing.*;
import java.awt.*;

public class Platform {
    private Rectangle rect;
    private Image pic;
    private int adjustment;
    private String f;



    public Platform(int x1,int y1, String file, int a){
        pic =  new ImageIcon("platPics/" + file + ".png").getImage();
        rect = new Rectangle(x1,y1,pic.getWidth(null), pic.getHeight(null));
        adjustment = a;
        f = file;
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

    public String platType(){
        return f;
    }

}
