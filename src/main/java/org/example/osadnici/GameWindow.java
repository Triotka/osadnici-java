package org.example.osadnici;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWindow extends JFrame {

    private BoardPanel boardPanel;
    private JPanel playerInfoPanel;
    private JPanel controlPanel;

    public GameWindow() {
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

        controlPanel.setPreferredSize(new Dimension(800, 100));
        controlPanel.setBackground(Color.DARK_GRAY);
        JButton rollDiceButton = new JButton("Roll Dice");
        JButton buildButton = new JButton("Build");
        JButton exitButton = new JButton("Exit"); // Exit button

        // Add action listener to exit button
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Exit the application
            }
        });

        controlPanel.add(exitButton);
        controlPanel.add(rollDiceButton);
        controlPanel.add(buildButton);

        add(boardPanel, BorderLayout.CENTER);
        add(playerInfoPanel, BorderLayout.EAST);
        add(controlPanel, BorderLayout.SOUTH);

        setFullscreen();
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

    public static void display() {
        SwingUtilities.invokeLater(GameWindow::new);
    }
}
