package game.factories;

import game.Npc;
import jsonParser.JSONObject;

public class NpcFactory {
    public static Npc createNpc(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String description = jsonObject.getString("description");
        int age = jsonObject.getInteger("age");

        Npc npc = new Npc(name, description, age);
        return npc;
    }
}
