import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.io.*;
import javax.imageio.*;
import javax.sound.midi.*;
import java.applet.*;

public class selectScreen extends JPanel{
    private WeebChronicles mainFrame;
    private Image[] background;
    private int frame;
    private boolean characterSelected;

    public selectScreen(WeebChronicles m) {
        mainFrame = m;
        background = loadSprite();
        frame = 0;

    }

    public void paintComponent(Graphics g){
        g.drawImage(background[frame], 0,0,null);
        frame++;
    }

    public Image[] loadSprite(){
        Image[]b = new Image[145];
        try{
            for(int i = 0; i < 145; i++) {
                System.out.println(i);
                Image img = ImageIO.read(new File("SelectScreen Back Frames1/frame" + i + ".png"));
                b[i] = img;
            }
        }
        catch(IOException e ){

            e.printStackTrace();
        }
        return b;
    }
    public boolean checkSelect(){
        return characterSelected;
    }





}