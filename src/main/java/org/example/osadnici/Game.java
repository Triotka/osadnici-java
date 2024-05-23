package org.example.osadnici;
import java.util.List;

public class Game {
    private int minimumPlayers = 2;
    private int maximumPlayers = 4;
    private int currentPlayerNum;
    private List<Player> players;
    private UserInterface UI;

    private void switchPlayers(){
        currentPlayerNum = (currentPlayerNum + 1) % players.size();
    }
    private void setPlayers(){
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
