import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.io.*;
import javax.imageio.*;
import javax.sound.midi.*;
import java.applet.*;

public class startScreen extends JPanel{
    private WeebChronicles mainFrame;
    private Image startBack;
    private int f = 0;

    public startScreen(WeebChronicles m) {
        mainFrame = m;

        startBack = new ImageIcon("Pictures/title.png").getImage();
    }
    public void addNotify() {
        super.addNotify();
        requestFocus();
    }
    public void paintComponent(Graphics g){
        g.drawImage(startBack,0,0,null);
        System.out.println("hi");
    }
}

