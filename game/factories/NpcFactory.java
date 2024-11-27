package game.factories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.Damage;
import game.dialogue.Dialogue;
import game.Npc;
import game.dialogue.Response;
import game.enums.Behaviour;
import game.item.InventoryItem;
import jsonParser.JSONObject;

public class NpcFactory {
    public static Map<String, Npc> npcs = new HashMap<>();

    public static void createNpc(JSONObject json) {
        String name = json.getString("name");
        String description = json.getString("description");
        int age = json.getInteger("age");
        float maxHealth = json.getFloat("maxHealth");

        Behaviour behaviour = switch (json.getString("behaviour")) {
            case "passive" -> Behaviour.Passive;
            case "neutral" -> Behaviour.Neutral;
            case "aggressive" -> Behaviour.Aggressive;
            default -> throw new RuntimeException("Invalid behaviour json string.");
        };

        behaviour.setDamage(new Damage(
                json.getFloat("damage"),
                json.getString("damageDescription")
        ));

        Npc npc = new Npc(name, description, age, maxHealth, behaviour);

        if (json.has("itemDrops")) {
            json.getJsonArray("itemDrops").<JSONObject>getList().forEach( jsonDrop -> {
                npc.addDrop(new InventoryItem(
                        ItemFactory.getItem(jsonDrop.getString("id")),
                        jsonDrop.getInteger("quantity")
                ));
            }
            );
        }


        if (json.has("dialogue")) {
            json.getJsonArray("dialogue").<JSONObject>getList().forEach(jsonDialogue -> {
                String id = jsonDialogue.getString("id");
                String message = jsonDialogue.getString("message");
                List<Response> responses = new ArrayList<>();
                jsonDialogue.getJsonArray("responses").<JSONObject>getList().forEach(jsonResponse -> {
                    String responseString = jsonResponse.getString("response");
                    String nextDialogue = jsonResponse.getString("nextDialogue");
                    Response response = new Response(responseString, nextDialogue);
                    responses.add(response);
                });
                Dialogue dialogue = new Dialogue(message, responses);
                npc.addDialogue(id, dialogue);
            });

            if (json.has("requiredDialogue")) npc.setRequiredDialogue(json.getBoolean("requiredDialogue"));
        }

        npcs.put(name, npc);
    }
}
