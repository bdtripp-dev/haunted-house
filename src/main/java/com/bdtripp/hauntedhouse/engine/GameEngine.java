package com.bdtripp.hauntedhouse.engine;

import java.util.ArrayList;
import java.util.Random;

import com.bdtripp.hauntedhouse.model.Command;
import com.bdtripp.hauntedhouse.model.CommandWord;
import com.bdtripp.hauntedhouse.model.Item;
import com.bdtripp.hauntedhouse.model.Player;
import com.bdtripp.hauntedhouse.model.PlayerStat;
import com.bdtripp.hauntedhouse.model.Room;
import com.bdtripp.hauntedhouse.model.Character;
import com.bdtripp.hauntedhouse.model.Direction;
import com.bdtripp.hauntedhouse.model.ExitType;
import com.bdtripp.hauntedhouse.model.Inventory;

/**
 * Represents the GameEngine of the Haunted House game. Haunted House is a text
 * based
 * adventure game in which the player can explore rooms in a haunted house. The
 * goal
 * of the game is to try to find your way out of the house. This class contains
 * the
 * core logic for the game that is shared by both the web version (GameService)
 * and the
 * CLI version (CliRunner). It creates all the rooms, items, characters, defines
 * the
 * exits for each room, and processes all Commands entered by the player.
 *
 * @author Michael Kölling, David J. Barnes, and Brian Tripp
 * @version 2026.04.02
 */

public class GameEngine {
    private Player player;
    private Room billiardRoom;
    private ArrayList<Room> rooms;
    private boolean gameOver;
    private Item key;

    /**
     * Creates the GameEngine and initialises a player and the rooms.
     */
    public GameEngine() {
        player = new Player("Brian", new Inventory(75));
        rooms = new ArrayList<>();
        createRooms();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms() {
        Room hallway, study, indoorGarden, rootCellar, library,
                den, wineCellar, bathroom, outside;

        hallway = new Room("in a dark hallway");
        study = new Room("in a study");
        indoorGarden = new Room("in a misty indoor garden");
        rootCellar = new Room("in the root cellar");
        library = new Room("in the library");
        billiardRoom = new Room("in the billiard room");
        den = new Room("in the musty den");
        wineCellar = new Room("in the wine cellar");
        bathroom = new Room("in a flooded bathroom...gross");
        outside = new Room("outside of the haunted house and the sun is so delightful!");

        Item spade = new Item(
                "spade",
                "an old spade",
                1,
                false,
                PlayerStat.NONE,
                0);

        key = new Item(
                "key",
                "a rusty skeleton key",
                1,
                false,
                PlayerStat.NONE,
                0);

        hallway.addItem(new Item(
                "elixir",
                "an elixir",
                50,
                true,
                PlayerStat.HEALTH,
                10));
        hallway.addItem(new Item(
                "cookie",
                "a magic cookie",
                5,
                true,
                PlayerStat.STRENGTH,
                5));
        indoorGarden.addItem(spade);
        indoorGarden.addItem(new Item(
                "plant",
                "fox glove",
                5,
                false,
                PlayerStat.NONE,
                0));
        wineCellar.addItem(new Item(
                "crate",
                "a big old crate",
                2000,
                false,
                PlayerStat.NONE,
                0));
        bathroom.addItem(key);
        bathroom.addItem(new Item(
                "bucket",
                "an empty bucket",
                20,
                false,
                PlayerStat.NONE,
                0));

        billiardRoom.addCharacter(
                "Beatrice",
                "How lovely to meet you. You didn't happen to see my spade...",
                "Oh so you found it! Wonderful! Here is something that might come in handy...",
                spade,
                new Item(
                        "potion",
                        "a powerful muscle building potion",
                        50, true,
                        PlayerStat.MAX_CARRY_WEIGHT,
                        50));

        hallway.setExit(Direction.NORTH, den, ExitType.UNLOCKED);
        hallway.setExit(Direction.SOUTH, outside, ExitType.LOCKED);

        study.setExit(Direction.EAST, indoorGarden, ExitType.UNLOCKED);

        indoorGarden.setExit(Direction.EAST, billiardRoom, ExitType.UNLOCKED);
        indoorGarden.setExit(Direction.SOUTH, rootCellar, ExitType.UNLOCKED);
        indoorGarden.setExit(Direction.WEST, study, ExitType.UNLOCKED);

        rootCellar.setExit(Direction.EAST, library, ExitType.UNLOCKED);
        rootCellar.setExit(Direction.SOUTH, wineCellar, ExitType.UNLOCKED);
        rootCellar.setExit(Direction.WEST, den, ExitType.UNLOCKED);

        library.setExit(Direction.NORTH, billiardRoom, ExitType.UNLOCKED);
        library.setExit(Direction.WEST, rootCellar, ExitType.UNLOCKED);

        billiardRoom.setExit(Direction.SOUTH, library, ExitType.UNLOCKED);
        billiardRoom.setExit(Direction.WEST, indoorGarden, ExitType.UNLOCKED);

        den.setExit(Direction.EAST, rootCellar, ExitType.UNLOCKED);
        den.setExit(Direction.SOUTH, hallway, ExitType.UNLOCKED);

        wineCellar.setExit(Direction.NORTH, rootCellar, ExitType.UNLOCKED);
        wineCellar.setExit(Direction.EAST, bathroom, ExitType.UNLOCKED);

        bathroom.setExit(Direction.WEST, wineCellar, ExitType.UNLOCKED);

        outside.setExit(Direction.NORTH, hallway, ExitType.UNLOCKED);

        rooms.add(hallway);
        rooms.add(study);
        rooms.add(indoorGarden);
        rooms.add(rootCellar);
        rooms.add(library);
        rooms.add(billiardRoom);
        rooms.add(den);
        rooms.add(wineCellar);
        rooms.add(bathroom);
        rooms.add(outside);
    }

    /**
     * Starts the game in the billiardRoom
     */
    public void startGame() {
        player.moveToRoom(billiardRoom, false);
    }

    /**
     * Returns all of the rooms
     * 
     * @return A list of the rooms
     */
    public ArrayList<Room> getRooms() {
        return rooms;
    }

    /**
     * Returns a random room in the haunted house
     * 
     * @return A random room
     */
    public Room getRandomRoom() {
        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(rooms.size());

        return rooms.get(index);
    }

    /**
     * Get the welcome message that displays when a new game is started. Includes
     * information about the room a player is in.
     * 
     * @return The welcome message
     */
    public String getWelcomeMessage() {
        StringBuilder buffer = new StringBuilder();

        buffer.append("""
                Welcome to the Haunted House!
                Haunted House is a spooky adventure game.
                Those who enter may never escape.
                Find the exit and you just might survive.
                Use the \"help\" command if you need help.

                """);
        buffer.append(getRoomDetails());

        return buffer.toString();
    }

    /**
     * Returns details about the room the player is currently in including the items
     * and
     * characters that it contains.
     * 
     * @return The details about the room
     */
    public String getRoomDetails() {
        StringBuilder buffer = new StringBuilder();

        buffer.append(player.getCurrentRoom().getLongDescription()).append("\n");
        buffer.append(player.getCurrentRoom().getItemsInRoomDetails()).append("\n");
        buffer.append(player.getCurrentRoom().getCharactersInRoomDetails());

        return buffer.toString();
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

    // implementations of user commands:

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
        Room currentRoom = player.getCurrentRoom();
        Room nextRoom = currentRoom.getNeighbor(direction);

        if (nextRoom == null) {
            return "There is no door!";
        } else if (currentRoom.getExit(direction).getType() == ExitType.LOCKED) {
            if (player.getInventory().hasItem(key)) {
                buffer.append("The door is locked...but you have the key!").append("\n");
                enterRoom(nextRoom, true);
                endGame();
                return buffer.append("You are ").append(player.getCurrentRoom().getDescription()).append("\n")
                        .toString();
            } else {
                return "The door is locked...you need to find the key!";
            }
        } else {
            if (player.getMovesLeft() == 0) {
                endGame();
                return "You ran out of moves! Game Over...";
            }
        }
        return buffer.append(enterRoom(nextRoom, true)).toString();
    }

    /**
     * Moves the player to the specified room and returns details about the room
     * 
     * @param nextRoom     The room to move to
     * @param addToHistory True if the current room should be added to history
     * @return The details about the room
     */
    private String enterRoom(Room nextRoom, boolean addToHistory) {
        player.moveToRoom(nextRoom, addToHistory);
        return getRoomDetails();
    }

    /**
     * Returns a message about the current location
     * 
     * @return A message to display
     */
    private String look() {
        return player.getCurrentRoom().getLongDescription();
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
        Item item = player.getInventory().getItem(itemName);
        Item itemToEat = player.getInventory().removeItem(item);

        if (itemToEat == null) {
            return "That item doesn't exist.";
        } else if (!itemToEat.isEdible()) {
            return "Don't eat that!";
        } else {
            return player.ingest(itemToEat);
        }
    }

    /**
     * Ends the game.
     */
    private void endGame() {
        gameOver = true;
    }

    /**
     * Checks if the game is over
     * 
     * @return true if the game is over, false otherwise
     */
    public boolean isGameOver() {
        return gameOver;
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
        Item item = player.getCurrentRoom().getItem(itemName);
        Item itemToTake = player.getCurrentRoom().removeItemFromRoom(item);

        if (itemToTake == null) {
            return "That item doesn't exist in this room";
        } else if ((itemToTake.getWeight() + player.getInventory().getCurrentCarryWeight()) > player.getInventory()
                .getMaxCarryWeight()) {
            return "It's too heavy! You can carry up to " +
                    player.getInventory().getMaxCarryWeight() + " units. Maybe if you dropped \n" +
                    "some items you could manage it. Or it may be simply too heavy.";
        } else {
            player.getInventory().addItem(itemToTake);
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
        Item item = player.getInventory().getItem(itemName);
        Item droppedItem = player.getInventory().removeItem(item);

        if (droppedItem == null) {
            return "You don't have one of those.";
        } else {
            player.getCurrentRoom().addItem(droppedItem);
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
        return player.getInventory().getItemDetails();
    }

    /**
     * Returns the players current stats
     * 
     * @return A message to display
     */
    private String showStats() {
        return player.getStats();
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
        Character character = player.getCurrentRoom().getCharacter(characterName);

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
        Character character = player.getCurrentRoom().getCharacter(characterName);

        if (character == null) {
            return "Who is \"" + characterName + "\" ?";
        }

        Item itemSought = character.getItemSought();
        String itemToGiveName = command.getSecondWord();
        Item itemToGive = player.getInventory().getItem(itemToGiveName);
        Item itemForReward = character.getItemForReward();

        if (itemToGive == null) {
            return "You don't have a(n) \"" + itemToGiveName + "\"";
        } else if (itemToGive == itemSought) {
            player.getInventory().removeItem(itemToGive);
            buffer.append(character.getAcceptanceDialog()).append("\n");
            player.getInventory().addItem(itemForReward);
            buffer.append("Received " + itemForReward.getDescription() + "!").append("\n");
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
            player.setBeamerLocation();
            player.setBeamerCharge(true);
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
        } else if (player.getBeamerCharge() == false) {
            return "Your beamer isn't charged!";
        } else {
            buffer.append("Beamer fired!").append("\n");
            buffer.append(enterRoom(player.getBeamerLocation(), true)).append("\n");
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
        if (player.getRoomHistory().empty()) {
            return "There is nowhere to go back to.";
        } else {
            return enterRoom(player.getPreviousRoom(), false);
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
            endGame();
            return "Thank you for playing.  Good bye.";
        }
    }
}
