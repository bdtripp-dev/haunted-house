package com.bdtripp.hauntedhouse.model;

/**
 * Represents the stats of a player in the Haunted House
 * game. This class stores and manages values such as health and strength,
 * keeping player-related state separate from inventory and
 * navigation logic.
 *
 * @author Brian Tripp
 * @version 2026.04.02
 */

public class Stats {
    private int health;
    private int strength;

    /**
     * @param health   The health of the player
     * @param strength The strength of the player
     */
    public Stats(int health, int strength) {
        this.health = health;
        this.strength = strength;
    }

    /**
     * Set the health of the player
     * 
     * @param value The value to set
     */
    public void setHealth(int value) {
        health = value;
    }

    /**
     * Returns the health of the player
     * 
     * @return the health of the player
     */
    public int getHealth() {
        return health;
    }

    /**
     * Set the strength of the player
     * 
     * @param value The value to set
     */
    public void setStrength(int value) {
        strength = value;
    }

    /**
     * Returns the strength of the player
     * 
     * @return the strength of the player
     */
    public int getStrength() {
        return strength;
    }
}