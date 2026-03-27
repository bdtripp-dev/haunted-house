package com.bdtripp.hauntedhouse;

/**
 * Represents a command entered by a player in the Haunted House game.
 * A command may have up to three words and are defined as a command word, a second
 * word, and a third word. For example, if the command was "give spade Beatrice", 
 * then the three words are "give", "spade", and "Beatrice". A command may only 
 * have one or two words. In this case the other words would be null.
 *
 * @author  Michael Kölling and David J. Barnes, Brian Tripp
 * @version 2026.02.10
 */

public class Command
{
    private CommandWord commandWord;
    private String secondWord;
    private String thirdWord;

    /**
     * Create a command with three words. Words may be null.
     * @param firstWord The first word of the command.
     * @param secondWord The second word of the command.
     * @param thirdWord The third word of the command.
     *
     */
    public Command(CommandWord firstWord, String secondWord, String thirdWord)
    {
        commandWord = firstWord;
        this.secondWord = secondWord;
        this.thirdWord = thirdWord;
    }

    /**
     * Returns the command word (the first word) of this command.
     * @return The command word.
     */
    public CommandWord getCommandWord()
    {
        return commandWord;
    }

    /**
     * @return The second word of this command. Returns null if there was no
     * second word.
     */
    public String getSecondWord()
    {
        return secondWord;
    }

    /**
     * @return The third word of this command. Returns null if there was no
     * third word.
     */
    public String getThirdWord()
    {
        return thirdWord;
    }

    /**
     * @return true if this command was not understood.
     */
    public boolean isUnknown()
    {
        return (commandWord == null);
    }

    /**
     * @return true if the command has a second word.
     */
    public boolean hasSecondWord()
    {
        return (secondWord != null);
    }

    /**
     * @return true if the command has a third word.
     */
    public boolean hasThirdWord()
    {
        return (thirdWord != null);
    }
}



