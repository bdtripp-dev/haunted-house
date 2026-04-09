package com.bdtripp.hauntedhouse.model;

/**
 * Represents the different player statistics that can be affected within the Haunted House game.
 *
 * <p>
 * Enumerates all supported stat types.
 * </p>
 * 
 * @author Brian Tripp
 */
public enum PlayerStat {

    /** Indicates that the item does not affect any player stat. */
    NONE,

    /** The player's health stat. */
    HEALTH,

    /** The player's strength stat. */
    STRENGTH,

    /** The player's maximum carry weight stat. */
    MAX_CARRY_WEIGHT;

    /**
     * Returns the enum name in lowercase with underscores replaced by spaces.
     *
     * @return the formatted stat name
     */
    @Override
    public String toString() {
        return name().replace('_', ' ').toLowerCase();
    }
}
