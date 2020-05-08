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
    private Rectangle rect;
    private Font font;
    private Font title;
    private Font littleFont;
    private boolean loaded;
    private boolean start;
    private boolean click;
    private Image[]load;
    private Image[]fastCity;
    private Image redStart;
    private Image grayStart;

    public startScreen(WeebChronicles m) throws IOException, FontFormatException{
        mainFrame = m;
        font = Font.createFont(Font.TRUETYPE_FONT, new File("newyorkescape.ttf"));
        title = new Font(font.getFontName(), Font.PLAIN, 50);
        littleFont = new Font(font.getFontName(), Font.PLAIN, 10);
        frame = 3;
        start = false;
        loaded = false;
        click = false;
        rect = new Rectangle(910, 525, 200, 86);
        load = loadSprite("loading/loading", 48);
        fastCity = loadSprite("city going fast/city", 30);
        redStart = new ImageIcon("Pictures/start_red.png").getImage();
        grayStart = new ImageIcon("Pictures/start_black.png").getImage();
        addMouseListener(this);
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
        PointerInfo point = MouseInfo.getPointerInfo();
        Point mousePos = point.getLocation();
        mx = (int) mousePos.getX();
        my = (int) mousePos.getY();
        if(!start) {
            if (frame % 3 == 0) {
                g.drawImage(fastCity[frame / 3], 0, 0, null);
            }
            g.drawImage(grayStart, 910,500,null);
            if(rect.contains(mousePos)){
                g.drawImage(redStart, 910, 500, null);
                if(click){
                    frame = 0;
                    start = true;
                }
            }
            g.setColor(Color.white);
            g.setFont(title);
            g.drawString("The Weeb Chronicles", 225,70);
            //g.drawString("Chronicles",30, 100);
            g.setFont(littleFont);
            g.drawString(" Created By the Vincent Massey Anime Association",30,600);

            frame++;
            if (frame == 90) {
                frame = 3;
            }
        }
        if(start){
            if(frame % 10 == 0){
                g.drawImage(load[frame / 10], 0, 0, null);
            }
            frame++;
            if(frame == 480){
                frame = 0;
                loaded = true;
            }
        }
        click = false;
    }
    public boolean getLoaded(){
        return loaded;
    }
    @Override
    public void mouseClicked(MouseEvent e) { }
    @Override
    public void mousePressed(MouseEvent e) {
        click = true;
    }
    @Override
    public void mouseReleased(MouseEvent e) { }
    @Override
    public void mouseEntered(MouseEvent e) {
    }
    @Override
    public void mouseExited(MouseEvent e) { }
}

