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

    public String receiveCommand(){
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


    public int getBuildNumber(String buildingName) {
        System.out.printf(String.format("Type number where to place %s", buildingName));
        Scanner in = new Scanner(System.in);
        return in.nextInt();
    }

    private void displayPawnsInfo(Player player){
        System.out.printf(String.format("Pawns available:"));
        var pawns = player.pawnList;
        for (var pawnType: pawns.keySet()){
            var pawnSet = pawns.get(pawnType);
            System.out.printf(String.format("%s:%s", pawnType.toString(), pawnSet.numberOfPawns));
        }
    }
    private void displayCardsInfo(Player player){
        System.out.printf(String.format("Cards in hand:"));
        var cards = player.cardsList;
        for (var material: cards.keySet()){
            var materialCard = cards.get(material);
            System.out.printf(String.format("%s:%s", material.toString(), materialCard.numberOfCards));
        }
    }
    public void displayStats(Player player) {
        System.out.printf(String.format("Player %s info:", player.uniqueIndex));
        System.out.printf(String.format("Points:", player.points));
        displayCardsInfo(player);



    }
}
