package org.example.osadnici;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserInterface {

    private String[] playerColors = {"Yellow", "Green", "Red", "Blue"};




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

    private Color matchPlayerColor(int playerIndex){
        switch (playerIndex){
            case 0:
                return Color.YELLOW;
            case 1:
                return Color.GREEN;
            case 2:
                return Color.BLUE;
            case 3:
                return Color.RED;
        }
        return Color.WHITE;
    }

    private Color matchMaterialColor(Material material){
        if (material == null){
            return Color.WHITE;
        }
        switch (material){
            case Material.Brick:
                return Color.YELLOW;
            case Material.Wood:
                return Color.GREEN;
            case Material.Sheep:
                return Color.PURPLE;
            case Material.Wheat:
                return Color.YELLOW;
            case Material.Stone:
                return Color.BLUE;
        }
        return Color.WHITE;
    }
    private String insertBuildPlace(Board board, int buildPlaceIndex, boolean buildView){
        String character = "XX";
        var building = board.buildings.get(buildPlaceIndex);
        if (building.type != null ){
            var color = matchPlayerColor(building.owner).get();
            var restart = Color.RESET.get();
            if (building.type == PawnType.Village){
                character = color + "VV" + restart;
            }
            else if (building.type == PawnType.Town)
                character = color + "TT" + restart;
        }
        else if (buildView){
            character = String.format("%02d", buildPlaceIndex);
        }

        return character;
    }
    private String insertTileNumber(Board board, int tileIndex){
        var tile = board.tilesList.get(tileIndex);
        var number = String.format("%02d", tile.getNumber());
        var color = matchMaterialColor(tile.getMaterial()).get();
        var restart = Color.RESET.get();
        String character = color + number + restart;
        return character;
    }


    private String insertRoad(int level){
       String character = "//";
        if (level == 2) {
           character = "||";
       }
        else if (level == 1){
            character = "\\" + "\\" ;
        }
        // TODO barva a cesty
        return character;
    }

    private void printFirstLine(Board board, int numberOfTiles, String spacing, List<Integer> buildPlaces, boolean buildView){
        StringBuilder line = new StringBuilder();
        line.append(spacing);
        line.append("   ");
        // print first line
        int currentBuildPlace = 0;
        for (int i = 0; i < numberOfTiles; i++){
            line.append(insertBuildPlace(board, buildPlaces.get(currentBuildPlace), buildView)).append("       ");
            currentBuildPlace++;
        }
        System.out.println(line);
    }
    private void printSecondLine(Board board, int numberOfTiles, String spacing, boolean buildView){
        // print second line
        StringBuilder line = new StringBuilder();
        line.append(spacing);
        for (int i = 0; i < numberOfTiles; i++){
            line.append(insertRoad(0));
            line.append("     ");
            line.append(insertRoad((1)));
        }
        System.out.println(line);
    }
    private void printThirdLine(Board board, int numberOfTiles, String spacing, List<Integer> buildPlaces, boolean buildView){
        // print third line
        StringBuilder line = new StringBuilder();
        line.append(spacing);
        int currentBuildPlace = 0;
        for (int i = 0; i < numberOfTiles; i++){
            line.append(insertBuildPlace(board, buildPlaces.get(currentBuildPlace), buildView));
            currentBuildPlace ++;
            line.append("       ");
        }
        line.append(insertBuildPlace(board, buildPlaces.get(currentBuildPlace), buildView));
        currentBuildPlace++;
        System.out.println(line);
    }

    private int printFourthLine(Board board, int numberOfTiles, String spacing, boolean buildView, int currentTile){
        // print fourth line
        StringBuilder line = new StringBuilder();
        line.append(spacing);
        for (int i = 0; i < numberOfTiles; i++){
            line.append(insertRoad(2));
            line.append("  " + insertTileNumber(board, currentTile) + "*  " );
            currentTile++;
        }
        line.append(insertRoad(2));
        System.out.println(line);
        return currentTile;
    }

    private void printFifthLine(Board board, int numberOfTiles, String spacing, List<Integer> buildPlaces, boolean buildView){
        // print fifth line
        int currentBuildPlace = 0;
        StringBuilder line = new StringBuilder();
        line.append(spacing);
        for (int i = 0; i < numberOfTiles; i++){
            line.append(insertBuildPlace(board, buildPlaces.get(currentBuildPlace), buildView));
            currentBuildPlace++;
            line.append("       ");
        }
        line.append(insertBuildPlace(board, buildPlaces.get(currentBuildPlace), buildView));
        currentBuildPlace++;
        System.out.println(line);

    }
    private void printSixthLine(Board board, int numberOfTiles, String spacing, boolean buildView) {
        // print sixth line
        StringBuilder line = new StringBuilder();
        line.append(spacing);
        for (int i = 0; i < numberOfTiles; i++){
            line.append(insertRoad(1));
            line.append("     ");
            line.append(insertRoad((0)));
        }
        System.out.println(line);
    }

    public void displayBoard(Board board) {
        displayBoard(board, false);
    }

        public void displayBoard(Board board, boolean buildView){
        int currentTileNumber = 0;
        String firstSpacing = "       ";
        String secondSpacing = "    ";
        String thirdSpacing = " ";
        printFirstLine(board,3, firstSpacing,  List.of(2, 4, 6), buildView);
        printSecondLine(board,3, firstSpacing, buildView);
        printThirdLine(board,3, firstSpacing, List.of(1, 3, 5, 7), buildView);
        currentTileNumber = printFourthLine(board,3, firstSpacing, buildView, currentTileNumber);
        printFirstLine(board, 4, secondSpacing, List.of(9, 11, 13, 15), buildView);
        printSecondLine(board,4, secondSpacing, buildView);
        printThirdLine(board,4, secondSpacing, List.of(8, 10, 12, 14, 16), buildView);
        currentTileNumber = printFourthLine(board,4, secondSpacing, buildView, currentTileNumber);
        printFirstLine(board,5, thirdSpacing, List.of(18, 20, 22, 24, 26), buildView);
        printSecondLine(board,5, thirdSpacing, buildView);
        printThirdLine(board,5, thirdSpacing, List.of(17, 19, 21, 23, 25, 48), buildView);
        currentTileNumber = printFourthLine(board,5, thirdSpacing, buildView, currentTileNumber);
        printFifthLine(board,5, thirdSpacing, List.of(53, 28, 30, 32, 34, 49), buildView);
        printSixthLine(board,5, thirdSpacing, buildView);

        printThirdLine(board,4, secondSpacing, List.of(27, 29, 31, 33, 50), buildView);
        currentTileNumber = printFourthLine(board,4, secondSpacing, buildView, currentTileNumber);
        printFifthLine(board,4, secondSpacing, List.of(54, 36, 38, 40, 51), buildView);
        printSixthLine(board,4, secondSpacing, buildView);

        printThirdLine(board,3, firstSpacing, List.of(35, 37, 39, 52), buildView);
        currentTileNumber = printFourthLine(board,3, firstSpacing, buildView, currentTileNumber);
        printFifthLine(board, 3, firstSpacing, List.of(41, 43, 45, 47), buildView);
        printSixthLine(board,3, firstSpacing, buildView);
        printFirstLine(board,3, firstSpacing, List.of(42, 44, 46), buildView);

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
    public void displayBuildBoard(Board board){
        // TODO
        displayBoard(board, true);
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
