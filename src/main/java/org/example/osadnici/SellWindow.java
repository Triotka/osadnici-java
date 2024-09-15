package org.example.osadnici;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


/**
 * The SellWindow class represents a window where current player can choose to whom he wants to sell to.
 * It is displayed when a player wants to sell a material.
 */
public class SellWindow extends JFrame {
    private final Game game;

    private final JPanel playerButtons;
    private final Material soldMaterial;

    /**
     * Constructs a SellWindow object, setting up the initial state and UI components.
     *
     * @param game         The Game object that contains the game state and logic.
     * @param soldMaterial The material that the player is selling.
     */
    public SellWindow (Game game, Material soldMaterial) {

        this.game = game;
        this.soldMaterial = soldMaterial;



        playerButtons = createPlayerButtons();
        add(playerButtons);
        JButton cancelButton = new JButton("Cancel");



        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameWindow.display(game);
                dispose();
            }
        });

        add(cancelButton);

        setFullscreen();
        setLayout(new GridLayout(4, 1));
        setVisible(true);
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
     * Creates buttons for each player in the game.
     *
     * @return A JPanel containing the player buttons.
     */
    private JPanel createPlayerButtons() {
        JPanel playerButtons = new JPanel();

        playerButtons.setLayout(new GridLayout(game.getPlayersNum(), 1));

        for (int i = 0; i < game.getPlayersNum(); i++) {

            JButton button = new JButton("Player " + GenericWindow.playerNames.get(i));


            final int playerPicked = i;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SwingUtilities.invokeLater(() -> new PickMaterialWindow(game, soldMaterial, playerPicked));
                    dispose();
                }

            });
            playerButtons.add(button);
        }
        return playerButtons;
    }


}
