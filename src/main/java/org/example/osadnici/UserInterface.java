package org.example.osadnici;

import java.util.List;
import java.util.Scanner;

/**
 * This class represents the user interface for the game. It contains methods
 * for displaying information to the user and receiving user input.
 */
public class UserInterface {
    // player colors in the game
    private final String[] playerColors = {"Yellow", "Green", "Blue", "Red"};

    /**
     * Display the rolled number from the dice.
     *
     * @param dice The Dice object representing the dice used in the game.
     */
    public void rollDice(Dice dice) {

        System.out.println("Number rolled is: " + dice.getRolledNumber());
    }

    /**
     * Inform the user that they won the game.
     */
    public void printWinner() {
        System.out.println("You won the game");
    }

    /**
     * Prompt the user to enter a command (switch, sell, buy, or stats).
     *
     * @return The user's entered command.
     */
    public String receiveCommand() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter command. Type switch, sell, buy or stats");
        return sc.nextLine();
    }

    /**
     * Match player index to corresponding color.
     *
     * @param playerIndex The index of the player.
     * @return The color associated with the player.
     */
    private ColorAnsi matchPlayerColor(int playerIndex) {
        return switch (playerIndex) {
            case 0 -> ColorAnsi.YELLOW;
            case 1 -> ColorAnsi.GREEN;
            case 2 -> ColorAnsi.BLUE;
            case 3 -> ColorAnsi.RED;
            default -> ColorAnsi.WHITE;
        };
    }

    /**
     * Match material to corresponding color.
     *
     * @param material The material type.
     * @return The color associated with the material.
     */
    private ColorAnsi matchMaterialColor(Material material) {
        if (material == null) {
            return ColorAnsi.WHITE;
        }
        return switch (material) {
            case Material.Brick -> ColorAnsi.RED;
            case Material.Wood -> ColorAnsi.GREEN;
            case Material.Sheep -> ColorAnsi.PURPLE;
            case Material.Wheat -> ColorAnsi.YELLOW;
            case Material.Stone -> ColorAnsi.BLUE;
        };
    }
    /**
     * Inserts building site into board according to number on the line.
     * Inserts only numbers if build view is activated.
     * If not then puts symbols for villages or towns of right color or neutral symbol if village or town not present.
     *
     * @param buildPlaceIndex The index of inserted build place in relation to line.
     * @param  board The game board with game logic data.
     * @param  buildView True if build view is activated otherwise false
     * @return The appropriate string for current building
     */
    private String insertBuildPlace(Board board, int buildPlaceIndex, boolean buildView) {
        String character = String.format("%02d", buildPlaceIndex);
        if (buildView)
            return character;

        var building = board.buildings.get(buildPlaceIndex);
        if (building.type != null) {
            var color = matchPlayerColor(building.owner).get();
            var restart = ColorAnsi.RESET.get();
            if (building.type == PawnType.Village) {
                character = color + "VV" + restart;
            } else if (building.type == PawnType.Town)
                character = color + "TT" + restart;
        } else {
            character = "XX";
        }

        return character;
    }
    /**
     * Inserts tiles' numbers into board.
     *
     * @param tileIndex The index of inserted tile number in relation to line.
     * @param  board The game board with game logic data.
     * @return The appropriate formatted string for current number.
     */
    private String insertTileNumber(Board board, int tileIndex) {
        var tile = board.tilesList.get(tileIndex);
        var number = String.format("%02d", tile.number());
        var color = matchMaterialColor(tile.material()).get();
        var restart = ColorAnsi.RESET.get();
        return color + number + restart;
    }

    /**
     * Inserts roads according to its position into board.
     *
     * @param currentRoadPosition The start and end position of road on the board
     * @param  board The game board with game logic data.
     * @param  roadTypeNumber number of road type being printed
     * @return The appropriate formatted string for current number.
     */
    private String insertRoad(Board board, int roadTypeNumber, Position currentRoadPosition) {

        String character = "//";
        if (roadTypeNumber == 2) {
            character = "||";
        } else if (roadTypeNumber == 1) {
            character = "\\" + "\\";
        }

        if (currentRoadPosition.start > currentRoadPosition.end) {
            int temp = currentRoadPosition.start;
            currentRoadPosition.start = currentRoadPosition.end;
            currentRoadPosition.end = temp;

        }
        // make road match owners color
        if (board.roads.containsKey(currentRoadPosition.start)) {
            var listOfRoads = board.roads.get(currentRoadPosition.start);
            for (var road : listOfRoads) {
                if (road.endPosition == currentRoadPosition.end) {
                    var color = matchPlayerColor(road.owner).get();
                    var restart = ColorAnsi.RESET.get();
                    return color + character + restart;
                }
            }
        }
        return character;
    }

    // Methods to print corresponding line levels of hexagon (6 levels in total for one complete hexagon)
    private void printFirstLine(Board board, int numberOfTiles, String spacing, List<Integer> buildPlaces, boolean buildView) {
        StringBuilder line = new StringBuilder();
        line.append(spacing);
        line.append("   ");
        // print first line
        int currentBuildPlace = 0;
        for (int i = 0; i < numberOfTiles; i++) {
            line.append(insertBuildPlace(board, buildPlaces.get(currentBuildPlace), buildView)).append("       ");
            currentBuildPlace++;
        }
        System.out.println(line);
    }

    private void printSecondLine(Board board, int numberOfTiles, String spacing, boolean buildView, List<Position> listRoads) {
        // print second line
        int currentRoadIndex = 0;
        StringBuilder line = new StringBuilder();
        line.append(spacing);
        for (int i = 0; i < numberOfTiles; i++) {
            line.append(insertRoad(board, 0, listRoads.get(currentRoadIndex)));
            currentRoadIndex++;
            line.append("     ");
            line.append(insertRoad(board, 1, listRoads.get(currentRoadIndex)));
            currentRoadIndex++;
        }
        System.out.println(line);
    }

    private void printThirdLine(Board board, int numberOfTiles, String spacing, List<Integer> buildPlaces, boolean buildView) {
        // print third line
        StringBuilder line = new StringBuilder();
        line.append(spacing);
        int currentBuildPlace = 0;
        for (int i = 0; i < numberOfTiles; i++) {
            line.append(insertBuildPlace(board, buildPlaces.get(currentBuildPlace), buildView));
            currentBuildPlace++;
            line.append("       ");
        }
        line.append(insertBuildPlace(board, buildPlaces.get(currentBuildPlace), buildView));
        currentBuildPlace++;
        System.out.println(line);
    }

    private int printFourthLine(Board board, int numberOfTiles, String spacing, boolean buildView, int currentTile, List<Position> listRoads) {
        // print fourth line
        StringBuilder line = new StringBuilder();
        line.append(spacing);
        int currentRoadIndex = 0;
        for (int i = 0; i < numberOfTiles; i++) {
            line.append(insertRoad(board, 2, listRoads.get(currentRoadIndex)));
            currentRoadIndex++;
            line.append("  ").append(insertTileNumber(board, currentTile)).append("*  ");
            currentTile++;
        }
        line.append(insertRoad(board, 2, listRoads.get(currentRoadIndex)));
        currentRoadIndex++;
        System.out.println(line);
        return currentTile;
    }

    private void printFifthLine(Board board, int numberOfTiles, String spacing, List<Integer> buildPlaces, boolean buildView) {
        // print fifth line
        int currentBuildPlace = 0;
        StringBuilder line = new StringBuilder();
        line.append(spacing);
        for (int i = 0; i < numberOfTiles; i++) {
            line.append(insertBuildPlace(board, buildPlaces.get(currentBuildPlace), buildView));
            currentBuildPlace++;
            line.append("       ");
        }
        line.append(insertBuildPlace(board, buildPlaces.get(currentBuildPlace), buildView));
        currentBuildPlace++;
        System.out.println(line);

    }

    private void printSixthLine(Board board, int numberOfTiles, String spacing, boolean buildView, List<Position> listRoads) {
        // print sixth line
        int currentRoadIndex = 0;
        StringBuilder line = new StringBuilder();
        line.append(spacing);
        for (int i = 0; i < numberOfTiles; i++) {
            line.append(insertRoad(board, 1, listRoads.get(currentRoadIndex)));
            currentRoadIndex++;
            line.append("     ");
            line.append(insertRoad(board, 0, listRoads.get(currentRoadIndex)));
            currentRoadIndex++;
        }
        System.out.println(line);
    }

    /**
     * Display the first board in normal view and then in build view so that user knows where to build.
     *
     * @param board     The Board object representing the game board.
     */
    public void displayBuildBoard(Board board) {
        displayBoard(board, false);
        System.out.println();
        displayBoard(board, true);
    }
    /**
     * Display the game board.
     *
     * @param board     The Board object representing the game board with game logic.
     * @param buildView Indicates whether to display build information (numbers instead of villages and towns).
     */
    public void displayBoard(Board board, boolean buildView) {
        int currentTileNumber = 0;
        String firstSpacing = "       ";
        String secondSpacing = "    ";
        String thirdSpacing = " ";
        printFirstLine(board, 3, firstSpacing, List.of(2, 4, 6), buildView);
        printSecondLine(board, 3, firstSpacing, buildView,
                List.of(new Position(2, 1), new Position(2, 3), new Position(4, 3), new Position(4, 5), new Position(6, 5), new Position(6, 7)));
        printThirdLine(board, 3, firstSpacing, List.of(1, 3, 5, 7), buildView);
        currentTileNumber = printFourthLine(board, 3, firstSpacing, buildView, currentTileNumber,
                List.of(new Position(1, 9), new Position(3, 11), new Position(5, 13), new Position(7, 15)));
        printFirstLine(board, 4, secondSpacing, List.of(9, 11, 13, 15), buildView);
        printSecondLine(board, 4, secondSpacing, buildView,
                List.of(new Position(9, 8), new Position(9, 10), new Position(11, 10), new Position(11, 12), new Position(13, 12), new Position(13, 14), new Position(15, 14), new Position(15, 16)));
        printThirdLine(board, 4, secondSpacing, List.of(8, 10, 12, 14, 16), buildView);
        currentTileNumber = printFourthLine(board, 4, secondSpacing, buildView, currentTileNumber,
                List.of(new Position(8, 18), new Position(10, 20), new Position(12, 22), new Position(14, 24), new Position(16, 26)));
        printFirstLine(board, 5, thirdSpacing, List.of(18, 20, 22, 24, 26), buildView);
        printSecondLine(board, 5, thirdSpacing, buildView,
                List.of(new Position(17, 18), new Position(19, 18), new Position(20, 19), new Position(21, 20), new Position(22, 21), new Position(22, 23), new Position(24, 23), new Position(24, 25), new Position(26, 25), new Position(48, 26)));
        printThirdLine(board, 5, thirdSpacing, List.of(17, 19, 21, 23, 25, 48), buildView);
        currentTileNumber = printFourthLine(board, 5, thirdSpacing, buildView, currentTileNumber,
                List.of(new Position(17, 53), new Position(19, 28), new Position(21, 30), new Position(23, 32), new Position(25, 34), new Position(48, 49)));
        printFifthLine(board, 5, thirdSpacing, List.of(53, 28, 30, 32, 34, 49), buildView);
        printSixthLine(board, 5, thirdSpacing, buildView,
                List.of(new Position(53, 27), new Position(28, 27), new Position(28, 29), new Position(30, 29), new Position(30, 31), new Position(32, 31), new Position(32, 33), new Position(34, 33), new Position(34, 50), new Position(50, 49)));

        printThirdLine(board, 4, secondSpacing, List.of(27, 29, 31, 33, 50), buildView);
        currentTileNumber = printFourthLine(board, 4, secondSpacing, buildView, currentTileNumber,
                List.of(new Position(27, 54), new Position(29, 36), new Position(31, 38), new Position(33, 40), new Position(50, 51)));
        printFifthLine(board, 4, secondSpacing, List.of(54, 36, 38, 40, 51), buildView);
        printSixthLine(board, 4, secondSpacing, buildView,
                List.of(new Position(54, 35), new Position(35, 36), new Position(36, 37), new Position(37, 38), new Position(38, 39), new Position(39, 40), new Position(40, 52), new Position(52, 51)));

        printThirdLine(board, 3, firstSpacing, List.of(35, 37, 39, 52), buildView);
        currentTileNumber = printFourthLine(board, 3, firstSpacing, buildView, currentTileNumber,
                List.of(new Position(35, 41), new Position(37, 43), new Position(39, 45), new Position(52, 47)));
        printFifthLine(board, 3, firstSpacing, List.of(41, 43, 45, 47), buildView);
        printSixthLine(board, 3, firstSpacing, buildView,
                List.of(new Position(41, 42), new Position(42, 43), new Position(43, 44), new Position(44, 45), new Position(45, 46), new Position(46, 47)));
        printFirstLine(board, 3, firstSpacing, List.of(42, 44, 46), buildView);

    }

    /**
     * Prompt the user to choose the number of players.
     *
     * @return The number of players chosen by the user.
     */
    public int choosePlayers() {
        System.out.println("Choose number of players from 2 to 4");
        Scanner in = new Scanner(System.in);
        return in.nextInt();
    }

    /**
     * Inform the user that the entered number of players is invalid.
     */
    public void printInvalidNumPlayers() {
        System.out.println("Invalid number of players");
    }

    /**
     * Prompt the user to enter a number indicating where to place a building.
     *
     * @param buildingName The name of the building type to appear in a message.
     * @return The number indicating the placement of the building.
     */
    public int getBuildNumber(String buildingName) {
        System.out.printf("Type number where to place %s%n", buildingName);
        Scanner in = new Scanner(System.in);
        return in.nextInt();
    }
    /**
     * Prompt the user to enter numbers for placing a road.
     *
     * @return The start and end positions for the road.
     */
    public Position getRoadNumbers() {
        System.out.println("Type numbers where to place road, type two beginning and end");
        Scanner in = new Scanner(System.in);
        int first_number = in.nextInt();
        int second_number = in.nextInt();

        if (first_number < second_number)
            return new Position(first_number, second_number);
        else {
            return new Position(second_number, first_number);
        }

    }

    /**
     * Display information about available pawns for a player.
     *
     * @param player The Player object representing the player.
     */
    private void displayPawnsInfo(Player player) {
        System.out.println("Pawns available:");
        var pawns = player.pawnList;
        for (var pawnType : pawns.keySet()) {
            var pawnSet = pawns.get(pawnType);
            System.out.printf("%s:%s%n", pawnType.toString(), pawnSet.numberOfPawns);
        }
    }
    /**
     * Display information about cards held by a player.
     *
     * @param player The Player object representing the player.
     */
    private void displayCardsInfo(Player player) {
        System.out.println("Cards in hand:");
        var cards = player.cardsList;
        for (var material : cards.keySet()) {
            var materialCard = cards.get(material);
            System.out.printf("%s:%s%n", material.toString(), materialCard.numberOfCards);
        }
    }
    /**
     * Display statistics of the player.
     *
     * @param player The Player object representing the player.
     */
    public void displayStats(Player player) {
        System.out.printf("%s player info:%n", this.playerColors[player.uniqueIndex]);
        System.out.printf("Points:" + player.points + "%n");
        displayCardsInfo(player);
        displayPawnsInfo(player);
    }

    /**
     * Inform the user that a sell operation was unsuccessful.
     */
    public void showInvalidSell() {
        System.out.println("Sell was unsuccessful");
    }

    /**
     * Inform the user that a buying operation was unsuccessful.
     */
    public void unsuccessfulBuying() {
        System.out.println("Buying was unsuccessful");
    }

    /**
     * Inform the user that a buying operation was successful.
     */
    public void successfulBuying() {
        System.out.println("Buying was successful");
    }

    /**
     * Inform the user that a sell operation was successful.
     */
    public void showValidSell() {
        System.out.println("Sell was successful");
    }

    /**
     * Prompt the user to enter a material for buying or selling.
     *
     * @param message The message indicating the context (buy or sell).
     * @return The material entered by the user.
     */
    public String getMaterial(String message) {
        Scanner sc = new Scanner(System.in);
        System.out.printf("Enter material you want to %s%n", message);
        System.out.println("Type Sheep, Brick, Wood, Wheat or Stone (case sensitive)");
        return sc.nextLine();
    }

    /**
     * Prompt the user to enter the number of the player they want to sell to.
     *
     * @param playersCount The total number of players in the game.
     * @return The number indicating the player to sell to.
     */
    public int getBuyerNumber(int playersCount) {
        System.out.println("Type number for player you want to sell to");

        for (int i = 0; i < playersCount; i++) {
            System.out.printf("type %s for %s player%n", i, playerColors[i]);
        }
        Scanner in = new Scanner(System.in);
        return in.nextInt();
    }

    /**
     * Prompt the user to enter a request for buying a pawn.
     *
     * @return The user's buy request.
     */
    public String getBuyRequest() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter pawn you want to buy. Type village, town, road or stop if you want to stop this action");
        return sc.nextLine();
    }


}
