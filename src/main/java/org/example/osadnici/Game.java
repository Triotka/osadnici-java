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

    /** The dice used in the game. */
    private Dice dice;


    /**
     * Handles the consequences when 7 is rolled.
     * Players who have more than 7 of cards lose half of them.
     */
    private void doSevenConsequences() {
        for (var player : players) {
            if (player.countCardSum() > unluckyNumber) {
                player.loseCards();
            }
        }
    }

    /**
     * Distributes cards to players based on the rolled number.
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
     * If the 7 is rolled, special consequences are triggered.
     * Otherwise, cards are distributed to the players.
     *
     * @return a string indicating the result of the roll
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
     * Get the current game board.
     *
     * @return the current Board object
     */
    public Board getBoard(){
        return board;
    }

    /**
     * Initiates the game setup.
     */
    public void init() {
        dice = new Dice();
        board = new Board();
    }

    /**
     * Switches to the next player in the turn order.
     */
    public void switchPlayers() {
        currentPlayerNum = (currentPlayerNum + 1) % players.size();
    }

    /**
     * Sets up the players for the game.
     *
     * @param numberOfPlayers the number of players participating in the game
     */
    public void setPlayers(int numberOfPlayers) {
        this.players = new ArrayList<>();
        for (int playerIndex = 0; playerIndex < numberOfPlayers; playerIndex++) {
            players.add(new Player(playerIndex));
        }
    }

    /**
     * Gets the current player based on the turn order.
     *
     * @return the current Player object
     */
    public Player getCurrentPlayer() {
        return players.get(currentPlayerNum);
    }

    /**
     * Gets the number of players in the game.
     *
     * @return the total number of players
     */
    public int getPlayersNum() {
        return players.size();
    }

    /**
     * Gets a player by their index in player list.
     *
     * @param index the index of the player to get
     * @return the Player at the specified index
     */
    public Player getPlayer(int index){
        return players.get(index);
    }
}