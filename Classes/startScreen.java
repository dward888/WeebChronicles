import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;

public class startScreen extends JPanel implements MouseListener{
    private WeebChronicles mainFrame;
    private Image startBack;
    private int mx;
    private int my;
    private int frame = 0;
    private Font font;
    private Font title;
    private boolean loaded;
    private boolean start;
    private Image[]load;
    private Image[]fastCity;
    private Image redStart;
    private Image grayStart;
    private MouseEvent e;

    public startScreen(WeebChronicles m) throws IOException, FontFormatException{
        mainFrame = m;
        font = Font.createFont(Font.TRUETYPE_FONT, new File("newyorkescape.ttf"));
        title = new Font(font.getFontName(), Font.PLAIN, 50);
        frame = 0;
        start = false;
        loaded = false;
        //load = loadSprite("loading/loading", 48);
        fastCity = loadSprite("city going fast/city", 30);

    }
    public void addNotify() {
        super.addNotify();
        requestFocus();
    }
    public Image[] loadSprite(String fileName, int len){
        Image[]b = new Image[len];
        try{
            for(int i = 0; i < len; i++) {
                Image img = ImageIO.read(new File(fileName + i + ".png"));
                b[i] = img;
            }
        }
        catch(IOException e ){
            e.printStackTrace();
        }
        return b;
    }
    public void paintComponent(Graphics g){
        if(!start) {
            if (frame % 5 == 0) {
                g.drawImage(fastCity[frame / 5], 0, 0, null);
                System.out.println(frame/5);
            }
            frame++;
            if (frame == 150) {
                frame = 0;
            }
        }


        //g.setColor(Color.white);
        //g.setFont(title);
        //g.drawString("The Weeb Chronicles", 50,50);

    }

    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) { }
    @Override
    public void mouseReleased(MouseEvent e) { }
    @Override
    public void mouseEntered(MouseEvent e) { }
    @Override
    public void mouseExited(MouseEvent e) { }
}

