package org.example.osadnici;

/**
 * Represents a building on the board.
 * A building can be of a specific type (village or town) and owned by a player.
 */
public class Building {
    /** The type of the building (village or town). */
    public PawnType type;

    /** The owner of the building. */
    public Integer owner;

    /**
     * Constructs a Building with the specified type and owner.
     *
     * @param type  the type of the building
     * @param owner the owner of the building
     */
    public Building(PawnType type, Integer owner) {
        this.owner = owner;
        this.type = type;
    }
}
