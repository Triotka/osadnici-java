package org.example.osadnici;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WinnerWindow extends JFrame {
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
        SwingUtilities.invokeLater(() -> new WinnerWindow(game));
    }
}