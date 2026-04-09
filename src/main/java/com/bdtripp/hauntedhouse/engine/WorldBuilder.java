package com.bdtripp.hauntedhouse.engine;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import com.bdtripp.hauntedhouse.model.Character;
import com.bdtripp.hauntedhouse.model.Direction;
import com.bdtripp.hauntedhouse.model.ExitType;
import com.bdtripp.hauntedhouse.model.Inventory;
import com.bdtripp.hauntedhouse.model.Item;
import com.bdtripp.hauntedhouse.model.Player;
import com.bdtripp.hauntedhouse.model.PlayerStat;
import com.bdtripp.hauntedhouse.model.Room;
import com.bdtripp.hauntedhouse.model.RoomName;
import com.bdtripp.hauntedhouse.model.World;

/**
 * Creates all of the things that exist in the game world such as rooms, characters, and items. It
 * also connects all of the rooms together so that the player can navigate between them.
 *
 * @author Brian Tripp
 * @version 2026.04.04
 */
public class WorldBuilder {

        /**
         * Creates a new WorldBuilder instance.
         */
        public WorldBuilder() {
        }

        /**
         * Create the game world. Create all of the items, rooms, characters, etc and place them in
         * rooms and connect wire up the exits of the rooms.
         * 
         * @return The game world
         */
        public World createWorld() {
                Player player = new Player("Brian", new Inventory(75));
                Map<RoomName, Room> rooms = createRooms();
                Map<String, Item> referencedItems = createReferencedItems();
                placeReferencedItems(rooms, referencedItems);
                placeUnreferencedItems(rooms);
                placeCharacters(rooms, referencedItems);
                wireExits(rooms);

                Item key = referencedItems.get("key");

                return new World(player, rooms, rooms.get(RoomName.BILLIARD_ROOM), key);
        }

        /**
         * Create all the rooms that will exist in the world
         * 
         * @return All of the rooms in the world
         */
        private Map<RoomName, Room> createRooms() {
                Map<RoomName, Room> rooms = new EnumMap<>(RoomName.class);

                rooms.put(RoomName.HALLWAY, new Room(RoomName.HALLWAY, "in a dark hallway"));
                rooms.put(RoomName.STUDY, new Room(RoomName.STUDY, "in a study"));
                rooms.put(RoomName.INDOOR_GARDEN,
                                new Room(RoomName.INDOOR_GARDEN, "in a misty indoor garden"));
                rooms.put(RoomName.ROOT_CELLAR,
                                new Room(RoomName.ROOT_CELLAR, "in the root cellar"));
                rooms.put(RoomName.LIBRARY, new Room(RoomName.LIBRARY, "in the library"));
                rooms.put(RoomName.BILLIARD_ROOM,
                                new Room(RoomName.BILLIARD_ROOM, "in the billiard room"));
                rooms.put(RoomName.DEN, new Room(RoomName.DEN, "in the musty den"));
                rooms.put(RoomName.WINE_CELLAR,
                                new Room(RoomName.WINE_CELLAR, "in the wine cellar"));
                rooms.put(RoomName.BATHROOM,
                                new Room(RoomName.BATHROOM, "in a flooded bathroom...gross"));
                rooms.put(RoomName.OUTSIDE, new Room(RoomName.OUTSIDE,
                                "outside of the haunted house and the sun is so delightful!"));

                return rooms;
        }

        /**
         * Create all the items that will be referenced later. For example an item may be referenced
         * later by a character that is looking for a particular item.
         * 
         * @return A list of all the items
         */
        private Map<String, Item> createReferencedItems() {
                Map<String, Item> items = new HashMap<>();

                items.put("spade", new Item("spade", "an old spade", 1, false, PlayerStat.NONE, 0));
                items.put("key", new Item("key", "a rusty skeleton key", 1, false, PlayerStat.NONE,
                                0));
                items.put("potion", new Item("potion", "a powerful muscle building potion", 50,
                                true, PlayerStat.MAX_CARRY_WEIGHT, 50));

                return items;
        }

        /**
         * Place the items that will be referenced later into rooms.
         */
        private void placeReferencedItems(Map<RoomName, Room> rooms, Map<String, Item> items) {
                rooms.get(RoomName.INDOOR_GARDEN).addItem(items.get("spade"));
                rooms.get(RoomName.BATHROOM).addItem(items.get("key"));
        }

        /**
         * Create and place the unreferenced items into rooms. Unreferenced items are created and
         * placed in the room all within this method because they do not need to be refered to
         * later.
         * 
         * @param rooms The rooms to place items in
         */
        private void placeUnreferencedItems(Map<RoomName, Room> rooms) {
                rooms.get(RoomName.HALLWAY).addItem(
                                new Item("elixir", "an elixir", 50, true, PlayerStat.HEALTH, 10));
                rooms.get(RoomName.HALLWAY).addItem(new Item("cookie", "a magic cookie", 5, true,
                                PlayerStat.STRENGTH, 5));
                rooms.get(RoomName.INDOOR_GARDEN).addItem(
                                new Item("plant", "fox glove", 5, false, PlayerStat.NONE, 0));
                rooms.get(RoomName.WINE_CELLAR).addItem(new Item("crate", "a big old crate", 2000,
                                false, PlayerStat.NONE, 0));
                rooms.get(RoomName.BATHROOM).addItem(new Item("bucket", "an empty bucket", 20,
                                false, PlayerStat.NONE, 0));
        }

        /**
         * Place the characters in a room
         * 
         * @param rooms The rooms to place characters in
         * @param items The items that can be given to characters
         */
        private void placeCharacters(Map<RoomName, Room> rooms, Map<String, Item> items) {
                rooms.get(RoomName.BILLIARD_ROOM).addCharacter(new Character("Beatrice",
                                "How lovely to meet you. You didn't happen to see my spade...",
                                "Oh so you found it! Wonderful! Here is something that might come in handy...",
                                items.get("spade"), items.get("potion")));
        }

        /**
         * Connect the rooms together so that a player can move from room to room at specific
         * locations
         * 
         * @param rooms The rooms to wire connect
         */
        private void wireExits(Map<RoomName, Room> rooms) {
                rooms.get(RoomName.HALLWAY).setExit(Direction.NORTH, rooms.get(RoomName.DEN),
                                ExitType.UNLOCKED);
                rooms.get(RoomName.HALLWAY).setExit(Direction.SOUTH, rooms.get(RoomName.OUTSIDE),
                                ExitType.LOCKED);

                rooms.get(RoomName.STUDY).setExit(Direction.EAST, rooms.get(RoomName.INDOOR_GARDEN),
                                ExitType.UNLOCKED);

                rooms.get(RoomName.INDOOR_GARDEN).setExit(Direction.EAST,
                                rooms.get(RoomName.BILLIARD_ROOM), ExitType.UNLOCKED);
                rooms.get(RoomName.INDOOR_GARDEN).setExit(Direction.SOUTH,
                                rooms.get(RoomName.ROOT_CELLAR), ExitType.UNLOCKED);
                rooms.get(RoomName.INDOOR_GARDEN).setExit(Direction.WEST, rooms.get(RoomName.STUDY),
                                ExitType.UNLOCKED);

                rooms.get(RoomName.ROOT_CELLAR).setExit(Direction.EAST, rooms.get(RoomName.LIBRARY),
                                ExitType.UNLOCKED);
                rooms.get(RoomName.ROOT_CELLAR).setExit(Direction.SOUTH,
                                rooms.get(RoomName.WINE_CELLAR), ExitType.UNLOCKED);
                rooms.get(RoomName.ROOT_CELLAR).setExit(Direction.WEST, rooms.get(RoomName.DEN),
                                ExitType.UNLOCKED);

                rooms.get(RoomName.LIBRARY).setExit(Direction.NORTH,
                                rooms.get(RoomName.BILLIARD_ROOM), ExitType.UNLOCKED);
                rooms.get(RoomName.LIBRARY).setExit(Direction.WEST, rooms.get(RoomName.ROOT_CELLAR),
                                ExitType.UNLOCKED);

                rooms.get(RoomName.BILLIARD_ROOM).setExit(Direction.SOUTH,
                                rooms.get(RoomName.LIBRARY), ExitType.UNLOCKED);
                rooms.get(RoomName.BILLIARD_ROOM).setExit(Direction.WEST,
                                rooms.get(RoomName.INDOOR_GARDEN), ExitType.UNLOCKED);

                rooms.get(RoomName.DEN).setExit(Direction.EAST, rooms.get(RoomName.ROOT_CELLAR),
                                ExitType.UNLOCKED);
                rooms.get(RoomName.DEN).setExit(Direction.SOUTH, rooms.get(RoomName.HALLWAY),
                                ExitType.UNLOCKED);

                rooms.get(RoomName.WINE_CELLAR).setExit(Direction.NORTH,
                                rooms.get(RoomName.ROOT_CELLAR), ExitType.UNLOCKED);
                rooms.get(RoomName.WINE_CELLAR).setExit(Direction.EAST,
                                rooms.get(RoomName.BATHROOM), ExitType.UNLOCKED);

                rooms.get(RoomName.BATHROOM).setExit(Direction.WEST,
                                rooms.get(RoomName.WINE_CELLAR), ExitType.UNLOCKED);

                rooms.get(RoomName.OUTSIDE).setExit(Direction.NORTH, rooms.get(RoomName.HALLWAY),
                                ExitType.UNLOCKED);
        }
}
