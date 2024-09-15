package org.example.osadnici;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWindow extends JFrame {

    private final BoardPanel boardPanel;
    private final JPanel playerInfoPanel;
    private final JPanel controlPanel;
    private final Game game;
    private final JTextArea playerInfoTextArea;
    private JLabel messageLabel;


    /**
     * Constructs a GameWindow object, setting up the initial state and UI components.
     *
     * @param game The Game object that contains the game state and logic.
     */
    public GameWindow(Game game) {
        this.game = game;
        setTitle("Settlers of Catan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        GenericWindow.changeBackground(this);
        setFullscreen();

        createMessageLabel();
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


        boardPanel = new BoardPanel(game, playerInfoTextArea, messageLabel, this);



        JButton exitButton = new JButton("Exit Game");
        JButton switchButton = new JButton("Switch Players");
        JButton buyRoadButton = new JButton("Buy Road");
        JButton buyVillageButton = new JButton("Buy Village");
        JButton buyTownButton = new JButton("Buy Town");

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
                        game.switchPlayers();
                    }
                    else{
                        game.currentAction = Action.RegularTurn;
                        game.switchPlayers();
                        messageLabel.setText(game.rollDice()); // print out
                    }

                }
                else if (game.currentAction == Action.RegularTurn){
                    game.switchPlayers();
                    messageLabel.setText(game.rollDice()); // print out
                }
                else{
                    messageLabel.setText("Unable to switch right now");
                }
                GenericWindow.updatePlayerInfo(game, playerInfoTextArea);
            }
        });
        buyRoadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = "Unable to buy road";
                if (game.currentAction == Action.RegularTurn){
                    if (game.getCurrentPlayer().buyRoad()){
                        game.currentAction = Action.BuildRoad;
                        message = "Road bought";
                    }
                }

                messageLabel.setText(message);
                GenericWindow.updatePlayerInfo(game, playerInfoTextArea);

            }
        });
        buyVillageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = "Unable to buy village";
                if (game.currentAction == Action.RegularTurn){
                    if (game.getCurrentPlayer().buyVillage()){
                        game.currentAction = Action.BuildVillage;
                        message = "Village bought";
                    }
                }
                messageLabel.setText(message);
                GenericWindow.updatePlayerInfo(game, playerInfoTextArea);
            }
        });
        buyTownButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = "Unable to buy town";
                if (game.currentAction == Action.RegularTurn){
                    if (game.getCurrentPlayer().buyTown()){
                        game.currentAction = Action.BuildTown;
                        message = "Town bought";
                    }
                }
                messageLabel.setText(message);
                GenericWindow.updatePlayerInfo(game, playerInfoTextArea);
            }
        });

        controlPanel.add(exitButton);
        controlPanel.add(switchButton);
        controlPanel.add(buyRoadButton);
        controlPanel.add(buyVillageButton);
        controlPanel.add(buyTownButton);

        add(boardPanel, BorderLayout.CENTER);
        add(playerInfoPanel, BorderLayout.EAST);
        add(controlPanel, BorderLayout.SOUTH);

        createSellButtons();

        GenericWindow.updatePlayerInfo(game, playerInfoTextArea);
    }
    /**
     * Creates and initializes the message label for displaying game messages.
     */
    private void createMessageLabel(){
        messageLabel = new JLabel("");
        messageLabel.setBounds(getWidth()/64, getHeight()/36, getWidth()/4, getHeight() / 25); // Adjust position and size as needed
        messageLabel.setForeground(Color.RED);
        messageLabel.setFont(new Font("Arial", Font.BOLD, getHeight() / 40));
        add(messageLabel);
    }

    /**
     * Sets the window to fullscreen mode.
     */
    private void setFullscreen() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (gd.isFullScreenSupported()) {
            setUndecorated(true);
            gd.setFullScreenWindow(this);
        } else {
            setExtendedState(JFrame.MAXIMIZED_BOTH);
        }
    }


    /**
     * Creates buttons for selling different materials.
     */
    private void createSellButtons() {
        JPanel sellPanel = new JPanel();
        sellPanel.setLayout(new GridLayout(1, Material.values().length - 1));

        for (Material material: Material.values()) {

            if (material != Material.Desert)
            {
                JButton sellButton = new JButton("Sell " + material.toString());
                sellButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (game.currentAction == Action.RegularTurn){
                            messageLabel.setText("Selling " + material);
                            SwingUtilities.invokeLater(() -> new SellWindow(game, material));
                        }
                        else{
                            messageLabel.setText("Cannot sell right now");
                        }

                    }
                });

                sellPanel.add(sellButton);
            }

        }
        controlPanel.add(sellPanel);

    }
    /**
     * Displays the GameWindow.
     *
     * @param game The Game object that contains the game state and logic.
     */
    public static void display(Game game) {
      SwingUtilities.invokeLater(() -> new GameWindow(game));

    }
}
