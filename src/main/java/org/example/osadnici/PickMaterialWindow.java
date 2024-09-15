package org.example.osadnici;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The PickMaterialWindow class represents a window where players can pick a material
 * in exchange for a sold material. This window is shown during selling.
 */
public class PickMaterialWindow extends JFrame {
    private final Game game;
    private final Integer playerPicked;

    private final Material soldMaterial;

    /**
     * Constructs a PickMaterialWindow object, setting up the initial state and UI components.
     *
     * @param game         The Game object that contains the game state and logic.
     * @param soldMaterial The material that the player is selling.
     * @param playerPicked The index of the player who is making the trade.
     */
    public PickMaterialWindow (Game game, Material soldMaterial, int playerPicked) {

        this.game = game;
        this.soldMaterial = soldMaterial;
        this.playerPicked = playerPicked;


        add(createMaterialButtons());
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
     * Creates buttons for each material available to trade.
     *
     * @return A JPanel containing the material buttons.
     */
    private JPanel createMaterialButtons() {
        JPanel materialButtons = new JPanel();
        materialButtons.setLayout(new GridLayout(game.getPlayersNum(), 1));


        var playersCards = game.getPlayer(this.playerPicked).getCardsList();
        for (var material: playersCards.keySet()) {
            if (playersCards.get(material).numberOfCards != 0){
                JButton button = new JButton(material.toString());
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        game.getCurrentPlayer().sell(material, soldMaterial, game.getPlayer(playerPicked));
                        GameWindow.display(game);
                        dispose();
                    }

                });
                materialButtons.add(button);
            }
        }
        return materialButtons;
    }
}
