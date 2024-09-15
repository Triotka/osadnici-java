package org.example.osadnici;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the main game logic for the board game.
 * It manages the board, players, dice rolls, and game flow.
 */
public class Game {

    public Action currentAction = Action.FirstVillage;
    /** The game board. */
    private Board board;
    /** The number that triggers the special consequences when rolled. */
    private final int unluckyNumber = 7;
    /** The index of the current player. */
    public int currentPlayerNum;
    /** The list of players in the game. */
    private List<Player> players;
    /** The user interface for interacting with the game. */
    private UserInterface UI; //TODO erase
    /** The dice used in the game. */
    private Dice dice;

    /**
     * Starts the first round of the game.
     * Each player places their initial villages and roads.
     */
//    private void startFirstRound() {
//        var currentPlayer = players.get(currentPlayerNum);
//        currentPlayer.startVillage(UI, board);
//        currentPlayer.startRoad(UI, board);
//        currentPlayer.startVillage(UI, board);
//        currentPlayer.startRoad(UI, board);
//        if (currentPlayerNum + 1 == players.size()) {
//            return;
//        }
//        switchPlayers();
//        startFirstRound();
//    }

    /**
     * Handles the consequences when the unlucky number is rolled.
     * Players who have more than the unlucky number of cards lose half of them.
     */
    private void doSevenConsequences() {
        for (var player : players) {
            if (player.countCardSum() > unluckyNumber) {
                player.loseCards();
            }
        }
    }

    /**
     * Distributes resource cards to players based on the rolled number.
     * Each player receives resources from their buildings adjacent to the rolled tile.
     */
    private void distributeCards() {
        int rolledNumber = dice.getRolledNumber();
        var tiles = board.numbersToTiles.get(rolledNumber);

        for (var tile : tiles) {
            var group = tile.nodeGroup();
            for (var index : group) {
                var building = board.buildings.get(index);
                if (building.type == PawnType.Village) {
                    players.get(building.owner).giveCards(1, tile.material());
                } else if (building.type == PawnType.Town) {
                    players.get(building.owner).giveCards(2, tile.material());
                }
            }
        }
    }

    /**
     * Rolls the dice and performs actions based on the rolled number.
     * If the unlucky number is rolled, special consequences are triggered.
     * Otherwise, resources are distributed to the players.
     */
    public String rollDice() {
        dice.roll();
        int rolledNumber = dice.getRolledNumber();
        if (rolledNumber == unluckyNumber) {
            doSevenConsequences();
            return "Rolled: " + rolledNumber;
        } else {
            distributeCards();
            return "Rolled: " + rolledNumber + " and cards given";
        }
    }

    /**
     * Starts a regular turn for the current player.
     * Rolls the dice and processes the player's commands.
     */
    private void startRegularTurn() {
        rollDice();
      //  processCommand();
    }

    /**
     * Processes commands from the user interface and executes the corresponding actions.
     * Commands include "stats", "switch", "buy", and "sell".
     */
    // TODO smazat UI
    public void processCommand(String command) {
        switch (command) {
            case "stats":
                UI.displayStats(players.get(currentPlayerNum));
                break;
            case "switch":
                switchPlayers();
                startRegularTurn();
                return;
            case "buy":
                players.get(currentPlayerNum).startBuy(UI, board);
                break;
            case "sell":
                players.get(currentPlayerNum).startSell(UI, players);
                break;
        }
    }


    public Board GetBoard(){
        return board;
    }
    /**
     * Initiates the game setup and starts the game loop.
     */
    public void play() {
        dice = new Dice();
       // UI = new UserInterface();
        //setPlayers();
        board = new Board();
        // TODO
      //  startFirstRound();
//        switchPlayers();
//        startRegularTurn();\
    }

    /**
     * Switches to the next player in the turn order.
     */
    public void switchPlayers() {
        currentPlayerNum = (currentPlayerNum + 1) % players.size();
    }

    /**
     * Sets up the players for the game.
     * Prompts the user to choose the number of players and initializes them.
     */
    public void setPlayers(int numberOfPlayers) {
        this.players = new ArrayList<>();
        //int numberOfPlayers = 4; // TODO UI.choseplayers alternative
        // valid range of players
     //   int minimumPlayers = 2;
       // int maximumPlayers = 4;
       // while (numberOfPlayers < minimumPlayers || numberOfPlayers > maximumPlayers) {
//           TODO UI.printInvalidNumPlayers(); alternative
           // numberOfPlayers = 4; // TODO UI.choseplayers alternative
       // }
        for (int playerIndex = 0; playerIndex < numberOfPlayers; playerIndex++) {
            players.add(new Player(playerIndex));
        }
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerNum);
    }
    public int getPlayersNum() {
        return players.size();
    }
}
