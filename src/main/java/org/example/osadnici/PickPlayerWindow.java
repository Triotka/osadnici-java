package org.example.osadnici;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PickPlayerWindow extends JFrame {
    public PickPlayerWindow (Game game) {

        JButton twoButton = new JButton("2 Players");
        JButton threeButton = new JButton("3 Players");
        JButton fourButton = new JButton("4 Players");
        JButton cancelButton = new JButton("Cancel");


        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        twoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.setPlayers(2);
                GameWindow.display(game);
            }
        });
        threeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.setPlayers(3);
                GameWindow.display(game);
            }
        });
        fourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.setPlayers(4);
                GameWindow.display(game);
            }
        });

        add(twoButton);
        add(threeButton);
        add(fourButton);
        add(cancelButton);

        setFullscreen();
        setLayout(new GridLayout(4, 1));
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
        SwingUtilities.invokeLater(() -> new PickPlayerWindow(game));
    }
}
