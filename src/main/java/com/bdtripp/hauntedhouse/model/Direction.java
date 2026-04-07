package com.bdtripp.hauntedhouse.model;

/**
 * Represents a direction in the Haunted House game.
 * 
 * Enumerates all the possible directions.
 * 
 * @author Brian Tripp
 * @version 2026.03.31
 */
public enum Direction {
    NORTH, SOUTH, EAST, WEST;

    public static Direction fromString(String s) {
        if (s == null)
            return null;
        try {
            return Direction.valueOf(s.toUpperCase());
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
