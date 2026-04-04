package com.bdtripp.hauntedhouse.engine;

import com.bdtripp.hauntedhouse.model.Character;
import com.bdtripp.hauntedhouse.model.Command;
import com.bdtripp.hauntedhouse.model.CommandWord;
import com.bdtripp.hauntedhouse.model.Direction;
import com.bdtripp.hauntedhouse.model.ExitType;
import com.bdtripp.hauntedhouse.model.Item;
import com.bdtripp.hauntedhouse.model.Room;
import com.bdtripp.hauntedhouse.model.World;

/**
 * Responsible for interpreting and executing player commands.
 *
 * @author Brian Tripp
 * @version 2026.04.04
 */
public class CommandProcessor {
    private final World world;
    private final GameEngine gameEngine;

    /**
     * Creates a CommandProcessor that operates on the given game world.
     * 
     * @param world      The game world whose state will be manipulated by commands.
     * @param gameEngine The engine that modifies the game's state
     */
    public CommandProcessor(World world, GameEngine gameEngine) {
        this.world = world;
        this.gameEngine = gameEngine;
    }

    /**
     * Given a command, process (that is: execute) the command.
     * 
     * @param command The command to be processed.
     * @return A message to display
     */
    public String processCommand(Command command) {
        return switch (command.getCommandWord()) {
            case HELP -> getHelpMessage();
            case GO -> goRoom(command);
            case LOOK -> look();
            case EAT -> eat(command);
            case TAKE -> take(command);
            case DROP -> drop(command);
            case ITEMS -> showItems();
            case STATS -> showStats();
            case TALK -> talk(command);
            case GIVE -> giveItem(command);
            case CHARGE -> chargeBeamer(command);
            case FIRE -> fireBeamer(command);
            case BACK -> goBack(command);
            case QUIT -> quit(command);
            case UNKNOWN -> "I don't know what you mean...";
        };
    }

    /**
     * Returns a list of command words and directions on how to use them.
     * 
     * @return A help message
     */
    private String getHelpMessage() {
        StringBuilder buffer = new StringBuilder();

        buffer.append("Your command words are:").append("\n");
        buffer.append(CommandWord.getCommandList()).append("\n\n");

        buffer.append("""
                How to use the commands:

                go: Use to move from room to room.
                Usage: type \"go\" + \"space\" + \"a direction\"
                Hint(s): Directions are north, south, east, or west.

                quit: Use to quit the program.
                Usage: type \"quit\"
                Hint(s): N/A

                help: Use to get information on how to play the game.
                Usage: type \"help\"
                Hint(s): N/A

                look: Use to get a description of your location and directions that you are able to travel in.
                Usage: type \"look\"
                Hint(s): N/A

                eat: Use to eat an item. Eating an item can boost your stats. Not all items are edible.
                Usage: type \"eat\" + \"space\" + \"the name of the item you want to eat\"
                Hint(s): example command - \"eat potion\".

                back: Use to backtrack consecutively through the rooms that you were just in.
                Usage: type \"back\"
                Hint(s): N/A

                take: If you find an item in a room, you can use the \"take\" command to pick up the item.
                Usage: type \"take\" + \"space\" + \"the name of the item you want to pick up\"
                Hint(s): N/A

                drop: Use to drop an item that you are carrying.
                Usage: type \"drop\" + \"space\" + \"the name of the item you want to drop\"
                Hint(s): You may want to drop an item since you can only carry so much weight.

                items: Use to print a list of items that you are carrying and descriptions of each item.
                Usage: type \"items\"
                Hint(s): N/A

                stats: Use to print a list of the players current stats.
                Usage: type \"stats\"
                Hint(s): This command will display information such as health, strength, and maximum carry weight.

                charge: Use to charge an item.
                Usage: type \"charge\" + \"space\" + \"the name of the item you want to charge\"
                Hint(s): You will need to charge your beamer before firing it. Charge the beamer in a room that
                you want to use as a return point. Later when you fire the beamer, it will send you back to the
                room that you charged it in originally.

                fire: Use to fire an item.
                Usage: type \"fire\" + \"space\" + \"the name of the item you want to fire\"
                Hint(s): You will need to charge your beamer before firing it. Charge the beamer in a room that
                you want to use as a return point. Later when you fire the beamer, it will send you back to the
                room that you charged it in originally.

                talk: If there is a character in a room, you can use this command to talk to them.
                Usage: type \"talk\" + \"space\" + \"the name of the person you want to talk to\"
                Hint(s): N/A

                give: Use to give an item to a Character.
                Usage: type \"give\" + \"space\" + \"the name of the item you want to give\"  + \"space\" +
                \"the name of the character you want to give it to\".
                Hint(s): Certain characters will give you a reward in exchange for giving them an item that
                you found. You must be in the same room as the Character that you want to give an item to.""");

        return buffer.toString();
    }

    /**
     * If there is an exit in the direction provided by the command, the player
     * takes that exit and enters
     * the neighboring room.
     * 
     * @param command The command that was entered
     * @return A message
     */
    private String goRoom(Command command) {
        StringBuilder buffer = new StringBuilder();

        if (!command.hasSecondWord()) {
            return "Go where?";
        }

        Direction direction = Direction.fromString(command.getSecondWord());
        Room currentRoom = world.getPlayer().getCurrentRoom();
        Room nextRoom = currentRoom.getNeighbor(direction);

        if (nextRoom == null) {
            return "There is no door!";
        } else if (currentRoom.getExit(direction).getType() == ExitType.LOCKED) {
            if (world.getPlayer().getInventory().hasItem(world.getKey())) {
                buffer.append("The door is locked...but you have the key!").append("\n");
                gameEngine.enterRoom(nextRoom, true);
                gameEngine.endGame();
                return buffer.append("You are ").append(world.getPlayer().getCurrentRoom().getDescription())
                        .append("\n")
                        .toString();
            } else {
                return "The door is locked...you need to find the key!";
            }
        } else {
            if (world.getPlayer().getMovesLeft() == 0) {
                gameEngine.endGame();
                return "You ran out of moves! Game Over...";
            }
        }
        return buffer.append(gameEngine.enterRoom(nextRoom, true)).toString();
    }

    /**
     * Returns a message about the current location
     * 
     * @return A message to display
     */
    private String look() {
        return world.getPlayer().getCurrentRoom().getLongDescription();
    }

    /**
     * Makes the player eat an item if it is edible
     * 
     * @param command The command that was entered
     * @return A message to display
     */
    private String eat(Command command) {
        if (!command.hasSecondWord()) {
            return "Eat what?";
        }

        String itemName = command.getSecondWord();
        Item item = world.getPlayer().getInventory().getItem(itemName);
        Item itemToEat = world.getPlayer().getInventory().removeItem(item);

        if (itemToEat == null) {
            return "That item doesn't exist.";
        } else if (!itemToEat.isEdible()) {
            return "Don't eat that!";
        } else {
            return world.getPlayer().ingest(itemToEat);
        }
    }

    /**
     * Makes a player pick up an item to carry with them
     * 
     * @param command The command that was entered
     * @return A message to display
     */
    private String take(Command command) {
        if (!command.hasSecondWord()) {
            return "Take what?";
        }

        String itemName = command.getSecondWord();
        Item item = world.getPlayer().getCurrentRoom().getItem(itemName);
        Item itemToTake = world.getPlayer().getCurrentRoom().removeItemFromRoom(item);

        if (itemToTake == null) {
            return "That item doesn't exist in this room";
        } else if ((itemToTake.getWeight() + world.getPlayer().getInventory().getCurrentCarryWeight()) > world
                .getPlayer().getInventory()
                .getMaxCarryWeight()) {
            return "It's too heavy! You can carry up to " +
                    world.getPlayer().getInventory().getMaxCarryWeight() + " units. Maybe if you dropped \n" +
                    "some items you could manage it. Or it may be simply too heavy.";
        } else {
            world.getPlayer().getInventory().addItem(itemToTake);
            return "You picked up " + itemToTake.getDescription() + "!";
        }
    }

    /**
     * Makes a player drop an item so they no longer have to carry it
     * 
     * @param command The command that was entered
     * @return A message to display
     */
    private String drop(Command command) {
        if (!command.hasSecondWord()) {
            return "Drop what?";
        }

        String itemName = command.getSecondWord();
        Item item = world.getPlayer().getInventory().getItem(itemName);
        Item droppedItem = world.getPlayer().getInventory().removeItem(item);

        if (droppedItem == null) {
            return "You don't have one of those.";
        } else {
            world.getPlayer().getCurrentRoom().addItem(droppedItem);
            return "You dropped " + droppedItem.getDescription();
        }
    }

    /**
     * Returns a message about all of the items that the player is
     * currently carrying
     * 
     * @return A message to display
     */
    private String showItems() {
        return world.getPlayer().getInventory().getItemDetails();
    }

    /**
     * Returns the players current stats
     * 
     * @return A message to display
     */
    private String showStats() {
        return world.getPlayer().getStats();
    }

    /**
     * Makes a play talk to a character
     * 
     * @param command The command that was entered
     * @return A message to display
     */
    public String talk(Command command) {
        if (!command.hasSecondWord()) {
            return "Talk to who?";
        }

        String characterName = command.getSecondWord();
        Character character = world.getPlayer().getCurrentRoom().getCharacter(characterName);

        if (character == null) {
            return "That is not someone who can be spoken to.";
        } else {
            return character.getInitialDialog();
        }
    }

    /**
     * Makes the player give an item to a character
     * 
     * @param command The command that was entered
     * @return A message to display
     */
    public String giveItem(Command command) {
        StringBuilder buffer = new StringBuilder();

        if (!command.hasSecondWord()) {
            return "Give what... to who...?";
        }

        if (!command.hasThirdWord()) {
            return "Give it to who?";
        }

        String characterName = command.getThirdWord();
        Character character = world.getPlayer().getCurrentRoom().getCharacter(characterName);

        if (character == null) {
            return "Who is \"" + characterName + "\" ?";
        }

        Item itemSought = character.getItemSought();
        String itemToGiveName = command.getSecondWord();
        Item itemToGive = world.getPlayer().getInventory().getItem(itemToGiveName);
        Item itemForReward = character.getItemForReward();

        if (itemToGive == null) {
            return "You don't have a(n) \"" + itemToGiveName + "\"";
        } else if (itemToGive == itemSought) {
            world.getPlayer().getInventory().removeItem(itemToGive);
            buffer.append(character.getAcceptanceDialog()).append("\n");
            world.getPlayer().getInventory().addItem(itemForReward);
            buffer.append("Received " + itemForReward.getDescription() + "!");
            return buffer.toString();
        } else {
            return characterName + " doesn't want your " + itemToGiveName;
        }
    }

    /**
     * Charges the beamer. The beamer memorizes the location of the players
     * current room.
     * 
     * @param command The command that was entered
     * @return A message to display
     */
    private String chargeBeamer(Command command) {
        if (!command.hasSecondWord()) {
            return "Charge what?";
        }

        String itemToCharge = command.getSecondWord();

        if (!itemToCharge.equals("beamer")) {
            return "That item can't be charged.";
        } else {
            world.getPlayer().setBeamerLocation();
            world.getPlayer().setBeamerCharge(true);
            return "Beamer charged!";
        }
    }

    /**
     * Fires the beamer. Returns the player to the location at which the
     * beamer was last charged.
     * 
     * @param command The command that was entered
     * @return A message to display
     */
    private String fireBeamer(Command command) {
        StringBuilder buffer = new StringBuilder();
        if (!command.hasSecondWord()) {
            return "Fire what?";
        }

        String itemToFire = command.getSecondWord();

        if (!itemToFire.equals("beamer")) {
            return "That item can't be fired.";
        } else if (world.getPlayer().getBeamerCharge() == false) {
            return "Your beamer isn't charged!";
        } else {
            buffer.append("Beamer fired!").append("\n");
            buffer.append(gameEngine.enterRoom(world.getPlayer().getBeamerLocation(), true)).append("\n");
            return buffer.toString();
        }
    }

    /**
     * Takes the player back to the previous room they
     * were in.
     * 
     * @param command The command that was entered
     * @return A message to display
     */
    private String goBack(Command command) {
        if (command.hasSecondWord()) {
            return ("\"back\" command does not allow a second word.");
        }
        if (world.getPlayer().getRoomHistory().empty()) {
            return "There is nowhere to go back to.";
        } else {
            return gameEngine.enterRoom(world.getPlayer().getPreviousRoom(), false);
        }
    }

    /**
     * Check the rest of the command to see
     * whether we really quit the game.
     * 
     * @return A message to display in the console
     */
    private String quit(Command command) {
        if (command.hasSecondWord()) {
            return "Enter \"quit\" (with nothing else after it) to quit the game.";
        } else {
            gameEngine.endGame();
            return "Thank you for playing.  Good bye.";
        }
    }
}
