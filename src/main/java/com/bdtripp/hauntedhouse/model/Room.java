package com.bdtripp.hauntedhouse.model;

import java.util.Set;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Represents a room in the Haunted House game. It is
 * connected to other rooms via exits. The exits are labelled north,
 * south, east, and west. For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 *
 * @author Michael Kölling, David J. Barnes, and Brian Tripp
 * @version 2020.06.13
 */
public class Room {
    private String description;
    private HashMap<Direction, Exit> exits;
    private ArrayList<Item> items;
    private ArrayList<Character> characters;

    /**
     * @param description The room's description. Something like "a kitchen" or
     *                    "an open court yard".
     */
    public Room(String description) {
        this.description = description;
        exits = new HashMap<>();
        items = new ArrayList<>();
        characters = new ArrayList<>();
    }

    /**
     * Adds an item to the room
     * 
     * @param item The item to add
     */
    public void addItem(Item item) {
        items.add(item);
    }

    /**
     * Removes an item from the room
     * 
     * @param name The name of the item to remove
     * @return The item that was removed
     */
    public Item removeItemFromRoom(String name) {
        for (Item item : items) {
            if (item.getName().equals(name)) {
                items.remove(item);
                return item;
            }
        }
        return null;
    }

    /**
     * Adds a character to the room
     * 
     * @param name             The name of the character
     * @param initialDialog    The character initial dialog
     * @param acceptanceDialog The dialog the character will speak upon accepting an
     *                         item
     * @param itemSought       The item that the character is seeking
     * @param itemForReward    The item the character will give as a reward
     */
    public void addCharacter(
            String name,
            String initialDialog,
            String acceptanceDialog,
            Item itemSought,
            Item itemForReward) {
        characters.add(new Character(
                name,
                initialDialog,
                acceptanceDialog,
                itemSought,
                itemForReward));
    }

    /**
     * Creates an exit for the room
     * 
     * @param direction The direction of the exit.
     * @param neighbor  The room in the given direction.
     * @param type      The type of exit (locked or unlocked)
     */
    public void setExit(Direction direction, Room neighbor, ExitType type) {
        Exit exit = new Exit(direction, neighbor, type);
        exits.put(direction, exit);
    }

    /**
     * 
     * @param direction The direction of the neighbor
     * @return The room in the given direction. null if there is no exit in that
     *         direction
     */
    public Room getNeighbor(Direction direction) {
        if (exits.containsKey(direction)) {
            return exits.get(direction).getNeighbor();
        }
        return null;
    }

    /**
     * @param direction The direction of the exit
     * @return The exit in the direction provided
     */
    public Exit getExit(Direction direction) {
        return exits.get(direction);
    }

    /**
     * @return A description of the room's exits, for example, "Exits: north west".
     */
    public String getExitString() {
        String returnString = "Exits:";
        Set<Direction> keys = exits.keySet();
        for (Direction exit : keys) {
            returnString += " " + exit.toString();
        }
        return returnString;
    }

    /**
     * @return The description of the room.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return Details about the items in this room such as their description and
     *         weight
     */
    public String getItemsInRoomDetails() {
        String returnString = "";
        if (items.isEmpty()) {
            return "No items were found.";
        }
        returnString += "You found:\n";
        int index = 0;
        for (Item item : items) {
            boolean isLastItem = index == (items.size() - 1);
            returnString += item.getDescription() +
                    "(weight: " + item.getWeight() + ") To take item use the" +
                    " command: take " + item.getName();
            if (!isLastItem) {
                returnString += "\n";
            }
            index++;
        }
        return returnString;
    }

    /**
     * @return Details about the characters in this room such as their name and
     *         dialog
     */
    public String getCharactersInRoomDetails() {
        String returnString = "";
        if (characters.isEmpty()) {
            return "No one is here.";
        }
        returnString += "Characters:\n";
        for (Character character : characters) {
            returnString += character.getName();
        }
        return returnString;
    }

    /**
     * @return A long desciption of this room. For example:
     *         You are in the kitchen.
     *         Exits: north west
     */
    public String getLongDescription() {
        return "You are " + description + ".\n" + getExitString();
    }

    /**
     * @return The character that is in the room
     */
    public Character getCharacter(String name) {
        for (Character character : characters) {
            if (character.getName().toLowerCase().equals(name.toLowerCase())) {
                return character;
            }
        }
        return null;
    }
}
