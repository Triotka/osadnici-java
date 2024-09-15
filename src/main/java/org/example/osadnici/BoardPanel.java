package org.example.osadnici;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BoardPanel extends JPanel {

    private Integer selectedRoadStart = null;
    private List<HexTile> tiles;
    Point[ ] vertices;
    private List<JButton> buttons;
    private Game game;
    private JTextArea playerInfoTextArea;
    private JLabel messageLabel;

    public BoardPanel(Game game, JTextArea playerInfo, JLabel messageLabel) {
        this.game = game;
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.WHITE);
        tiles = new ArrayList<>();
        vertices = new Point[55];
        buttons = new ArrayList<>();
        this.playerInfoTextArea = playerInfo;
        this.messageLabel = messageLabel;
        createHexagonalBoard();
        createButtons();
        GenericWindow.changeBackground(this);
    }

    private void createHexagonalBoard() {
        int radius = 70;
        int startX = 350;
        int startY = 100;


        var gameTiles = game.GetBoard().tilesList;
       // String[] resourceTypes = {"Forest", "Mountain", "Field", "Pasture", "Hill", "Desert"};
       // int[] numbers = {5, 8, 3, 6, 9, 11, 4, 10, 12, 2, 8, 5, 6, 9, 3, 4, 10};

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
                    var currentTile = gameTiles.get(tileCounter);
                    var resourceType = currentTile.material();
                    int number = currentTile.number();

                    tiles.add(new HexTile(xOffset, yOffset, radius, resourceType, number));
                    tileCounter++;
                }
            }
        }

        setVertices();
    }

    private void setVertex(int buildIndex, int tileNumber, int pointNumber){

        int xPos = tiles.get(tileNumber).getXPoints()[pointNumber];
        int yPos = tiles.get(tileNumber).getYPoints()[pointNumber];
        vertices[buildIndex] = new Point(xPos, yPos);
    }
    private void setVertices(){
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
                    boolean actionSuccessful = false;

                    if (game.currentAction == Action.BuildTown){
                       actionSuccessful =  game.getCurrentPlayer().buildTown(index, game.GetBoard());

                        if (actionSuccessful) {
                            game.currentAction = Action.RegularTurn;
                        }
                    }
                    else if (game.currentAction == Action.BuildVillage){
                        actionSuccessful = game.getCurrentPlayer().buildVillage(index, game.GetBoard());
                        if (actionSuccessful) {
                            game.currentAction = Action.RegularTurn;
                        }
                    }
                    else if (game.currentAction == Action.BuildRoad){
                        if (selectedRoadStart != null){
                            var pos = new Position(selectedRoadStart, index);
                            actionSuccessful = game.getCurrentPlayer().buildRoad(pos, game.GetBoard());
                            selectedRoadStart = null;
                            if (actionSuccessful) {
                                game.currentAction = Action.RegularTurn;
                            }
                        }
                        else{
                            selectedRoadStart = index;
                            actionSuccessful = true;
                        }

                    }

                    else if (game.currentAction == Action.FirstVillage){
                        actionSuccessful = game.getCurrentPlayer().startVillage(index, game.GetBoard());

                        if (actionSuccessful) {
                            game.currentAction = Action.FirstRoad;
                        }
                    }
                    else if (game.currentAction == Action.SecondVillage){
                        actionSuccessful = game.getCurrentPlayer().startVillage(index, game.GetBoard());
                        if (actionSuccessful) {
                            game.currentAction = Action.SecondRoad;
                        }
                    }
                    else if (game.currentAction == Action.FirstRoad){
                        if (selectedRoadStart != null){
                            var pos = new Position(selectedRoadStart, index);
                            actionSuccessful = game.getCurrentPlayer().startRoad(pos, game.GetBoard());
                            selectedRoadStart = null;
                            if (actionSuccessful) {
                                game.currentAction = Action.SecondVillage;
                            }
                        }
                        else{
                            selectedRoadStart = index;
                            actionSuccessful = true;
                        }
                    }
                    else if (game.currentAction == Action.SecondRoad){
                        if (selectedRoadStart != null){
                            var pos = new Position(selectedRoadStart, index);
                           actionSuccessful = game.getCurrentPlayer().startRoad(pos, game.GetBoard());
                            selectedRoadStart = null;
                            if (actionSuccessful) {
                                game.currentAction = Action.EndTurn;
                            }
                        }
                        else{
                            selectedRoadStart = index;
                            actionSuccessful = true;
                        }
                    }

                    if (actionSuccessful) {
                        messageLabel.setText("Building successful!");
                    } else {
                        messageLabel.setText("Building failed! Try a different location.");
                    }

                    GenericWindow.updatePlayerInfo(game, playerInfoTextArea);
                    updateBoard();

                }
            });
            buttons.add(button);
            add(button);
        }
    }
    private void updateBoard() {;
        repaint();
    }


    private void drawVillage(Graphics2D g2d, Integer owner, int index){
        int length = 40; // TODO adjust
        var position = vertices[index];
        var color = GenericWindow.playerColors.get(owner); // TODO check if valid
        drawSquare(g2d, position.x, position.y, length, color);
    }
    private void drawTown(Graphics2D g2d, Integer owner, int index){
        int radius = 40; // TODO adjust
        var position = vertices[index];
        var color = GenericWindow.playerColors.get(owner); // TODO check if valid
        drawCircle(g2d, position.x, position.y, radius, color);
    }
    private void drawBuildings(Graphics2D g2d){
        for (int i = 1; i < vertices.length; i++) {
            var buildSpot = game.GetBoard().buildings.get(i);
            if (buildSpot.type == PawnType.Village) // TODO check off by one error
            {
                drawVillage(g2d, buildSpot.owner, i);
            }
            else if (buildSpot.type == PawnType.Town){
                drawTown(g2d, buildSpot.owner, i);
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
//        if (!tiles.isEmpty()) {
//            HexTile firstTile = tiles.get(0); // Get the first hex tile
//            drawCircle(g2d, firstTile.getXPosition(), firstTile.getYPosition(), 30); // Draw circle at hexagon's center
//             }

        drawBuildings(g2d);
        drawRoads(g2d);

    }


    /**
     * Draws the roads on the board using the Graphics object.
     * Each road is drawn as a line between its start and end node positions.
     * @param g the Graphics object used for drawing
     */
    public void drawRoads(Graphics2D g) {
        // Iterate over all the roads in the board's 'roads' HashMap
        for (Map.Entry<Integer, List<Road>> entry : game.GetBoard().roads.entrySet()) {
            int startNode = entry.getKey();
            List<Road> roadList = entry.getValue();

            // Iterate over each road that starts at the current node
            for (Road road : roadList) {
                int endNode = road.endPosition;

                // Example: draw the road as a line between start and end positions
                // You can map startNode and endNode to coordinates on the board
                var startPos = vertices[startNode]; // Map node to screen coordinates
                var endPos = vertices[endNode];     // Map node to screen coordinates

                // Choose color based on the road's owner (player)
                Color color = (GenericWindow.playerColors.get(road.owner));

                // Draw the road as a line between the two positions
                drawThickLine(g, startPos.x, startPos.y, endPos.x, endPos.y, 10, color);
            }
        }
    }
    /**
     * Draws a circle at the specified (x, y) position with the given radius.
     *
     * @param g Graphics2D object
     * @param x Center X coordinate of the circle
     * @param y Center Y coordinate of the circle
     * @param radius Radius of the circle
     */
    private void drawCircle(Graphics2D g, int x, int y, int radius, Color color) {
        g.setColor(color);

        int topLeftX = x - radius;
        int topLeftY = y - radius;
        g.drawOval(topLeftX, topLeftY, 2 * radius, 2 * radius);
         g.fillOval(topLeftX, topLeftY, 2 * radius, 2 * radius);
    }

    /**
     * Draws a square at the specified (x, y) position with the given side length.
     *
     * @param g Graphics2D object
     * @param x Center X coordinate of the square
     * @param y Center Y coordinate of the square
     * @param sideLength Length of each side of the square
     */
    private void drawSquare(Graphics2D g, int x, int y, int sideLength, Color color) {
        g.setColor(color);
        int topLeftX = x - sideLength / 2;
        int topLeftY = y - sideLength / 2;

        g.drawRect(topLeftX, topLeftY, sideLength, sideLength);
        g.fillRect(topLeftX, topLeftY, sideLength, sideLength);
    }

    /**
     * Draws a thick line between two points (x1, y1) and (x2, y2).
     *
     * @param g Graphics2D object
     * @param x1 Starting X coordinate
     * @param y1 Starting Y coordinate
     * @param x2 Ending X coordinate
     * @param y2 Ending Y coordinate
     * @param thickness The thickness of the line
     */
    private void drawThickLine(Graphics2D g, int x1, int y1, int x2, int y2, float thickness, Color color) {
        g.setColor(color);
        g.setStroke(new BasicStroke(thickness));
        g.drawLine(x1, y1, x2, y2);

        g.setStroke(new BasicStroke()); // back to default
        g.setColor(Color.BLACK);
    }
}
