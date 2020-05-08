import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import javax.imageio.*;

public class selectScreen extends JPanel implements MouseListener {
    private WeebChronicles mainFrame;
    private Image[] background;
    private int frame;
    private boolean characterSelected;
    private Rectangle rect;
    private Image redStart;
    private Image grayStart;
    private boolean click;
    private int mx;
    private int my;

    public selectScreen(WeebChronicles m) {
        mainFrame = m;
        background = loadSprite();
        frame = 0;
        characterSelected = false;
        click = false;
        rect = new Rectangle(910, 525, 200, 86);
        redStart = new ImageIcon("Pictures/start_red.png").getImage();
        grayStart = new ImageIcon("Pictures/start_black.png").getImage();
        addMouseListener(this);
    }
    public void addNotify() {
        super.addNotify();
        requestFocus();
    }
    public void paintComponent(Graphics g){
        PointerInfo point = MouseInfo.getPointerInfo();
        Point mousePos = point.getLocation();
        mx = (int) mousePos.getX();
        my = (int) mousePos.getY();
        g.drawImage(background[frame], 0,0,null);
        frame++;
        if(frame == 144){
            frame = 0;
        }
        g.drawImage(grayStart, 910,500,null);
        if(rect.contains(mousePos)){
            g.drawImage(redStart, 910, 500, null);
            if(click){
                characterSelected = true;
            }
        }
        click = false;
    }

    public Image[] loadSprite(){
        Image[]b = new Image[145];
        try{
            for(int i = 0; i < 145; i++) {
                Image img = ImageIO.read(new File("SelectScreen Back Frames/frame" + i + ".png"));
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

    @Override
    public void mouseClicked(MouseEvent e) { }
    @Override
    public void mousePressed(MouseEvent e) {click = true;}
    @Override
    public void mouseReleased(MouseEvent e) { }
    @Override
    public void mouseEntered(MouseEvent e) { }
    @Override
    public void mouseExited(MouseEvent e) { }
}
