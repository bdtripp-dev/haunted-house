package com.bdtripp.hauntedhouse.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.bdtripp.hauntedhouse.model.Command;
import com.bdtripp.hauntedhouse.model.Room;
import com.bdtripp.hauntedhouse.model.RoomName;
import com.bdtripp.hauntedhouse.model.World;

/**
 * Orchestrates the overall game loop and state transitions.
 *
 * The GameEngine initializes the world, manages game state, delegates command execution, moves the
 * player between rooms, and generates messages.
 *
 * @author Michael Kölling, David J. Barnes, and Brian Tripp
 */
public class GameEngine {
    private final World world;
    private final CommandProcessor commandProcessor;
    private boolean gameOver;

    /**
     * Creates the GameEngine and the game world.
     */
    public GameEngine() {
        world = new WorldBuilder().createWorld();
        commandProcessor = new CommandProcessor(world, this);
    }

    /**
     * Starts the game by placing the player in the starting room
     */
    public void startGame() {
        world.getPlayer().moveToRoom(world.getStartingRoom(), false);
    }

    /**
     * Given a command, process (that is: execute) the command.
     * 
     * @param command The command to be processed.
     * @return A message to display
     */
    public String processCommand(Command command) {
        return commandProcessor.processCommand(command);
    }

    /**
     * Returns a random room in the haunted house
     * 
     * @return A random room
     */
    public Room getRandomRoom() {
        Random randomGenerator = new Random();
        int randomIndex = randomGenerator.nextInt(world.getRooms().size());
        List<RoomName> names = new ArrayList<>(world.getRooms().keySet());
        RoomName randomRoomName = names.get(randomIndex);

        return world.getRoom(randomRoomName);
    }

    /**
     * Build the welcome message that displays when a new game is started. Includes information
     * about the room a player is in.
     * 
     * @return The welcome message
     */
    public String buildWelcomeMessage() {
        StringBuilder buffer = new StringBuilder();

        buffer.append("""
                Welcome to the Haunted House!
                Haunted House is a spooky adventure game.
                Those who enter may never escape.
                Find the way out and you just might survive.
                Use the \"help\" command if you need help.

                """);
        buffer.append(describeCurrentRoom());

        return buffer.toString();
    }

    /**
     * Gets a description about the room the player is currently in including the items and
     * characters that it contains.
     * 
     * @return A description of the room
     */
    public String describeCurrentRoom() {
        Room currentRoom = world.getPlayer().getCurrentRoom();
        StringBuilder buffer = new StringBuilder();

        buffer.append(currentRoom.describeRoom()).append("\n\n");
        buffer.append(currentRoom.describeItems()).append("\n\n");
        buffer.append(currentRoom.describeCharacters());

        return buffer.toString();
    }

    /**
     * Moves the player to the specified room and returns details about the room
     * 
     * @param nextRoom The room to move to
     * @param addToHistory True if the current room should be added to history
     * @return The details about the room
     */
    public String movePlayerTo(Room nextRoom, boolean addToHistory) {
        world.getPlayer().moveToRoom(nextRoom, addToHistory);
        return describeCurrentRoom();
    }

    /**
     * Ends the game.
     */
    public void endGame() {
        gameOver = true;
    }

    /**
     * Checks if the game is over
     * 
     * @return true if the game is over, false otherwise
     */
    public boolean isGameOver() {
        return gameOver;
    }
}
