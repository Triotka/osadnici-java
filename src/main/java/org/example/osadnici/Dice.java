package org.example.osadnici;

public class Dice {
    private int rolledNumber;
    public void roll(UserInterface UI){
        // TODO
        UI.rollDice(this);
    }
    public int getRolledNumber() {

        return rolledNumber;
    }
}
