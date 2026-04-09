package com.bdtripp.hauntedhouse.model;

/**
 * Represents an exit connecting one room to another.
 *
 * An Exit has a direction, a neighboring room, and an exit type indicating whether it is locked or
 * unlocked.
 *
 * @author Brian Tripp
 */
public class Exit {
    private final Direction direction;
    private final Room neighbor;
    private final ExitType type;

    /**
     * Creates an exit and sets its direction, neighbor, and type.
     * 
     * @param direction The direction of the exit.
     * @param neighbor The neighboring room
     * @param type The type of exit (locked or unlocked)
     */
    public Exit(Direction direction, Room neighbor, ExitType type) {
        this.direction = direction;
        this.neighbor = neighbor;
        this.type = type;
    }

    /**
     * Returns the room that the exit leads to.
     * 
     * @return The room.
     */
    public Room getNeighbor() {
        return neighbor;
    }

    /**
     * Returns the type of exit.
     * 
     * @return The type of exit.
     */
    public ExitType getType() {
        return type;
    }
}
