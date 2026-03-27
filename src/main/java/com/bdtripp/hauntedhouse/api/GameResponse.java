package com.bdtripp.hauntedhouse.api;

import com.bdtripp.hauntedhouse.model.GameStatus;

/**
 * Represents a GameResponse in the Haunted House game.
 * 
 * A GameResponse is created by the GameService and contains
 * output from the GameEngine and the game's current status.
 * The GameResponse is then sent to the client.
 *
 * @author Brian Tripp
 * @version 2026.02.09
 */

public class GameResponse {
    private String output;
    private GameStatus status;

    /**
     * Creates an empty GameResponse.
     */
    public GameResponse() {
    }

    /**
     * Creates a GameResponse and initialize the output and status.
     * 
     * @param output The output from the GameEngine.
     * @param status The game's status.
     */
    public GameResponse(String output, GameStatus status) {
        this.output = output;
        this.status = status;
    }

    /**
     * Returns the output from the GameEngine.
     * 
     * @return The output.
     */
    public String getOutput() {
        return output;
    }

    /**
     * Sets the output from the GameEngine.
     * 
     * @param output The output.
     */
    public void setOutput(String output) {
        this.output = output;
    }

    /**
     * Returns the status of the game.
     * 
     * @return The status.
     */
    public GameStatus getStatus() {
        return status;
    }

    /**
     * Sets the status of the game.
     * 
     * @param status The status.
     */
    public void setStatus(GameStatus status) {
        this.status = status;
    }
}
