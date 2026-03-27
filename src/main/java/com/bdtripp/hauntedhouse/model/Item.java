package com.bdtripp.hauntedhouse;

/**
 *  Represents an item in the Haunted House game.
 *
 * Items are found in rooms and can help players in various ways. Some items 
 * may be too heavy to pick up.
 *
 * @author Brian Tripp
 * @version 2020.06.13
 */
public class Item
{
    private String name;
    private String description;
    private int weight;
    private boolean isEdible;
    private String propToAffect;
    private int affectValue;

    /**
     * @param name The name of the item
     * @param description A description of the item
     * @param weight The items weight
     * @param isEdible Whether or not the item is edible
     * @param propToAffect The property of a player that the item has an affect on (eg. strength)
     * @param affectValue The amount to change the property's value by
     */
    public Item(
        String name, 
        String description, 
        int weight, 
        boolean isEdible, 
        String propToAffect, 
        int affectValue
    )
    {
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.isEdible = isEdible;
        this.propToAffect = propToAffect;
        this.affectValue = affectValue;
    }

    /**
     * Returns the name of the item
     * @return The name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns the description of the item
     * @return The description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Returns the weight of the item
     * @return The weight
     */
    public int getWeight()
    {
        return weight;
    }

    /**
     * Returns whether this item is edible or not
     * @return true if this item is edible
     */
    public boolean isEdible()
    {
        return isEdible;
    }

    /** Returns the property the itme has an effect on
     * @return The property
     */
    public String getPropToAffect() {
        return propToAffect;
    }

    /** Returns the amount to change a property's value by
     * @return The amount
     */
    public int getAffectValue() {
        return affectValue;
    }
}
