package com.bdtripp.hauntedhouse.model;

/**
 * Represents an exit in the Haunted House game. Exits are associated with a particular room and
 * allow the character to move from one room to the next. An exit can be located at one of four
 * directions - north, east, south, or west. Exits can be either locked or unlocked.
 *
 * @author Brian Tripp
 * @version 2020.06.13
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
