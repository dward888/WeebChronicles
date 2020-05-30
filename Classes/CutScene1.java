import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CutScene1 extends JPanel {
    private WeebChronicles mainFrame;
    private int frame;
    private int roadF;
    private int ryanRunF;
    private boolean watched;

    private Font font;
    private Font initialFont;
    private Font weebFont;

    private Image city;
    private Image[]road;
    private Image[]ryanRun;



    public CutScene1(WeebChronicles m) throws IOException, FontFormatException {
        mainFrame = m;
        frame = 0;
        roadF = 0;
        ryanRunF = 0;
        watched = false;
        font = Font.createFont(Font.TRUETYPE_FONT, new File("font/newyorkescape.ttf"));
        initialFont = new Font(font.getFontName(), Font.PLAIN, 35);
        weebFont = new Font(font.getFontName(), Font.PLAIN, 90);
        city = new ImageIcon("Pictures/city.jpg").getImage();
        road = new Image[5];
        ryanRun = new Image[10];
        loadSprite(road, "Road/road");
        loadSprite(ryanRun, "Ryan Funyanjiwan/Run/run");

    }
    public void addNotify() {
        super.addNotify();
        requestFocus();
        mainFrame.start();
    }
    public void paintComponent(Graphics g){
        if(frame < 600) {
            g.setColor(Color.black);
            g.fillRect(0, 0, 1200, 650);
        }
        if(frame > 120 && frame < 300){
            g.setColor(Color.white);
            g.setFont(initialFont);
            g.drawString("This is the story of a boy...", 250,305);
        }
        if(frame > 320 && frame < 450){
            g.setColor(Color.white);
            g.setFont(initialFont);
            g.drawString("more specifically...",350,305);
        }
        if(frame > 470 && frame < 600){
            g.setColor(Color.red);
            g.setFont(weebFont);
            g.drawString("A  WEEB",350,315);
        }
        if(frame > 600){
            g.drawImage(city,0,0,null);
            g.drawImage(road[roadF/8],0,570,null);
            g.drawImage(ryanRun[ryanRunF/4],250,525,null);
            if(roadF >= (road.length-1)*8){
                roadF = -1;
            }
            if(ryanRunF >= (ryanRun.length-1)*4){
                ryanRunF = -1;
            }
            roadF ++;
            ryanRunF ++;
        }
        if(frame == 1500){
            watched = true;
        }
        frame ++;
    }
    public boolean finished(){return watched;}
    public void loadSprite(Image[]action, String directory){
        try{
            for(int i = 0; i < action.length; i++) {
                Image img = ImageIO.read(new File(directory +  i + ".png"));
                action[i] = img;
            }
        }
        catch(IOException e ){
            e.printStackTrace();
        }
    }
    public Image flipImage(Image image) {
        BufferedImage bImg = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g = (Graphics2D) bImg.getGraphics();
        g.drawImage(image, 0, 0, null);
        AffineTransform mirror = AffineTransform.getScaleInstance(-1, 1);
        mirror.translate(-bImg.getWidth(null), 0);
        AffineTransformOp mirrorOp = new AffineTransformOp(mirror, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        bImg = mirrorOp.filter(bImg, null);
        return bImg;
    }
}
