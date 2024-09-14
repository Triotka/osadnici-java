package org.example.osadnici;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class BoardPanel extends JPanel {

    private List<HexTile> tiles;
    Point[ ] vertices;
    private List<JButton> buttons;

    public BoardPanel() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.WHITE);
        tiles = new ArrayList<>();
        vertices = new Point[55];
        buttons = new ArrayList<>();
        createHexagonalBoard();
        createButtons();
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

        setVertecies();
    }

    private void setVertex(int buildIndex, int tileNumber, int pointNumber){

        int xPos = tiles.get(tileNumber).getXPoints()[pointNumber];
        int yPos = tiles.get(tileNumber).getYPoints()[pointNumber];
        vertices[buildIndex] = new Point(xPos, yPos);
    }
    private void setVertecies(){
        vertices[0] = null;

        setVertex(1, 0, 3);
        setVertex(2, 0, 4);
        setVertex(3, 0, 5);
        setVertex(4, 1, 4);
        setVertex(5, 1, 5);
        setVertex(6, 2, 4);
        setVertex(7, 2, 5);
        setVertex(8, 3, 3);
        setVertex(9, 3, 4);
        setVertex(10, 3, 5);
        setVertex(11, 4, 4);
        setVertex(12, 4, 5);
        setVertex(13, 5, 4);
        setVertex(14, 6, 3);
        setVertex(15, 6, 4);
        setVertex(16, 6, 5);
        setVertex(17, 7, 3);
        setVertex(18, 7, 4);
        setVertex(19, 7, 5);
        setVertex(20, 8, 4);
        setVertex(21, 9, 3);
        setVertex(22, 9, 4);
        setVertex(23, 9, 5);
        setVertex(24, 10, 4);
        setVertex(25, 10, 5);
        setVertex(26, 11, 4);
        setVertex(27, 12, 3);
        setVertex(28, 12, 4);
        setVertex(29, 12, 5);
        setVertex(30, 13, 4);
        setVertex(31, 13, 5);
        setVertex(32, 14, 4);
        setVertex(33, 14, 5);
        setVertex(34, 15, 4);
        setVertex(35, 16, 3);
        setVertex(36, 16, 4);
        setVertex(37, 16, 5);
        setVertex(38, 17, 4);
        setVertex(39, 17, 5);
        setVertex(40, 18, 4);
        setVertex(41, 16, 2);
        setVertex(42, 16, 1);
        setVertex(43, 17, 2);
        setVertex(44, 17, 1);
        setVertex(45, 17, 0);
        setVertex(46, 18, 1);
        setVertex(47, 18, 0);
        setVertex(48, 11, 5);
        setVertex(49, 11, 0);
        setVertex(50, 11, 1);
        setVertex(51, 15, 0);
        setVertex(52, 15, 1);
        setVertex(53, 7, 2);
        setVertex(54, 12, 2);
    }
    private void createButtons() {
        setLayout(null);
        int buttonSize = 20; //TODO auto

        for (int i = 1; i < vertices.length; i++) {

            JButton button = new JButton(String.valueOf(i));
            button.setBounds(vertices[i].x - buttonSize / 2, vertices[i].y - buttonSize / 2, buttonSize, buttonSize);

            final int index = i;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                   // callFunction(index); // Call function with button index
                }
            });

            buttons.add(button);
            add(button); // Add button to the panel
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
