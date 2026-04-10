package com.bdtripp.hauntedhouse.model;

/**
 * Represents the status of the game.
 *
 * Enumerates the possible lifecycle states the game may be in.
 *
 * @author Brian Tripp
 */
public enum GameStatus {

    /** Indicates that the game is currently running. */
    RUNNING,

    /** Indicates that the game has been stopped. */
    STOPPED;
}