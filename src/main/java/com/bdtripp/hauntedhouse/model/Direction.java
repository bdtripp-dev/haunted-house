package com.bdtripp.hauntedhouse.model;

/**
 * Represents the four cardinal directions used for room navigation
 * within the Haunted House game.
 * 
 * @author Brian Tripp
 * @version 2026.03.31
 */
public enum Direction {

    /** North (comments are here to satisfy Maven Javadoc plugin */
    NORTH,

    /** South */
    SOUTH,

    /** East */
    EAST,

    /** West */
    WEST;

    /**
     * Converts a raw input string into a {@link Direction}. The comparison
     * is case-insensitive. If the input does not match any direction,
     * this method returns {@code null}.
     *
     * @param s the input string to convert
     * @return the matching {@link Direction}, or {@code null} if no match exists
     */
    public static Direction fromString(String s) {
        if (s == null)
            return null;
        try {
            return Direction.valueOf(s.toUpperCase());
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    /**
     * Returns the enum name in lowercase for display and user-facing output.
     */
    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
