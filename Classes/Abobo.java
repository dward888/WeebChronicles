import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Abobo {
    int currentF;
    int x, y, maxLeft,maxRight;
    int direction, left, right;
    bossAction currentAct;
    bossAction jump;
    bossAction backhand;
    bossAction died;
    bossAction getHit;
    bossAction gutpunch;
    bossAction headbutt;
    bossAction idle;
    bossAction jumpelbow;
    bossAction laugh;
    bossAction thunderclap;
    bossAction walk;
    bossAction[] actions;

    public Abobo(){
        currentF = 0;
        x = 7650;
        y = 485;
        maxLeft = 6720;
        maxRight = 7800;
        left = 1;
        right = 2;
        direction = left;

        jump = new bossAction("Abobo/jump/jump", 1, 1, 1, 1);
        backhand = new bossAction("Abobo/backhand/backhand", 6, 13, 0, 0);
        died = new bossAction("Abobo/died/blownback", 18, 7,0,1);
        getHit = new bossAction("Abobo/gets hit/gets hit", 4, 12,0,0);
        gutpunch = new bossAction("Abobo/gut punches/gutpunches", 19,8,0,1);
        headbutt = new bossAction("Abobo/headbutt/headbutt", 11, 10,0,1);
        idle = new bossAction("Abobo/idle/idle", 10,8,0,0);
        jumpelbow = new bossAction("Abobo/jump elbow/jump elbow", 12, 1,1,1);
        laugh = new bossAction("Abobo/laugh/laugh", 16,11,0,0);
        thunderclap = new bossAction("Abobo/thunder clap/thunderclap",13,8,0,1);
        walk = new bossAction("Abobo/walking/walk",12,5,2,0);

        actions = new bossAction[]{jump, backhand, died, getHit, gutpunch, headbutt, idle, jumpelbow, laugh, thunderclap, walk};
        currentAct = laugh;
    }
    public Image getFrame(){
        if(direction == left){
            if(x + currentAct.getmX(direction) <= maxLeft){
                direction = right;
            }
        }
        if(direction == right){
            if(x + currentAct.getmX(direction) >= maxRight){
                direction = left;
            }
        }
        return currentAct.getFrame(direction);
    }
    public void getNextAction(){
        currentAct = actions[randint(0, actions.length - 1)];
        currentAct.resetCurrentF();
    }
    public int getX(){
        x += currentAct.getmX(direction);
        return x;
    }
    public int getY(){
        return y;
    }
    public void moveX(int n){
        x += n;
    }
    public int randint(int low, int high){
        return(int)(Math.random()*(high-low+1)+low);
    }
}
