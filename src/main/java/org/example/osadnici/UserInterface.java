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
        Scanner sc = new Scanner(System.in);
        System.out.printf(String.format("Enter command. Type switch, sell, buy or stats"));
        return sc.nextLine();
    }

    private String insertBuildPlace(){
        // TODO change building all color
        String character = "X";
        return character;
    }
    private String insertTileNumber(){
        // TODO change
        String character = "11";
        return character;
    }


    private String insertRoad(int level){
       String character = "/";
        if (level == 2) {
           character = "|";
       }
        else if (level == 1){
            character = "\\";
        }
        // TODO barva a cesty
        return character;
    }
    private void changeMaterialColor(){
        // TODO
    }

    private void printLastRow(int numberOfTiles)
    {
        // TODO spacing
        StringBuilder line = new StringBuilder();
        // print first line
        for (int i = 0; i < numberOfTiles; i++){
            line.append("  ").append(insertBuildPlace());
        }
        System.out.println(line);
    }
    private void printRow(int numberOfTiles) {
        // TODO spacing
        StringBuilder line = new StringBuilder();
        // print first line
        for (int i = 0; i < numberOfTiles; i++){
             line.append("  ").append(insertBuildPlace());
        }
        System.out.println(line);
        // print second line
        for (int i = 0; i < numberOfTiles; i++){
            line.append(insertRoad(0));
            changeMaterialColor();
            line.append("***");
            line.append(insertRoad((1)));
        }
        System.out.println(line);
        // print third line
        for (int i = 0; i < numberOfTiles; i++){
            line.append(insertBuildPlace());
            changeMaterialColor();
            line.append("*****");
        }
        line.append(insertBuildPlace());
        System.out.println(line);
        // print fourth line
        for (int i = 0; i < numberOfTiles; i++){
            line.append(insertRoad(2));
            changeMaterialColor();
            line.append("***" + insertTileNumber() + "***" );
        }
        line.append(insertRoad(2));
        System.out.println(line);

        // print fifth line
        for (int i = 0; i < numberOfTiles; i++){
            line.append(insertBuildPlace());
            changeMaterialColor();
            line.append("*****");
        }
        line.append(insertBuildPlace());
        System.out.println(line);

        // print sixth line
        for (int i = 0; i < numberOfTiles; i++){
            line.append(insertRoad(0));
            changeMaterialColor();
            line.append("***");
            line.append(insertRoad((1)));
        }
        System.out.println(line);

    }

    public void displayBoard(Board board){
        printRow(3);
        printRow(4);
        printRow(5);
        printRow(4);
        printRow(3);
        printLastRow(3);
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
    public void unsuccessfulBuying() {
        System.out.printf(String.format("Buying was unsuccessful"));
    }
    public void successfulBuying() {
        System.out.printf(String.format("Buying was successful"));
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

    public String getBuyRequest() {
        Scanner sc = new Scanner(System.in);
        System.out.printf(String.format("Enter pawn you want to buy. Type village, town, road or stop if you want to stop this action"));
        return sc.nextLine();
    }

    public Position getRoadNumbers() {
        System.out.printf(String.format("Type numbers where to place road, type two beginning and end"));
        Scanner in = new Scanner(System.in);
        return new Position(in.nextInt(), in.nextInt());
    }
}
