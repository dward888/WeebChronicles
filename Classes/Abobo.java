//Abobo.java
//Jim Ji and Edward Yang
//This class is used to make the level 1 boss, Abobo. This class outputs the frames and takes in account of the health
//and the attacks.

import java.awt.*;

public class Abobo {
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
    private bossAction backhand;
    private bossAction died;
    private bossAction getHit;
    private bossAction gutpunch;
    private bossAction headbutt;
    private bossAction idle;
    private bossAction jumpelbow;
    private bossAction laugh;
    private bossAction thunderclap;
    private bossAction walk;
    private int walking;
    private int act;
    private int[]decision;
    private int[]fiftyfifty;
    private bossAction[] actions;

    public Abobo(){
        currentF = 0;
        x = 7650;
        y = 485;
        maxLeft = 6720;
        maxRight = 7800;
        left = 2;
        right = 1;
        walking = 1;
        act = 2;
        //hits = 16;
        hits = 1;
        direction = left;
        startMoving = false;
        health = 1;
        //health = 150;
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
        actions = new bossAction[]{backhand, gutpunch, headbutt, idle, laugh, thunderclap};
        currentAct = laugh;
        decision = new int[]{walking, act};
        fiftyfifty = new int[]{right, left};
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
            if(currentAct == backhand || currentAct == gutpunch || currentAct == headbutt || currentAct == thunderclap){
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
        currentAct = getHit;
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
        return new Rectangle(x ,y, 100,100);
    }
    public int randint(int low, int high){
        return(int)(Math.random()*(high-low+1)+low);
    }
}
