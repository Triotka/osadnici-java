package org.example.osadnici;
import javax.swing.*;
import java.awt.*;


public class BoardPanel extends JPanel {

    public BoardPanel() {
        setPreferredSize(new Dimension(600, 600));
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.GRAY);
        g.fillRect(100, 100, 100, 100);
        g.drawString("Board goes here", 120, 150);
    }
}
