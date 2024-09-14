package org.example.osadnici;

import javax.swing.*;
import java.util.Map;

public class Text {
    public static void updatePlayerInfo(Game game, JTextArea textArea){
        Player currentPlayer = game.getCurrentPlayer(); // Get current player from game logic
        StringBuilder info = new StringBuilder("Player Information:\n");
        info.append("Player ").append(currentPlayer.getUniqueIndex() + 1).append("\n");
        info.append("Points: ").append(currentPlayer.getPoints()).append("\n");
        info.append("Resources:\n");

        for (Map.Entry<Material, MaterialCards> entry : currentPlayer.getCardsList().entrySet()) {
            info.append(entry.getKey()).append(": ").append(entry.getValue().numberOfCards).append("\n");
        }

        info.append("Pawns:\n");
        for (Map.Entry<PawnType, PawnSet> entry : currentPlayer.getPawnList().entrySet()) {
            info.append(entry.getKey()).append(": ").append(entry.getValue().numberOfPawns).append("\n");
        }

        textArea.setText(info.toString());
    }
}
