package org.example.osadnici;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class GenericWindow {
    public static final List<Color> playerColors = List.of(Color.YELLOW, Color.WHITE, Color.RED, Color.BLUE );
    public static final List<String> playerNames = List.of("Yellow", "White", "Red", "Blue" );

    public static <T> void changeBackground(T component){
        // TODO if T is JFrame set its background and if T is JPanel set its background to gray
        if (component instanceof JFrame) {
            JFrame frame = (JFrame) component;
            frame.getContentPane().setBackground(Color.DARK_GRAY);
        } else if (component instanceof JPanel) {
            JPanel panel = (JPanel) component;
            panel.setBackground(new Color(100, 160, 255));
        } else {
            throw new IllegalArgumentException("Unsupported component type");
        }
    }
    public static void updatePlayerInfo(Game game, JTextArea textArea){
        Player currentPlayer = game.getCurrentPlayer(); // Get current player from game logic
        StringBuilder info = new StringBuilder("Player Information:\n");
        info.append("Player ").append(playerNames.get(currentPlayer.getUniqueIndex())).append("\n");
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
