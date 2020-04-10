import javax.imageio.*;
import javax.swing.*;

public class Character {
    private String name;
    private int attack, defense, speed;

    public Character(String n, int a,  int d, int s){
        name = n;
        attack = a;
        defense = d;
        speed = s;
    }
}
