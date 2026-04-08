package com.bdtripp.hauntedhouse.model;

/**
 * Represents a command entered by a player in the Haunted House game.
 * A command may have up to three words and are defined as a command word, a
 * second
 * word, and a third word. For example, if the command was "give spade
 * Beatrice",
 * then the three words are "give", "spade", and "Beatrice". A command may only
 * have one or two words. In this case the other words would be null.
 *
 * @author Michael Kölling and David J. Barnes, Brian Tripp
 * @version 2026.02.10
 */

public class Command {
    private CommandWord commandWord;
    private String secondWord;
    private String thirdWord;

    /**
     * Create a command with three words. Words may be null.
     * 
     * @param firstWord  The first word of the command.
     * @param secondWord The second word of the command.
     * @param thirdWord  The third word of the command.
     *
     */
    public Command(CommandWord firstWord, String secondWord, String thirdWord) {
        commandWord = firstWord;
        this.secondWord = secondWord;
        this.thirdWord = thirdWord;
    }

    /**
     * Returns the primary command word supplied by the player.
     * This represents the action the player intends to perform.
     *
     * @return the first word of the command
     */
    public CommandWord getCommandWord() {
        return commandWord;
    }

    /**
     * Returns the second word of the command, if present.
     * This is typically the object or target of the command.
     *
     * @return the second word, or {@code null} if the command contains only one
     *         word.
     */
    public String getSecondWord() {
        return secondWord;
    }

    /**
     * Returns the third word of the command, if present.
     * This is used for commands that require two arguments.
     *
     * @return the third word, or {@code null} if the command does not include one.
     */
    public String getThirdWord() {
        return thirdWord;
    }

    /**
     * Indicates whether the command word is unrecognized.
     * A command is considered unknown when its primary word
     * does not match any valid {@link CommandWord}.
     *
     * @return {@code true} if the command word is not understood.
     */
    public boolean isUnknown() {
        return (commandWord == null);
    }

    /**
     * Indicates whether the command includes a second word.
     *
     * @return {@code true} if a second word is present.
     */
    public boolean hasSecondWord() {
        return (secondWord != null);
    }

    /**
     * Indicates whether the command includes a third word.
     *
     * @return {@code true} if a third word is present.
     */
    public boolean hasThirdWord() {
        return (thirdWord != null);
    }
}
