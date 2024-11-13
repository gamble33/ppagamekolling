package game;

import game.dialogue.Dialogue;

import java.util.HashMap;
import java.util.Map;

public class Npc {
    private final String name;
    private final String description;
    private final int age;
    private final Map<String, Dialogue> dialogueMap;

    public Npc(String name, String description, int age) {
        this.name = name;
        this.description = description;
        this.age = age;
        this.dialogueMap = new HashMap<String, Dialogue>();
    }

    public boolean canTalkTo() {
        return dialogueMap.size() > 0;
    }

    public void addDialogue(String dialogueId, Dialogue dialogue) {
        dialogueMap.put(dialogueId, dialogue);
    }

    public String getName() {
        return name;
    }

    public Dialogue getDialogue(String dialogueId) {
        return dialogueMap.get(dialogueId);
    }
    
    @Override
    public String toString() {
        return name + ", " + description + ", " + age;
    }
    
}
