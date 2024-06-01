package org.example.osadnici;

import java.util.Random;

/**
 * Represents a dice used in the game.
 * The dice can be rolled to generate a random number between 2 and 12.
 */
public class Dice {
    /** The number obtained from the latest roll of the dice. */
    private int rolledNumber;

    /**
     * Rolls the dice and updates the rolled number.
     * The rolled number is the sum of two random integers between 1 and 6.
     *
     * @param UI the user interface to update with the rolled number
     */
    public void roll(UserInterface UI) {
        Random rand = new Random();
        int firstRolled = rand.nextInt(6) + 1;
        int secondRolled = rand.nextInt(6) + 1;
        this.rolledNumber = firstRolled + secondRolled;
        UI.rollDice(this);
    }

    /**
     * Gets the number obtained from the latest roll of the dice.
     *
     * @return the rolled number
     */
    public int getRolledNumber() {
        return rolledNumber;
    }
}
