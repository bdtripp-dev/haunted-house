package com.bdtripp.hauntedhouse.model;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Represents a player in the Haunted House game. Keeps track of where the
 * player
 * is, what items they are carrying, and their current stats.
 *
 * @author Brian Tripp
 * @version 2026.04.02
 */
public class Player {
    private String name;
    private int maxCarryWeight;
    private int health = 100;
    private int strength = 10;
    private ArrayList<Item> items;
    private Room currentRoom;
    private Stack<Room> roomHistory;
    private static final int MAX_MOVES_ALLOWED = 30;
    private Room beamerLocation;
    private boolean beamerCharged;

    /**
     * @param name           The name of the player
     * @param maxCarryWeight The maximum weight the player can carry
     */
    public Player(String name, int maxCarryWeight) {
        items = new ArrayList<>();
        this.maxCarryWeight = maxCarryWeight;
        this.name = name;
        roomHistory = new Stack<>();
    }

    /**
     * @param value True if the beamer is charged, otherwise false
     */
    public void setBeamerCharge(boolean value) {
        beamerCharged = value;
    }

    /**
     * Returns the status of the beamer charge
     * 
     * @return true if the beamer is charged
     */
    public boolean getBeamerCharge() {
        return beamerCharged;
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
     * Moves the player to the specified room
     * 
     * @param room         The room to move the player to
     * @param addToHistory true if current room should be added to history
     */
    public void moveToRoom(Room room, boolean addToHistory) {
        if (addToHistory) {
            updateRoomHistory();
        }
        currentRoom = room;
    }

    /**
     * Adds the current room to the room history
     */
    public void updateRoomHistory() {
        roomHistory.push(currentRoom);
    }

    /**
     * @return The history of the rooms the player has been in
     */
    public Stack<Room> getRoomHistory() {
        return roomHistory;
    }

    /**
     * @return The room the player is currently in
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }

    /**
     * Returns the previous room and removes it from the room history
     * 
     * @return The previous room
     */
    public Room getPreviousRoom() {
        return roomHistory.pop();
    }

    /**
     * Adds an item to the list of what the player is carrying
     * 
     * @param item The item to take
     */
    public void takeItem(Item item) {
        items.add(item);
    }

    /**
     * Removes an item from the list of what the player is currently carrying
     * 
     * @param name The name of the item to drop
     * @return The item that was dropped
     */
    public Item dropItem(String name) {
        for (Item item : items) {
            if (item.getName().equals(name)) {
                items.remove(item);
                return item;
            }
        }
        return null;
    }

    /**
     *
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
     * @return The items the player is carrying
     */
    public ArrayList<Item> getItems() {
        return items;
    }

    /**
     * Returns an item that the player is carrying
     * 
     * @param name The name of the item
     * @return The item
     */
    public Item getItem(String name) {
        for (Item item : items) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    /**
     * Checks if the player has a particular item
     * 
     * @param item The item to check
     * @return true if the player has the item
     */
    public boolean hasItem(Item item) {
        for (Item i : items) {
            if (i == item) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return The details of the items that the player is carrying
     */
    public String getCurrentItemDetails() {
        if (items.isEmpty()) {
            return "There are no items in your posession.";
        }
        String returnString = "You are carrying the following:\n\n";
        for (Item item : items) {
            returnString += "Name: " + item.getName() + "\n";
            returnString += "Description: " + item.getDescription() + "\n";
            returnString += "Weight: " + item.getWeight() + "\n\n";
        }
        returnString += "Total Weight: " + getCurrentCarryWeight();
        return returnString;
    }

    /**
     * @return The players stats
     */
    public String getStats() {
        String returnString = "Health: " + health + "\n";
        returnString += "Strength: " + strength + "\n";
        returnString += "Maximum Carry Weight: " + maxCarryWeight + "\n";

        return returnString;
    }

    /**
     * @return The number of moves a player has left
     */
    public int getMovesLeft() {
        return MAX_MOVES_ALLOWED - roomHistory.size();
    }

    /**
     * Sets the location of a beamer charge
     */
    public void setBeamerLocation() {
        beamerLocation = currentRoom;
    }

    /**
     * @return The location of the last beamer charge
     */
    public Room getBeamerLocation() {
        return beamerLocation;
    }

    /**
     * Makes a player ingest an item
     * 
     * @param item The item to ingest
     * @return A message to display
     */
    public String ingest(Item item) {
        PlayerStat statToAffect = item.getStatToAffect();
        int affectValue = item.getAffectValue();

        switch (statToAffect) {
            case HEALTH -> health += affectValue;
            case STRENGTH -> strength += affectValue;
            case MAX_CARRY_WEIGHT -> maxCarryWeight += affectValue;
            case NONE -> {
                return "This item has no effect.";
            }
        }

        return "That was delicious. Not only that but your " + statToAffect.toString() + " increased by " + affectValue
                + "!";
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
     * Set the strength of the player
     * 
     * @param value The value to set
     */
    public void setStrength(int value) {
        strength = value;
    }
}
