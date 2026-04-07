package com.bdtripp.hauntedhouse.model;

/**
 * Represents the PlayerStat of the Haunted House game.
 * 
 * Enumerates all the possible player stats.
 * 
 * @author Brian Tripp
 * @version 2026.04.02
 */
public enum PlayerStat {
    NONE,
    HEALTH,
    STRENGTH,
    MAX_CARRY_WEIGHT;

    @Override
    public String toString() {
        return name().replace('_', ' ').toLowerCase();
    }
}