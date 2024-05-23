package org.example.osadnici;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private Board board;
    private int minimumPlayers = 2;
    private int maximumPlayers = 4;
    private int pirateNumber = 7;
    private int currentPlayerNum;
    private List<Player> players;
    private UserInterface UI;
    private Dice dice;


    private void startFirstRound(){
       var currentPlayer = players.get(currentPlayerNum);
       currentPlayer.startVillage(UI, board);
       currentPlayer.startRoad(UI, board);
       int position = currentPlayer.startVillage(UI, board);
       currentPlayer.startRoad(UI, board);
       currentPlayer.giveStartCards(position);
       if (currentPlayerNum + 1 == players.size()){
           startRegularTurn();
       }
       switchPlayers();
       startFirstRound();


    }
    private void doPirateConsequences(){
        // TODO
    }
    private void distributeCards(){
        // TODO
    }
// rolls dice and does expected action according to it
    private void rollDice(){
        dice.roll(UI);
        int rolledNumber = dice.getRolledNumber();
        if (rolledNumber == pirateNumber){
            doPirateConsequences();
        }
        else{
            distributeCards();
        }
    }

    private void startRegularTurn() {
        rollDice();
        processCommands();
    }

    private void processCommands(){
       var command = UI.receiveCommand();
       switch (command){
           case "stats":
               UI.displayStats(players.get(currentPlayerNum));
               break;
           case "switch":
               startRegularTurn(); // TODO zkontrolovat jestli funguje
               return;
           case "buy":
               players.get(currentPlayerNum).startBuy(UI, board);
               break;
           case "sell":
               players.get(currentPlayerNum).startSell(UI, players);
               break;
       }
        processCommands();

    }

    public void play(){
        setPlayers();
        startFirstRound();
    }
    private void switchPlayers(){
        currentPlayerNum = (currentPlayerNum + 1) % players.size();
    }
    private void setPlayers(){
        this.players = new ArrayList<Player>();
        int numberOfPlayers = UI.choosePlayers();
        // invalid number of players
        while(numberOfPlayers < minimumPlayers || numberOfPlayers > maximumPlayers){
            UI.printInvalidNumPlayers();
            numberOfPlayers = UI.choosePlayers();
        }
        for (int playerIndex = 0; playerIndex < numberOfPlayers; playerIndex++){
            players.add(new Player(playerIndex));
        }
    }
}
