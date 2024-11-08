package game.factories;

import game.Room;
import jsonParser.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RoomFactory {
    public static Map<String, Room> rooms = new HashMap<>();

    public static Room createRoom(JSONObject json) {
        String name = json.getString("name");
        String description = json.getString("description");

        Room room = new Room(description);
        
        for (JSONObject exit : json.getJsonArray("exits").<JSONObject>getList()) {
            String exitName = exit.getString("name");
            String roomName = exit.getString("room");
            room.setExit(exitName, new Room(roomName));
        }
        
        rooms.put(name, room);
        return room;
    }

    public static void injectDependencies() {
        for (Room room : rooms.values()) {
            for (Map.Entry<String, Room> exit : room.getExits().entrySet()) {
                room.setExit(exit.getKey(), rooms.get(exit.getValue().getShortDescription()));
            }
        }
    }

    public static Room getRoom(String name) {
        return rooms.get(name);
    }
}
