package org.example.osadnici;
import java.util.Scanner;

public class UserInterface {
    private String[] playerColors = {"Yellow", "White", "Red", "Blue"};

    public void rollDice(Dice dice){
        System.out.printf("Number rolled is dice:", dice.getRolledNumber());
    }
    public void showStats(Player currentPlayer){
        // TODO
    }
    public void printWinner(){
        // TODO
    }

    public String recieveCommand(){
        // TODO
        return null;
    }
    public void displayBoard(){
        // TODO
    }
    public int choosePlayers() {
        int numberOfPlayers = 0;
        System.out.printf("Choose number of players from 2 to 4");
        Scanner in = new Scanner(System.in);
        numberOfPlayers = in.nextInt();
        return numberOfPlayers;
    }
    public void printInvalidNumPlayers(){
        System.out.printf("Invalid number of players");
    }
}
