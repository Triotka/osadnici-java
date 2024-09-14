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
    private JLabel messageLabel;

    public GameWindow(Game game) {
        this.game = game; // Initialize game logic
        setTitle("Settlers of Catan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        messageLabel = new JLabel("");
        messageLabel.setBounds(20, 20, 300, 30); // Adjust position and size as needed
        messageLabel.setForeground(Color.RED); // Set the color of the message text
        add(messageLabel); // Add the label to the panel

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


        boardPanel = new BoardPanel(game, playerInfoTextArea, messageLabel);



        JButton exitButton = new JButton("Exit Game");
        JButton switchButton = new JButton("Switch Players");


        JButton buyRoadButton = new JButton("Buy Road");
        JButton buyVillageButton = new JButton("Buy Village");
        JButton buyTownButton = new JButton("Buy Town");

        JButton sellLambButton = new JButton("Sell Lamb");
        JButton sellWheatButton = new JButton("Sell Wheat");
        JButton sellWoodButton = new JButton("Sell Wood");
        JButton sellStoneButton = new JButton("Sell Stone");
        JButton sellBrickButton = new JButton("Sell Brick");

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Exit the application
            }
        });
        switchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (game.currentAction == Action.EndTurn){
                    if (game.currentPlayerNum + 1 != game.getPlayersNum()) {
                        game.currentAction = Action.FirstVillage;
                    }
                    else{
                        game.currentAction = Action.RegularTurn;
                    }
                    game.switchPlayers();
                }
                else if (game.currentAction == Action.RegularTurn){
                    game.switchPlayers();
                }
                Text.updatePlayerInfo(game, playerInfoTextArea);
            }
        });
        buyRoadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (game.getCurrentPlayer().buyRoad()){
                    game.currentAction = Action.BuildRoad;
                }
                Text.updatePlayerInfo(game, playerInfoTextArea);

            }
        });
        buyVillageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (game.getCurrentPlayer().buyVillage()){
                    game.currentAction = Action.BuildVillage;
                }
                Text.updatePlayerInfo(game, playerInfoTextArea);
            }
        });
        buyTownButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (game.getCurrentPlayer().buyTown()){
                    game.currentAction = Action.BuildTown;
                }
                Text.updatePlayerInfo(game, playerInfoTextArea);
            }
        });

        controlPanel.add(exitButton);
        controlPanel.add(switchButton);

        controlPanel.add(buyRoadButton);
        controlPanel.add(buyVillageButton);
        controlPanel.add(buyTownButton);

        controlPanel.add(sellBrickButton);
        controlPanel.add(sellLambButton);
        controlPanel.add(sellWoodButton);
        controlPanel.add(sellWheatButton);
        controlPanel.add(sellStoneButton);

        add(boardPanel, BorderLayout.CENTER);
        add(playerInfoPanel, BorderLayout.EAST);
        add(controlPanel, BorderLayout.SOUTH);

        setFullscreen();
        Text.updatePlayerInfo(game, playerInfoTextArea);
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


    public static void display(Game game) {
        SwingUtilities.invokeLater(() -> new GameWindow(game));
    }
}
