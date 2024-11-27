package game.factories;

import game.Exit;
import game.Location;
import game.Npc;
import game.item.InventoryItem;
import game.item.Item;
import game.item.LocationItem;
import jsonParser.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LocationFactory {
    public static Map<String, Location> rooms = new HashMap<>();

    public static Location createRoom(JSONObject json) {
        String name = json.getString("name");
        String title = json.getString("title");
        String description = json.getString("description");
        Location location = new Location(title, description);

        if (json.has("music")) {
            String music = json.getString("music");
            location.setMusic(music);
        }

        if (json.has("image")) {
            String image = json.getString("image");
            location.setImage(image);
        }

        for (JSONObject exit : json.getJsonArray("exits").<JSONObject>getList()) {
            String exitName = exit.getString("name");
            String roomName = exit.getString("room");
            String exitRequirement = exit.has("itemRequirement") ? exit.getString("itemRequirement") : null;
            location.setExit(exitName, new Exit(exitName, new Location("", roomName), exitRequirement));
        }

        if (json.has("npcs")) {
            for (String npcName : json.getJsonArray("npcs").<String>getList()) {
                Npc npc = NpcFactory.npcs.get(npcName);
                location.addNpc(npc);
            }
        }

        if (json.has("items")) {
            json.getJsonArray("items").<JSONObject>getList().forEach(itemJSON -> {
                String id = itemJSON.getString("id");
                String itemDescription = itemJSON.getString("description");
                int quantity = itemJSON.getInteger("quantity");
                Item item = ItemFactory.getItem(id);
                InventoryItem locationItem = new InventoryItem(item, quantity);
                location.getLocationInventory().addItem(locationItem);
            });
        }
        
        rooms.put(name, location);
        return location;
    }

    public static void injectDependencies() {
        for (Location location : rooms.values()) {
            for (Map.Entry<String, Exit> exit : location.getExits().entrySet()) {
                if (rooms.get(exit.getValue().getLocation().getShortDescription()) == null) {
                    throw new RuntimeException(location.getTitle() + " couldn't find exit: " + exit.getValue().getLocation().getShortDescription());
                }
                Exit actualExit = new Exit(
                        exit.getKey(),
                        rooms.get(exit.getValue().getLocation().getShortDescription()),
                        exit.getValue().getItemRequirement()
                );
                location.setExit(exit.getKey(), actualExit);
            }
        }
    }

    public static Location getRoom(String name) {
        return rooms.get(name);
    }
}
