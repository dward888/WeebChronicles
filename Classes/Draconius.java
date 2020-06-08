//Abobo.java
//Jim Ji and Edward Yang
//This class is used to make the level 1 boss, Abobo. This class outputs the frames and takes in account of the health
//and the attacks.

import java.awt.*;

public class Draconius{
    private int currentF; //variable used to keep track of the frames
    //maxLeft and maxRight are used to keep the x coordinate of the boss within a range
    private int x, y, maxLeft,maxRight;
    private int direction, left, right;
    private boolean startMoving; //variable used to check that the mini cut scene is over
    private boolean attack; //variable used to check if the boss is attacking
    private int hits; //used as a health variable, the boss can take up to 16 hits
    private int health;
    private bossAction currentAct;
    private bossAction jump;
    private bossAction idle;
    private bossAction swing;
    private bossAction walk;
    private int walking;
    private int act;
    private int[]decision;
    private int[]fiftyfifty;
    private bossAction[] actions;

    public Draconius(){
        currentF = 0;
        x = 7650;
        y = 400;
        maxLeft = 6720;
        maxRight = 7800;
        left = 2;
        right = 1;
        walking = 1;
        act = 2;
        hits = 32;
        direction = left;
        startMoving = false;
        health = 1;


        jump = new bossAction("draconiusFrames/jump/jump", 4, 15, 1, 10);
        swing = new bossAction("draconiusFrames/swing/swing", 3, 20, 1, 1);
        //died = new bossAction("draconiusFrames/dead/dead", 4, 10,1,1);

        idle = new bossAction("draconiusFrames/idle/idle", 1,1,0,0);

        walk = new bossAction("draconiusFrames/walking/walking",5,15,3,1);
        actions = new bossAction[]{swing, swing, swing, jump, idle};
        currentAct = idle;
        decision = new int[]{walking, act};
        fiftyfifty = new int[]{right, left};
    }
    public Image getFrame(){
        //This method is used to get the frames of whatever action the boss is doing
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
        if(currentAct.isDone() && startMoving){
            currentAct.resetCurrentF();
            currentAct.resetMove();
            attack = false;
            getNextAction();
        }
        return currentAct.getFrame(direction);
    }
    public void getNextAction(){
        int next = decision[randint(0, decision.length - 1)];
        int changeDirection = fiftyfifty[randint(0, fiftyfifty.length-1)];
        if(changeDirection == right){
            if(direction == right){
                direction = left;
            }
            else{
                direction = right;
            }
        }
        if(next == walking){
            currentAct = walk;
            currentAct.resetCurrentF();
        }
        if(next == act){
            currentAct = actions[randint(0, actions.length - 1)];
            currentAct.resetCurrentF();//adsf
            if(currentAct == swing || currentAct == jump){
                attack = true;
            }
        }
    }
    public int getX(){
        x += currentAct.getmX(direction);
        return x;
    }
    public void start(){
        startMoving = true;
    }
    public boolean getStart(){
        return startMoving;
    }
    public int getY(){
        return y;
    }
    public boolean getAttack(){
        return attack;
    }
    public void gotHit(){
        hits --;
    }
    public int getHits(){
        return hits;
    }
    public void moveX(int n){
        x += n;
    }
    public int getDirection(){
        return direction;
    }
    public Rectangle getRect(){
        return new Rectangle(x ,y, 200,200);
    }
    public int randint(int low, int high){
        return(int)(Math.random()*(high-low+1)+low);
    }
}
