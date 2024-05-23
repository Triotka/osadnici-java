package org.example.osadnici;
import java.util.Scanner;

public class UserInterface {
    private String[] playerColors = {"Yellow", "White", "Red", "Blue"};

    public void rollDice(Dice dice){

        System.out.printf("Number rolled is dice:", dice.getRolledNumber());
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
    public void displayBuildBoard(){
        // TODO
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
        displayPawnsInfo(player);
    }
    public void showInvalidSell() {
        System.out.printf(String.format("Sell was unsuccessful"));
    }

    public void showValidSell() {
        System.out.printf(String.format("Sell was successful"));
    }

    public String getMaterial(String message) {
        Scanner sc = new Scanner(System.in);
        System.out.printf(String.format("Enter material you want to %s", message));
        return sc.nextLine();
    }

    public int getBuyerNumber(int playersCount) {
        System.out.printf(String.format("Type number for player you want to sell to"));

        for (int i = 0; i < playersCount; i++){
            System.out.printf(String.format("type %s for %s player", i, playerColors[i]));
        }
        Scanner in = new Scanner(System.in);
        return in.nextInt();
    }
}
