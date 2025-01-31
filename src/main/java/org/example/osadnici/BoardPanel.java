package org.example.osadnici;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The `BoardPanel` a component for rendering the game board.
 * It handles the layout and rendering of hexagonal tiles, vertices, buttons, and player buildings and roads.
 */
public class BoardPanel extends JPanel {

    private Integer selectedRoadStart = null;
    private final List<HexTile> tiles;
    Point[] vertices;
    private final List<JButton> buttons;

    private final JFrame currentWindow;
    private final Game game;
    private final JTextArea playerInfoTextArea;
    private final JLabel messageLabel;

    /**
     * Constructor for the BoardPanel. Initializes various components and sets up the game board.
     *
     * @param game            The current game instance.
     * @param playerInfo      The JTextArea to display player information.
     * @param messageLabel    The JLabel to display messages.
     * @param window          The JFrame current window
     */
    public BoardPanel(Game game, JTextArea playerInfo, JLabel messageLabel, JFrame window) {
        this.game = game;
        setPreferredSize(new Dimension((int) (getWidth() / 1.5), (int) (600 / 1.2)));
        setBackground(Color.WHITE);
        tiles = new ArrayList<>();
        vertices = new Point[55];
        buttons = new ArrayList<>();
        currentWindow = window;
        this.playerInfoTextArea = playerInfo;
        this.messageLabel = messageLabel;
        createHexagonalBoard();
        createButtons();
        GenericWindow.changeBackground(this);
    }

    /**
     * Creates a hexagonal board layout and initializes the tiles based on the game configuration.
     */
    private void createHexagonalBoard() {
        int radius = 70;
        int startX = 350;
        int startY = 100;

        var gameTiles = game.getBoard().tilesList;

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
                    int xOffset = startX + (int) (col * hexHorizontalSpacing);
                    int yOffset = startY + (int) (row * hexVerticalSpacing);

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

    /**
     * Sets vertices positions for the game board.
     *
     * @param buildIndex The index to set in the vertices array.
     * @param tileNumber The index of the tile in the tiles list.
     * @param pointNumber The index of the point in the tile's points array.
     */
    private void setVertex(int buildIndex, int tileNumber, int pointNumber) {
        int xPos = tiles.get(tileNumber).getXPoints()[pointNumber];
        int yPos = tiles.get(tileNumber).getYPoints()[pointNumber];
        vertices[buildIndex] = new Point(xPos, yPos);
    }

    /**
     * Initializes the vertices for the game board based on the layout.
     */
    private void setVertices() {
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

    /**
     * Creates buttons for the user to interact with the game board.
     */
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

                    if (game.currentAction == Action.BuildTown) {
                        actionSuccessful = game.getCurrentPlayer().buildTown(index, game.getBoard());
                        if (actionSuccessful) {
                            game.currentAction = Action.RegularTurn;
                        }
                    } else if (game.currentAction == Action.BuildVillage) {
                        actionSuccessful = game.getCurrentPlayer().buildVillage(index, game.getBoard());
                        if (actionSuccessful) {
                            game.currentAction = Action.RegularTurn;
                        }
                    } else if (game.currentAction == Action.BuildRoad) {
                        if (selectedRoadStart != null) {
                            var pos = new Position(selectedRoadStart, index);
                            actionSuccessful = game.getCurrentPlayer().buildRoad(pos, game.getBoard());
                            selectedRoadStart = null;
                            if (actionSuccessful) {
                                game.currentAction = Action.RegularTurn;
                            }
                        } else {
                            selectedRoadStart = index;
                            actionSuccessful = true;
                        }
                    } else if (game.currentAction == Action.FirstVillage) {
                        actionSuccessful = game.getCurrentPlayer().startVillage(index, game.getBoard());
                        if (actionSuccessful) {
                            game.currentAction = Action.FirstRoad;
                        }
                    } else if (game.currentAction == Action.SecondVillage) {
                        actionSuccessful = game.getCurrentPlayer().startVillage(index, game.getBoard());
                        if (actionSuccessful) {
                            game.currentAction = Action.SecondRoad;
                        }
                    } else if (game.currentAction == Action.FirstRoad) {
                        if (selectedRoadStart != null) {
                            var pos = new Position(selectedRoadStart, index);
                            actionSuccessful = game.getCurrentPlayer().startRoad(pos, game.getBoard());
                            selectedRoadStart = null;
                            if (actionSuccessful) {
                                game.currentAction = Action.SecondVillage;
                            }
                        } else {
                            selectedRoadStart = index;
                            actionSuccessful = true;
                        }
                    } else if (game.currentAction == Action.SecondRoad) {
                        if (selectedRoadStart != null) {
                            var pos = new Position(selectedRoadStart, index);
                            actionSuccessful = game.getCurrentPlayer().startRoad(pos, game.getBoard());
                            selectedRoadStart = null;
                            if (actionSuccessful) {
                                game.currentAction = Action.EndTurn;
                            }
                        } else {
                            selectedRoadStart = index;
                            actionSuccessful = true;
                        }
                    }

                    updateBoard();
                    if (game.getCurrentPlayer().evaluateWinner()){
                        WinnerWindow.display(game);
                        currentWindow.dispose();
                    }

                    GenericWindow.updatePlayerInfo(game, playerInfoTextArea);

                    if (actionSuccessful) {
                        messageLabel.setText("Building successful!");
                    } else {
                        messageLabel.setText("Building failed! Try a different location.");
                    }
                }
            });
            buttons.add(button);
            add(button);
        }
    }

    /**
     * Updates and repaints the board.
     */
    private void updateBoard() {
        repaint();
    }

    /**
     * Draws a village on the board.
     *
     * @param g2d    The Graphics2D object used for drawing.
     * @param owner  The owner of the village.
     * @param index  The index of the vertex where the village is drawn.
     */
    private void drawVillage(Graphics2D g2d, Integer owner, int index) {
        int length = getHeight() / 18;
        var position = vertices[index];
        var color = GenericWindow.playerColors.get(owner);
        drawSquare(g2d, position.x, position.y, length, color);
    }

    /**
     * Draws a town on the board.
     *
     * @param g2d    The Graphics2D object used for drawing.
     * @param owner  The owner of the town.
     * @param index  The index of the vertex where the town is drawn.
     */
    private void drawTown(Graphics2D g2d, Integer owner, int index) {
        int radius = getHeight() / 18;
        var position = vertices[index];
        var color = GenericWindow.playerColors.get(owner);
        drawCircle(g2d, position.x, position.y, radius, color);
    }

    /**
     * Draws all buildings (villages and towns) on the board.
     *
     * @param g2d The Graphics2D object used for drawing.
     */
    private void drawBuildings(Graphics2D g2d) {
        for (int i = 1; i < vertices.length; i++) {
            var buildSpot = game.getBoard().buildings.get(i);
            if (buildSpot.type == PawnType.Village) {
                drawVillage(g2d, buildSpot.owner, i);
            } else if (buildSpot.type == PawnType.Town) {
                drawTown(g2d, buildSpot.owner, i);
            }
        }
    }

    /**
     * Paints the component, including the hexagonal tiles and buildings.
     *
     * @param g The Graphics object used for drawing.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (HexTile tile : tiles) {
            tile.draw(g2d);
        }
        drawBuildings(g2d);
        drawRoads(g2d);
    }

    /**
     * Draws the roads on the board using the Graphics object.
     * Each road is drawn as a line between its start and end node positions.
     *
     * @param g the Graphics object used for drawing
     */
    public void drawRoads(Graphics2D g) {
        for (Map.Entry<Integer, List<Road>> entry : game.getBoard().roads.entrySet()) {
            int startNode = entry.getKey();
            List<Road> roadList = entry.getValue();

            for (Road road : roadList) {
                int endNode = road.endPosition;

                var startPos = vertices[startNode];
                var endPos = vertices[endNode];

                Color color = (GenericWindow.playerColors.get(road.owner));

                drawThickLine(g, startPos.x, startPos.y, endPos.x, endPos.y, (float) getHeight() / 40, color);
            }
        }
    }

    /**
     * Draws a circle at the specified (x, y) position with the given radius.
     *
     * @param g      Graphics2D object
     * @param x      Center X coordinate of the circle
     * @param y      Center Y coordinate of the circle
     * @param radius Radius of the circle
     * @param color  The color to fill the circle
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
     * @param g           Graphics2D object
     * @param x           Center X coordinate of the square
     * @param y           Center Y coordinate of the square
     * @param sideLength  Length of each side of the square
     * @param color       The color to fill the square
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
     * @param g         Graphics2D object
     * @param x1        Starting X coordinate
     * @param y1        Starting Y coordinate
     * @param x2        Ending X coordinate
     * @param y2        Ending Y coordinate
     * @param thickness The thickness of the line
     * @param color     The color of the line
     */
    private void drawThickLine(Graphics2D g, int x1, int y1, int x2, int y2, float thickness, Color color) {
        g.setColor(color);
        g.setStroke(new BasicStroke(thickness));
        g.drawLine(x1, y1, x2, y2);

        g.setStroke(new BasicStroke()); // back to default
        g.setColor(Color.BLACK);
    }
}