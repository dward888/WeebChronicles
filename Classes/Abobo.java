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
    //Following variable are the different actions that the boss does
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
    //Following 2 variables are used to decide if the boss is going to walk or do another action
    private int walking;
    private int act;

    private int[]decision; //Array used determine the next action the boss will take
    private int[]fiftyfifty; //Array used to see if the boss will change direction
    private bossAction[] actions; //if the next decision is not to walk, it will be picked from this array

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
        decision = new int[]{walking, act}; //the boss will walk more than the other action
        fiftyfifty = new int[]{right, left};
    }
    //This method is used to get the frames of whatever action the boss is doing
    public Image getFrame(){
        //Following code changes the boss' direction if they are about to go out of the x range
        if(direction == left && hits >0){
            if(x + currentAct.getmX(direction) <= maxLeft){
                direction = right;
            }
        }
        if(direction == right && hits > 0){
            if(x + currentAct.getmX(direction) >= maxRight){
                direction = left;
            }
        }
        //Following code checks if the action is completed, if it is, then it determines the next action
        if(currentAct.isDone() && startMoving && hits > 0){
            currentAct.resetCurrentF();
            currentAct.resetMove();
            attack = false;
            getNextAction();
        }
        return currentAct.getFrame(direction);
    }
    //Following method is used to determine the next action of the boss, it is done by picking an action at random
    //from an array
    public void getNextAction(){
        if(hits > 0) {
            int next = decision[randint(0, decision.length - 1)]; //picking the next action
            int changeDirection = fiftyfifty[randint(0, fiftyfifty.length - 1)]; //seeing if the direction is going to change
            //Changing the direction
            if (changeDirection == right) {
                if (direction == right) {
                    direction = left;
                } else {
                    direction = right;
                }
            }
            if (next == walking) {
                currentAct = walk; //sets next action
                currentAct.resetCurrentF();
            }
            if (next == act) {
                //determines the next action from another array
                currentAct = actions[randint(0, actions.length - 1)];
                currentAct.resetCurrentF();
                //if the next action is any of these than it is considered an attack and will damage the player
                if (currentAct == backhand || currentAct == gutpunch || currentAct == headbutt || currentAct == thunderclap) {
                    attack = true;
                }
            }
        }
    }
    //get methods
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
        currentAct = getHit; //displays the get hit frames
    }
    public int getHits(){
        return hits;
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
