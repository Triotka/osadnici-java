package org.example.osadnici;

/**
 * Represents a road on the game board.
 */
public class Road {
    public int startPosition;
    public int endPosition;
    public int owner;

    /**
     * Constructs a Road object with the given start, end positions, and owner.
     *
     * @param start the start position of the road
     * @param end   the end position of the road
     * @param owner the owner of the road
     */
    public Road(int start, int end, int owner) {
        startPosition = start;
        endPosition = end;
        this.owner = owner;
    }
}
