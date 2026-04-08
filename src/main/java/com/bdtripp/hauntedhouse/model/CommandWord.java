package com.bdtripp.hauntedhouse.model;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * An enumeration of all valid commands in the Haunted House game.
 *
 * @author Michael Kölling, David J. Barnes, and Brian Tripp
 * @version 2026.02.10
 */
public enum CommandWord {

    /** Move to another room. */
    GO,

    /** Quit the game. */
    QUIT,

    /** Display help information. */
    HELP,

    /** Look around the current room. */
    LOOK,

    /** Eat an item. */
    EAT,

    /** Return to the previous room. */
    BACK,

    /** Pick up an item. */
    TAKE,

    /** Drop an item. */
    DROP,

    /** Show the player's inventory. */
    ITEMS,

    /** Show the player's statistics. */
    STATS,

    /** Charge an item. */
    CHARGE,

    /** Fire an item. */
    FIRE,

    /** Talk to a character. */
    TALK,

    /** Give an item to a character. */
    GIVE,

    /** Represents an unrecognized or invalid command. */
    UNKNOWN;

    /**
     * Checks whether a given string is a valid command word.
     * 
     * @param aString The string to check
     * @return true if a given string is a valid command,
     *         false if it isn't.
     */
    public static boolean isCommand(String aString) {
        try {
            CommandWord.valueOf(aString.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Get a list of all valid commands
     * 
     * @return A list of all valid commands separated by spaces
     */
    public static String getCommandList() {
        return Arrays.stream(CommandWord.values())
                .map(Enum::name)
                .map(String::toLowerCase)
                .collect(Collectors.joining(" "));
    }

    /**
     * Converts a string into a CommandWord if it is a valid command
     * 
     * @param word A string to convert to a CommandWord
     * @return The CommandWord
     */
    public static CommandWord fromString(String word) {
        try {
            return CommandWord.valueOf(word.toUpperCase());
        } catch (IllegalArgumentException ex) {
            return CommandWord.UNKNOWN;
        }
    }
}
