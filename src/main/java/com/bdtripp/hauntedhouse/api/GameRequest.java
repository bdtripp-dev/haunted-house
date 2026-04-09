package com.bdtripp.hauntedhouse.api;

/**
 * Represents input submitted by the client.
 *
 * A GameRequest carries the raw text entered by the player so the GameController can forward it to
 * the game logic.
 *
 * @author Brian Tripp
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
