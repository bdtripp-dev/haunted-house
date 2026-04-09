package com.bdtripp.hauntedhouse.model;

/**
 * Represents a non-player character.
 *
 * A Character can speak with the player and may request a specific item in exchange for offering a
 * reward item.
 *
 * @author Brian Tripp
 */
public class Character {
    private String name;
    private String initialDialog;
    private String acceptanceDialog;
    private Item itemSought;
    private Item itemForReward;

    /**
     * Create a character with a name, dialog, item they are seeking, and item they give as a
     * reward.
     * 
     * @param name The name of the character.
     * @param initialDialog The initial dialog that the character speaks.
     * @param acceptanceDialog The dialog that the character speaks upon accepting an item.
     * @param itemSought The item that the character is seeking.
     * @param itemForReward The item that the character rewards the player with.
     */
    public Character(String name, String initialDialog, String acceptanceDialog, Item itemSought,
            Item itemForReward) {
        this.name = name;
        this.initialDialog = initialDialog;
        this.acceptanceDialog = acceptanceDialog;
        this.itemSought = itemSought;
        this.itemForReward = itemForReward;
    }

    /**
     * Returns the name of the character
     * 
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the initial dialog that the character speaks
     * 
     * @return The initial dialog
     */
    public String getInitialDialog() {
        return initialDialog;
    }

    /**
     * Returns the dialog that the character speaks upon accepting an item
     * 
     * @return The acceptance dialog
     */
    public String getAcceptanceDialog() {
        return acceptanceDialog;
    }

    /**
     * Returns the item that the character is seeking
     * 
     * @return The item
     */
    public Item getItemSought() {
        return itemSought;
    }

    /**
     * Returns the item as a reward
     * 
     * @return The item
     */
    public Item getItemForReward() {
        return itemForReward;
    }
}
