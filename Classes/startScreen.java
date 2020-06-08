//startScreen.java
//Jim Ji and Edward Yang
//This class is the panel with all the graphics for the starting screen of the game.

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;

public class startScreen extends JPanel implements MouseListener{
    private WeebChronicles mainFrame;
    private int frame = 0; //used to
    private Rectangle rect; //Rect to check collision with the start button

    //Fonts
    private Font font;
    private Font title;
    private Font littleFont;

    private boolean loaded; //checks if the loading screen is done so that the cut scene can start
    private boolean start; //checks if the start button was pressed
    private boolean click; //used to see if the mouse was pressed

    //Images and Frames
    private Image[]load;
    private Image[]fastCity;
    private Image redStart;
    private Image grayStart;


    public startScreen(WeebChronicles m){
        mainFrame = m;
        //Following code loads the fonts
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("font/newyorkescape.ttf"));
            title = font.deriveFont(50f);
            littleFont = font.deriveFont(10f);
        }
        catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
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
    //Following code loads in images from a file and adds them all to a Image array
    public Image[] loadSprite(String fileName, int len){
        Image[]b = new Image[len];
        try{
            for(int i = 0; i < len; i++) {
                Image img = ImageIO.read(new File(fileName + i + ".png")); //reads from a file
                b[i] = img;
            }
        }
        catch(IOException e ){
            e.printStackTrace();
        }
        return b;
    }
    public void paintComponent(Graphics g){
        //Following code gets the coordinates of the mouse
        Point mousePos = getMousePosition();
        if(mousePos == null){
            mousePos = new Point(0,0);
        }
        if(!start) { //When the start button has not been clicked
            if (frame % 3 == 0) {
                //The picture that is drawn is changed to the next frame every 3 loops
                g.drawImage(fastCity[frame / 3], 0, 0, null); //background sprite
            }
            //Following code is draws and checks if the start button is clicked
            g.drawImage(grayStart, 910,500,null);
            if(rect.contains(mousePos)){
                g.drawImage(redStart, 910, 500, null);
                if(click){
                    start = true;
                }
            }
            //Title
            g.setColor(Color.white);
            g.setFont(title);
            g.drawString("The Weeb Chronicles", 225,70);
            g.setFont(littleFont);
            g.drawString(" Created By the Vincent Massey Anime Association",30,600);

            frame++;
            //Following code resets the frame value when the all frames have been drawn
            if (frame == fastCity.length * 3) {
                frame = 3;
            }
        }
        if(start){ //when the start button is clicked
            if(frame % 10 == 0){
                g.drawImage(load[frame / 10], 0, 0, null); //loading screen
            }
            frame++;
            //Following code resets the frame value when the all frames have been drawn
            if(frame == load.length * 10){
                frame = 0;
                loaded = true;
            }
        }
        click = false;
    }
    //Following function is used to change the start sceen to the firt cut scene
    public boolean getLoaded(){
        return loaded;
    }

    //Following methods are related to mouse input
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

