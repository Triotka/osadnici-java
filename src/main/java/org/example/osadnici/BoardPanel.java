package org.example.osadnici;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BoardPanel extends JPanel {

    private List<HexTile> tiles;

    public BoardPanel() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.WHITE);
        tiles = new ArrayList<>();
        createHexagonalBoard();
    }

    private void createHexagonalBoard() {
        int radius = 70;
        int startX = 350;
        int startY = 100;

        String[] resourceTypes = {"Forest", "Mountain", "Field", "Pasture", "Hill", "Desert"};
        int[] numbers = {5, 8, 3, 6, 9, 11, 4, 10, 12, 2, 8, 5, 6, 9, 3, 4, 10};

        int[][] layout = {
                {0, 1, 1, 1, 0},
                {1, 1, 1, 1, 0},
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 0},
                {0, 1, 1, 1, 0}
        };


        double hexHorizontalSpacing = 1.7 * radius;

        double hexVerticalSpacing = 1.5 * radius;

        int tileCounter = 0;


        for (int row = 0; row < layout.length; row++) {
            for (int col = 0; col < layout[row].length; col++) {
                if (layout[row][col] == 1) {
                    // Calculate the X and Y positions
                    int xOffset = startX + (int) (col * hexHorizontalSpacing);
                    int yOffset = startY + (int) (row * hexVerticalSpacing);

                    // Offset odd rows by half a hexagon's width
                    if (row % 2 != 0) {
                        xOffset += radius * 0.85;
                    }
                    String resourceType = resourceTypes[tileCounter % resourceTypes.length];
                    int number = numbers[tileCounter % numbers.length];

                    tiles.add(new HexTile(xOffset, yOffset, radius, resourceType, number));
                    tileCounter++;
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (HexTile tile : tiles) {
            tile.draw(g2d);
        }
    }
}
