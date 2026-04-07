package com.bdtripp.hauntedhouse.model;

import java.util.Stack;

/**
 * Represents a player in the Haunted House game. Keeps track of where the
 * player is, their inventory, and their current stats.
 *
 * @author Brian Tripp
 * @version 2026.04.02
 */
public class Player {
    private String name;
    private Room currentRoom;
    private Stack<Room> roomHistory;
    private static final int MAX_MOVES_ALLOWED = 30;
    private Room beamerLocation;
    private boolean beamerCharged;
    private Inventory inventory;
    private Stats stats;

    /**
     * @param name      The name of the player
     * @param inventory A collection of items carried by the player
     */
    public Player(String name, Inventory inventory) {
        this.name = name;
        this.inventory = inventory;
        roomHistory = new Stack<>();
        this.stats = new Stats(100, 10);
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
            case HEALTH -> stats.setHealth(stats.getHealth() + affectValue);
            case STRENGTH -> stats.setStrength(stats.getStrength() + affectValue);
            case MAX_CARRY_WEIGHT -> inventory.setMaxCarryWeight(
                    inventory.getMaxCarryWeight() + affectValue);
            case NONE -> {
                return "This item has no effect.";
            }
        }

        return "That was delicious. Not only that but your " + statToAffect.toString() + " increased by " + affectValue
                + "!";
    }

    /**
     * @return The player's inventory
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * @return The players stats
     */
    public String getStats() {
        String returnString = "Health: " + stats.getHealth() + "\n";
        returnString += "Strength: " + stats.getStrength() + "\n";
        returnString += "Maximum Carry Weight: " + inventory.getMaxCarryWeight() + "\n";

        return returnString;
    }
}
