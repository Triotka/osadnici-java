package org.example.osadnici;
import java.util.Random;
public class Dice {
    private int rolledNumber;
    public void roll(UserInterface UI){
        Random rand = new Random();
        this.rolledNumber = rand.nextInt(6) + 1;
        UI.rollDice(this);
    }
    public int getRolledNumber() {

        return rolledNumber;
    }
}
