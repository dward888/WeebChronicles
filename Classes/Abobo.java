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
        x = 7200;
        y = 500;
        maxLeft = 6720;
        maxRight = 7830;
        left = 1;
        right = 2;
        direction = left;
        /*jump = new bossAction(loadSprite(1, "Abobo/jump/jump"), 1, 1, 1);
        backhand = new bossAction(loadSprite(6,"Abobo/backhand/backhand"), 1, 1, 1);
        died = new bossAction(loadSprite(18, "Abobo/died/blownback"), 1, 1, 1);
        getHit = new bossAction(loadSprite(4, "Abobo/gets hit/gets hit"), 1, 1, 1);
        gutpunch = new bossAction(loadSprite(19, "Abobo/gut punches/gutpunches"), 1, 1, 1);
        headbutt = new bossAction(loadSprite(11, "Abobo/headbutt/headbutt"), 1,1,1);
        idle = new bossAction(loadSprite(10, "Abobo/idle/idle"),1,0,0);
        jumpelbow = new bossAction(loadSprite(12, "Abobo/jump elbow/jump elbow"),1,1,1);
        laugh = new bossAction(loadSprite(19, "Abobo/laugh/laugh"),1,0,0);
        thunderclap = new bossAction(loadSprite(13, "Abobo/thunder clap/thunderclap"),1,1,1);
        walk = new bossAction(loadSprite(12, "Abobo/walking/walk"), 1,1,1);*/

        jump = new bossAction("Abobo/jump/jump", 1, 1, 1, 1);
        backhand = new bossAction("Abobo/backhand/backhand", 6, 1, 1, 1);
        died = new bossAction("Abobo/died/blownback", 18, 1,1,1);
        getHit = new bossAction("Abobo/gets hit/gets hit", 4, 1,1,1);
        gutpunch = new bossAction("Abobo/gut punches/gutpunches", 19,1,1,1);
        headbutt = new bossAction("Abobo/headbutt/headbutt", 11, 1,1,1);
        idle = new bossAction("Abobo/idle/idle", 10,1,0,0);
        jumpelbow = new bossAction("Abobo/jump elbow/jump elbow", 12, 1,1,1);
        laugh = new bossAction("Abobo/laugh/laugh", 19,1,0,0);
        thunderclap = new bossAction("Abobo/thunder clap/thunderclap",13,1,1,1);
        walk = new bossAction("Abobo/walking/walk",12,1,1,1);

        actions = new bossAction[]{jump, backhand, died, getHit, gutpunch, headbutt, idle, jumpelbow, laugh, thunderclap, walk};
        currentAct = walk;
    }
    public Image getFrame(){
        //return currentAct.getFrame(direction);
        return currentAct.f();
    }
    public void getNextAction(){
        currentAct = actions[randint(0, actions.length - 1)];
        currentAct.resetCurrentF();
    }
    public int getX(){
        return x + currentAct.getmX(direction);
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
