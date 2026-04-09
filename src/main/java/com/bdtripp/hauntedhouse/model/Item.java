package com.bdtripp.hauntedhouse.model;

/**
 * Represents an item in the game world.
 *
 * Items have names, descriptions, weights, and optional effects on player statistics. Some items
 * may also be edible.
 *
 * @author Brian Tripp
 */
public class Item {
    private String name;
    private String description;
    private int weight;
    private boolean isEdible;
    private PlayerStat statToAffect;
    private int affectValue;

    /**
     * Creates a new item
     *
     * @param name The name of the item
     * @param description A description of the item
     * @param weight The items weight
     * @param isEdible Whether or not the item is edible
     * @param statToAffect The stat of a player that the item has an affect on (eg. strength)
     * @param affectValue The amount to change the stat's value by
     */
    public Item(String name, String description, int weight, boolean isEdible,
            PlayerStat statToAffect, int affectValue) {
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.isEdible = isEdible;
        this.statToAffect = statToAffect;
        this.affectValue = affectValue;
    }

    /**
     * Returns the name of the item
     * 
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the description of the item
     * 
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the weight of the item
     * 
     * @return The weight
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Returns whether this item is edible or not
     * 
     * @return true if this item is edible
     */
    public boolean isEdible() {
        return isEdible;
    }

    /**
     * Returns the stat the item has an effect on
     * 
     * @return The stat
     */
    public PlayerStat getStatToAffect() {
        return statToAffect;
    }

    /**
     * Returns the amount to change a stat's value by
     * 
     * @return The amount
     */
    public int getAffectValue() {
        return affectValue;
    }
}
