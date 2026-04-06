package com.bdtripp.hauntedhouse.model;

import java.util.ArrayList;

/**
 * Represents a collection of items carried by the player in
 * the Haunted House game. The Inventory is responsible for storing items,
 * enforcing carry-weight limits, and providing operations for adding, removing,
 * and querying items. It encapsulates all item-management logic so that Player
 * remains focused on player-specific behavior.
 * 
 * @author Brian Tripp
 * @version 2026.04.02
 */
public class Inventory {
    private int maxCarryWeight;
    private ArrayList<Item> items;

    /**
     * @param maxCarryWeight The maximum weight the player can carry
     */
    public Inventory(int maxCarryWeight) {
        items = new ArrayList<>();
        this.maxCarryWeight = maxCarryWeight;
    }

    /**
     * @param max The maximum weight a player can carry
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
     * @return The weight of the items that the player is currently carrying
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
     * @return The details of the items in the inventory
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
