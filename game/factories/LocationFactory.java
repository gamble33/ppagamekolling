package game.factories;

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


        for (JSONObject exit : json.getJsonArray("exits").<JSONObject>getList()) {
            String exitName = exit.getString("name");
            String roomName = exit.getString("room");
            location.setExit(exitName, new Location("", roomName));
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
            for (Map.Entry<String, Location> exit : location.getExits().entrySet()) {
                location.setExit(exit.getKey(), rooms.get(exit.getValue().getShortDescription()));
            }
        }
    }

    public static Location getRoom(String name) {
        return rooms.get(name);
    }
}
