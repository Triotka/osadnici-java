package org.example.osadnici;

/**
 * Represents a position for item bigger than one index on board
 * Class stores index where it starts and where it ends
 */
public class Position {
    public int start;
    public int end;

    /**
     * Constructs a Position object with the given start.
     *
     * @param start the start index on board
     * @param end   the end index on board
     */
    public Position(int start, int end) {
        this.start = start;
        this.end = end;
    }
}
