package org.example.osadnici;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class GameWindow extends JFrame {

    private BoardPanel boardPanel;
    private JPanel playerInfoPanel;
    private JPanel controlPanel;
    private Game game;
    private JTextArea playerInfoTextArea;


    public GameWindow(Game game) {
        this.game = game; // Initialize game logic
        setTitle("Settlers of Catan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        boardPanel = new BoardPanel();
        playerInfoPanel = new JPanel();
        controlPanel = new JPanel();

        playerInfoPanel.setPreferredSize(new Dimension(200, 600));
        playerInfoPanel.setBackground(Color.LIGHT_GRAY);
        playerInfoPanel.setLayout(new BoxLayout(playerInfoPanel, BoxLayout.Y_AXIS));
        JLabel playerLabel = new JLabel("Player Information");
        playerInfoPanel.add(playerLabel);

        playerInfoTextArea = new JTextArea(30, 20);
        playerInfoTextArea.setEditable(false);
        playerInfoPanel.add(new JScrollPane(playerInfoTextArea));

        controlPanel.setPreferredSize(new Dimension(800, 100));
        controlPanel.setBackground(Color.DARK_GRAY);
        JButton buildButton = new JButton("Build");
        JButton exitButton = new JButton("Exit Game");
        JButton switchButton = new JButton("Switch Players");

        JButton sellLambButton = new JButton("Sell Lamb");
        JButton sellWheatButton = new JButton("Sell Wheat");
        JButton sellWoodButton = new JButton("Sell Wood");
        JButton sellStoneButton = new JButton("Sell Stone");
        JButton sellBrickButton = new JButton("Sell Brick");

        // Add action listener to exit button
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Exit the application
            }
        });
        switchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.processCommand("switch");
                updatePlayerInfo();
            }
        });

        controlPanel.add(exitButton);
        controlPanel.add(buildButton);
        controlPanel.add(switchButton);
        controlPanel.add(sellBrickButton);
        controlPanel.add(sellLambButton);
        controlPanel.add(sellWoodButton);
        controlPanel.add(sellWheatButton);
        controlPanel.add(sellStoneButton);

        add(boardPanel, BorderLayout.CENTER);
        add(playerInfoPanel, BorderLayout.EAST);
        add(controlPanel, BorderLayout.SOUTH);

        setFullscreen();
        updatePlayerInfo();
    }

    private void setFullscreen() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (gd.isFullScreenSupported()) {
            setUndecorated(true);
            gd.setFullScreenWindow(this);
        } else {
            setExtendedState(JFrame.MAXIMIZED_BOTH);
        }
    }
    // Method to update player information
    private void updatePlayerInfo() {
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

        playerInfoTextArea.setText(info.toString());
    }

    public static void display(Game game) {
        SwingUtilities.invokeLater(() -> new GameWindow(game));
    }
}
