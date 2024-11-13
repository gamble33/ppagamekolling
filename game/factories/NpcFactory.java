package game.factories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.dialogue.Dialogue;
import game.Npc;
import game.dialogue.Response;
import jsonParser.JSONObject;

public class NpcFactory {
    public static Map<String, Npc> npcs = new HashMap<>();

    public static void createNpc(JSONObject json) {
        String name = json.getString("name");
        String description = json.getString("description");
        int age = json.getInteger("age");

        Npc npc = new Npc(name, description, age);

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
        }

        npcs.put(name, npc);
    }
}
