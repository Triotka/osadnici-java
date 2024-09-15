package org.example.osadnici;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

/**
 * The GenericWindow class contains methods useful for more windows such as changing background updating player information in the game.
 */
public class GenericWindow {
    public static final List<Color> playerColors = List.of(Color.YELLOW, Color.WHITE, Color.RED, Color.BLUE );
    public static final List<String> playerNames = List.of("Yellow", "White", "Red", "Blue" );

    /**
     * Changes the background color of the given component.
     *
     * @param component The component whose background color is to be changed.
     * @param <T>       The type of the component (either JFrame or JPanel).
     */
    public static <T> void changeBackground(T component){
        if (component instanceof JFrame frame) {
            frame.getContentPane().setBackground(Color.DARK_GRAY);
        } else if (component instanceof JPanel panel) {
            panel.setBackground(new Color(100, 160, 255));
        } else {
            throw new IllegalArgumentException("Unsupported component type");
        }
    }

    /**
     * Updates the player information in the given JTextArea.
     *
     * @param game      The Game object that contains the game state and logic.
     * @param textArea  The JTextArea where the player information should be displayed.
     */
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
