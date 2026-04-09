package com.bdtripp.hauntedhouse.model;

import java.util.ArrayList;

/**
 * Represents a collection of items carried by the player.
 *
 * The inventory stores items, enforces carry‑weight limits, and provides operations for adding,
 * removing, and querying items.
 *
 * @author Brian Tripp
 */
public class Inventory {
    private int maxCarryWeight;
    private ArrayList<Item> items;

    /**
     * Creates a new inventory with the specified maximum carry weight.
     *
     * @param maxCarryWeight the maximum weight the player can carry
     */
    public Inventory(int maxCarryWeight) {
        items = new ArrayList<>();
        this.maxCarryWeight = maxCarryWeight;
    }

    /**
     * Updates the maximum weight the player is allowed to carry.
     *
     * @param max the new maximum carry weight
     */
    public void setMaxCarryWeight(int max) {
        maxCarryWeight = max;
    }

    /**
     * Returns the maximum weight that the player can carry
     * 
     * @return The maximum weight
     */
    public int getMaxCarryWeight() {
        return maxCarryWeight;
    }

    /**
     * Adds an item to the inventory
     * 
     * @param item The item add
     */
    public void addItem(Item item) {
        items.add(item);
    }

    /**
     * Removes an item from the inventory
     * 
     * @param item The item to remove
     * @return The item that was removed
     */
    public Item removeItem(Item item) {
        if (items.remove(item)) {
            return item;
        }
        return null;
    }

    /**
     * Calculates the total weight of all items currently in the inventory.
     *
     * @return the combined weight of all carried items
     */
    public int getCurrentCarryWeight() {
        int totalWeight = 0;
        for (Item item : items) {
            totalWeight += item.getWeight();
        }
        return totalWeight;
    }

    /**
     * Returns an item in the inventory
     * 
     * @param name The name of the item
     * @return The item
     */
    public Item findItem(String name) {
        for (Item item : items) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    /**
     * Checks if a particular item is in the inventory
     * 
     * @param item The item to check
     * @return true if the item is in the inventory
     */
    public boolean hasItem(Item item) {
        return items.contains(item);
    }

    /**
     * Produces a formatted description of all items in the inventory.
     *
     * @return a multi-line string listing item names, descriptions, and weights
     */
    public String getItemDetails() {
        if (items.isEmpty()) {
            return "There are no items in your posession.";
        }
        StringBuilder buffer = new StringBuilder("You are carrying the following:\n\n");
        for (Item item : items) {
            buffer.append("Name: ").append(item.getName()).append("\n");
            buffer.append("Description: ").append(item.getDescription()).append("\n");
            buffer.append("Weight: ").append(item.getWeight()).append("\n\n");
        }
        buffer.append("Total Weight: ").append(getCurrentCarryWeight());
        return buffer.toString();
    }
}