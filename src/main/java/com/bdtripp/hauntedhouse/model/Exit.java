package com.bdtripp.hauntedhouse;

/**
 * Represents an exit in the Haunted House game. Exits are associated with a 
 * particular room and allow the character to move from one room to the next.
 * An exit can be located at one of four directions - north, east, south, or west. 
 * Exits can be either locked or unlocked.
 *
 * @author Brian Tripp
 * @version 2020.06.13
 */
public class Exit
{
    private String direction;
    private Room neighbor;
    private boolean locked;
    /**
     * Creates an exit and sets its direction, neighbor, and whether or not it is locked. 
     * @param direction The direction of the exit.
     * @param neighbor The neighboring room
     * @param locked Whether or not the exit is locked
     */
    public Exit(String direction, Room neighbor, boolean locked)
    {
        this.direction = direction;
        this.neighbor = neighbor;
        this.locked = locked;
    }

    /**
     * Returns the room that the exit leads to.
     * @return The room.
     */
    public Room getNeighbor()
    {
        return neighbor;
    }

    /**
     * Checks if the exit is locked.
     * @return true if the exit is locked, otherwise false
     */
    public boolean isLocked()
    {
        return locked;
    }
}

