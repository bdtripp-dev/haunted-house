package com.bdtripp.hauntedhouse.model;

import java.util.Set;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Represents a room in the game world.
 *
 * Rooms contain items, characters, and exits that connect them to other rooms.
 *
 * @author Michael Kölling, David J. Barnes, and Brian Tripp
 */
public class Room {
    private final RoomName name;
    private final String description;
    private HashMap<Direction, Exit> exits;
    private ArrayList<Item> items;
    private ArrayList<Character> characters;

    /**
     * Creates a new room with the given name and description.
     * 
     * @param description the room's description. Something like "a kitchen" or "an open court
     * yard".
     */
    public Room(RoomName name, String description) {
        this.name = name;
        this.description = description;
        exits = new HashMap<>();
        items = new ArrayList<>();
        characters = new ArrayList<>();
    }

    /**
     * Adds an item to the room
     * 
     * @param item the item to add
     */
    public void addItem(Item item) {
        items.add(item);
    }

    /**
     * Finds an item with a given name
     * 
     * @param name the name of the item
     * @return the item
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
     * Removes an item from the room
     * 
     * @param item the item to remove
     * @return the item that was removed
     */
    public Item removeItem(Item item) {
        if (items.remove(item)) {
            return item;
        }
        return null;
    }

    /**
     * Adds a character to the room
     * 
     * @param character the character to add
     */
    public void addCharacter(Character character) {
        characters.add(character);
    }

    /**
     * Creates an exit for the room
     * 
     * @param direction the direction of the exit.
     * @param neighbor the room in the given direction.
     * @param type the type of exit (locked or unlocked)
     */
    public void setExit(Direction direction, Room neighbor, ExitType type) {
        Exit exit = new Exit(direction, neighbor, type);
        exits.put(direction, exit);
    }

    /**
     * Retrieves the neighboring room in the specified direction.
     *
     * @param direction the direction of the neighbor
     * @return the room in the given direction. null if there is no exit in that direction
     */
    public Room getNeighbor(Direction direction) {
        if (exits.containsKey(direction)) {
            return exits.get(direction).getNeighbor();
        }
        return null;
    }

    /**
     * Returns the exit associated with the given direction.
     *
     * @param direction the direction of the exit
     * @return the exit in the direction provided
     */
    public Exit getExit(Direction direction) {
        return exits.get(direction);
    }

    /**
     * Builds a description of all exits from this room.
     *
     * @return a description of the room's exits, for example, "Exits: north west".
     */
    public String describeExits() {
        String returnString = "Exits:";
        Set<Direction> keys = exits.keySet();
        for (Direction exit : keys) {
            returnString += " " + exit.toString();
        }
        return returnString;
    }

    /**
     * Returns the name of the room.
     *
     * @return the name of the room.
     */
    public RoomName getName() {
        return name;
    }

    /**
     * Returns the description for this room.
     *
     * @return the description of the room.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Produces a formatted description of all items present in the room.
     *
     * @return a description of the items in the room
     */
    public String describeItems() {
        String returnString = "";
        if (items.isEmpty()) {
            return "No items were found.";
        }
        returnString += "You found:\n";
        int index = 0;
        for (Item item : items) {
            boolean isLastItem = index == (items.size() - 1);
            returnString += item.getDescription() + " (weight: " + item.getWeight()
                    + ") To take item use the" + " command: take " + item.getName();
            if (!isLastItem) {
                returnString += "\n";
            }
            index++;
        }
        return returnString;
    }

    /**
     * Produces a formatted list of characters in the room.
     *
     * @return a list of characters in the room
     */
    public String describeCharacters() {
        String returnString = "Characters:\n";
        if (characters.isEmpty()) {
            returnString += "No one is here.";
            return returnString;
        }
        returnString += String.join("\n", characters.stream().map(Character::getName).toList());

        return returnString;
    }

    /**
     * Produces a full description of the room, including its exits.
     * 
     * @return a description of the room. For example: You are in the kitchen. Exits: north west
     */
    public String describeRoom() {
        return "You are " + description + ".\n" + describeExits();
    }

    /**
     * Find the character that matches the given name
     * 
     * @param name the name to search for
     * @return the matching character, or null if none is found
     */
    public Character findCharacter(String name) {
        for (Character character : characters) {
            if (character.getName().toLowerCase().equals(name.toLowerCase())) {
                return character;
            }
        }
        return null;
    }
}
