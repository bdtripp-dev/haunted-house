package com.bdtripp.hauntedhouse.api;

/**
 * Represents a GameRequest in the Haunted House game.
 * 
 * A GameRequest is created when player input is received by the GameController
 *
 * @author Brian Tripp
 * @version 2026.02.09
 */

public class GameRequest {
    private String input;

    /**
     * Creates an empty GameRequest
     */
    public GameRequest() {
    }

    /**
     * Returns the players input
     * 
     * @return The input from the client
     */
    public String getInput() {
        return input;
    }

    /**
     * Sets the player's input
     * 
     * @param input the input from the client
     */
    public void setInput(String input) {
        this.input = input;
    }
}
