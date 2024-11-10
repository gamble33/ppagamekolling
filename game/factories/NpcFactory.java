package game.factories;

import java.util.HashMap;
import java.util.Map;

import game.Npc;
import jsonParser.JSONObject;

public class NpcFactory {
    public static Map<String, Npc> npcs = new HashMap<>();

    public static void createNpc(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String description = jsonObject.getString("description");
        int age = jsonObject.getInteger("age");

        Npc npc = new Npc(name, description, age);
        npcs.put(name, npc);
    }
}
