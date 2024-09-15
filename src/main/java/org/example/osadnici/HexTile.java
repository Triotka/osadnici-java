package org.example.osadnici;

import java.awt.*;
import java.awt.geom.Path2D;

/**
 * The class represents a UI hexagonal tile drawing used in the game.
 */
public class HexTile {

    private final int radius;
    private final int x;
    private final int y;
    private final Material resourceType;
    private final int number;
    private int[] xPoints;
    private int[] yPoints;

    /**
     * Constructs a HexTile object with a specified position, radius, resource type, and number.
     *
     * @param x            The x-coordinate of the center of the hexagon.
     * @param y            The y-coordinate of the center of the hexagon.
     * @param radius       The radius of the hexagon.
     * @param resourceType The type of resource this hexagon provides.
     * @param number       The number associated with this hexagon.
     */
    public HexTile(int x, int y, int radius, Material resourceType, int number) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.resourceType = resourceType;
        this.number = number;

        // Calculate points immediately after creating
        calculateVertexPoints();
    }

    /**
     * Gets the x-coordinates of the vertices of the hexagon.
     *
     * @return An array of x-coordinates of the hexagon vertices.
     */
    public int[] getXPoints() {
        return xPoints;
    }

    /**
     * Gets the y-coordinates of the vertices of the hexagon.
     *
     * @return An array of y-coordinates of the hexagon vertices.
     */
    public int[] getYPoints() {
        return yPoints;
    }

    /**
     * Calculates the vertex points of the hexagon based on its center and radius.
     */
    private void calculateVertexPoints() {
        xPoints = new int[6];
        yPoints = new int[6];
        for (int i = 0; i < 6; i++) {
            double angle = Math.toRadians(30 + 60 * i); // Start at 30 degrees for flat-top hexagon
            xPoints[i] = (int) (x + radius * Math.cos(angle));
            yPoints[i] = (int) (y + radius * Math.sin(angle));
        }
    }
    /**
     * Draws the hexagon on a given Graphics2D object.
     *
     * @param g The Graphics2D object for drawing the hexagon.
     */
    public void draw(Graphics2D g) {
        Path2D hexagon = createHexagonPath();

        // Fill hexagon based on resource type
        switch (resourceType) {
            case Material.Wood:
                g.setColor(new Color(34, 139, 34)); // Forest Green
                break;
            case Material.Stone:
                g.setColor(new Color(169, 169, 169)); // Grey for mountains
                break;
            case Material.Wheat:
                g.setColor(new Color(255, 215, 0)); // Yellow for fields
                break;
            case Material.Sheep:
                g.setColor(new Color(144, 238, 144)); // Light green for pasture
                break;
            case Material.Brick:
                g.setColor(new Color(205, 92, 92)); // Red for hills
                break;
            case Material.Desert:
                g.setColor(new Color(210, 180, 140)); // Tan for desert
                break;
            default:
                g.setColor(Color.LIGHT_GRAY);
        }

        g.fill(hexagon);
        g.setColor(Color.BLACK);
        g.draw(hexagon);

        g.setFont(new Font("Arial", Font.BOLD, 24));
        FontMetrics metrics = g.getFontMetrics();

        // Draw the number in the center
        int textX = x - metrics.stringWidth(String.valueOf(number)) / 2;
        int textY = y + metrics.getHeight() / 3;  // Adjusted to center vertically
        g.drawString(String.valueOf(number), textX, textY);
    }

    /**
     * Creates a hexagonal path for drawing (rotated for a flat-top hexagon).
     *
     * @return A Path2D object representing the hexagonal path.
     */
    private Path2D createHexagonPath() {
        Path2D hex = new Path2D.Double();
        for (int i = 0; i < 6; i++) {
            double angle = Math.toRadians(30 + 60 * i); // Start at 30 degrees for flat-top hexagon
            int xOffset = (int) (x + radius * Math.cos(angle));
            int yOffset = (int) (y + radius * Math.sin(angle));
            if (i == 0) {
                hex.moveTo(xOffset, yOffset);
            } else {
                hex.lineTo(xOffset, yOffset);
            }
        }
        hex.closePath();
        return hex;
    }
}