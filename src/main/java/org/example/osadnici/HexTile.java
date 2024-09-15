package org.example.osadnici;

import java.awt.*;
import java.awt.geom.Path2D;

public class HexTile {

    private final int radius;
    private final int x;
    private final int y;
    private final Material resourceType;
    private final int number;
    private int[] xPoints;
    private int[] yPoints;

    public HexTile(int x, int y, int radius, Material resourceType, int number) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.resourceType = resourceType;
        this.number = number;

        // Calculate points immediately upon creation
        calculateVertexPoints();
    }

    public int getYPosition() {
        return y;
    }

    public int getXPosition() {
        return x;
    }

    public int[] getXPoints() {
        return xPoints;
    }

    public int[] getYPoints() {
        return yPoints;
    }

    private void calculateVertexPoints() {
        xPoints = new int[6];
        yPoints = new int[6];
        for (int i = 0; i < 6; i++) {
            double angle = Math.toRadians(30 + 60 * i); // Start at 30 degrees for flat-top hexagon
            xPoints[i] = (int) (x + radius * Math.cos(angle));
            yPoints[i] = (int) (y + radius * Math.sin(angle));
        }
    }

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

    // Create a hexagonal path for drawing (rotated for flat-top)
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