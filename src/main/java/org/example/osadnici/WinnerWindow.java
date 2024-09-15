package org.example.osadnici;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The WinnerWindow class represents a window that displays the winner of the game.
 */
public class WinnerWindow extends JFrame {

    /**
     * Constructs a WinnerWindow object, setting up the initial state and UI components.
     *
     * @param game The Game object that contains the game state and logic.
     */
    public WinnerWindow(Game game) {

        GenericWindow.changeBackground(this);
        JLabel winnerLabel = new JLabel("Winner: " + GenericWindow.playerNames.get(game.getCurrentPlayer().getUniqueIndex()));
        JButton exitButton = new JButton("Exit");

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        setFullscreen();

        setLayout(new GridLayout(2, 1, 0, getHeight() / 2));

        winnerLabel.setFont(new Font("Arial", Font.BOLD, getHeight() / 15));
        winnerLabel.setHorizontalAlignment(JLabel.CENTER);
        winnerLabel.setForeground(Color.PINK);
        add(winnerLabel);
        add(exitButton);


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
     * Displays the WinnerWindow.
     *
     * @param game The Game object that contains the game state and logic.
     */
    public static void display(Game game) {
        SwingUtilities.invokeLater(() -> new WinnerWindow(game));
    }
}