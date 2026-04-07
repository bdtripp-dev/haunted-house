package com.bdtripp.hauntedhouse.model;

import java.util.Map;

/**
 * Stores all of the things that exist in the game world such as
 * the player, the rooms of the haunted house, the items in the
 * rooms, etc.
 *
 * @author Brian Tripp
 * @version 2026.04.04
 */
public class World {
    private final Player player;
    private final Map<RoomName, Room> rooms;
    private final Room startingRoom;
    private final Item key;

    /**
     * Create the game world that has a player, rooms, a starting room, key etc.
     * 
     * @param player       The player of the game
     * @param rooms        The rooms in the game
     * @param startingRoom The room where the game starts
     * @param key          A key that unlocks an exit
     */
    public World(
            Player player,
            Map<RoomName, Room> rooms,
            Room startingRoom,
            Item key) {
        this.player = player;
        this.rooms = rooms;
        this.startingRoom = startingRoom;
        this.key = key;
    }

    /**
     * @return The player of the game
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @return The rooms in the game
     */
    public Map<RoomName, Room> getRooms() {
        return rooms;
    }

    /**
     * @return The starting room of the game
     */
    public Room getStartingRoom() {
        return startingRoom;
    }

    /**
     * Get the room that matches a name
     * 
     * @param name The name of the room
     * @return The room
     */
    public Room getRoom(RoomName name) {
        return rooms.get(name);
    }

    /**
     * @return A key that is used to unlock an exit
     */
    public Item getKey() {
        return key;
    }
}
