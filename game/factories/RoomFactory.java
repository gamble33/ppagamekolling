package game.factories;

import game.Location;
import game.Npc;
import jsonParser.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RoomFactory {
    public static Map<String, Location> rooms = new HashMap<>();

    public static Location createRoom(JSONObject json) {
        String name = json.getString("name");
        String description = json.getString("description");

        Location location = new Location(description);
        
        for (JSONObject exit : json.getJsonArray("exits").<JSONObject>getList()) {
            String exitName = exit.getString("name");
            String roomName = exit.getString("room");
            location.setExit(exitName, new Location(roomName));
        }

        if (json.has("npcs")) {
            for (String npcName : json.getJsonArray("npcs").<String>getList()) {
                Npc npc = NpcFactory.npcs.get(npcName);
                location.addNpc(npc);
            }
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
