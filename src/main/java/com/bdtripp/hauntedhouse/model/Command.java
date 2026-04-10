package com.bdtripp.hauntedhouse.model;

/**
 * Represents a parsed player command.
 *
 * A Command consists of a primary verb and up to two optional arguments that specify the target or
 * context of the action. For example, the input "give spade Beatrice" produces the command word
 * "give" with "spade" and "Beatrice" as its arguments.
 *
 * Missing arguments are represented as {@code null}, allowing the game to distinguish between
 * incomplete and fully specified commands.
 * 
 * @author Brian Tripp
 */
public class Command {
    private CommandWord commandWord;
    private String secondWord;
    private String thirdWord;

    /**
     * Create a command with three words. Words may be null.
     * 
     * @param firstWord the first word of the command.
     * @param secondWord the second word of the command.
     * @param thirdWord the third word of the command.
     *
     */
    public Command(CommandWord firstWord, String secondWord, String thirdWord) {
        commandWord = firstWord;
        this.secondWord = secondWord;
        this.thirdWord = thirdWord;
    }

    /**
     * Returns the primary command word supplied by the player. This represents the action the
     * player intends to perform.
     *
     * @return the first word of the command
     */
    public CommandWord getCommandWord() {
        return commandWord;
    }

    /**
     * Returns the second word of the command, if present. This is typically the object or target of
     * the command.
     *
     * @return the second word, or {@code null} if the command contains only one word.
     */
    public String getSecondWord() {
        return secondWord;
    }

    /**
     * Returns the third word of the command, if present. This is used for commands that require two
     * arguments.
     *
     * @return the third word, or {@code null} if the command does not include one.
     */
    public String getThirdWord() {
        return thirdWord;
    }

    /**
     * Indicates whether the command word is unrecognized. A command is considered unknown when its
     * primary word does not match any valid {@link CommandWord}.
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
