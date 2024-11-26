package game;

import game.dialogue.Dialogue;
import game.enums.Behaviour;

import java.util.HashMap;
import java.util.Map;

public class Npc {
    private final String name;
    private final String description;
    private final Behaviour behaviour;
    private final int age;
    private final Map<String, Dialogue> dialogueMap;

    private float health;

    public Npc(String name, String description, int age, float maxHealth, Behaviour behaviour) {
        this.name = name;
        this.description = description;
        this.age = age;
        this.dialogueMap = new HashMap<>();
        this.health = maxHealth;
        this.behaviour = behaviour;
    }

    /**
     * Attacking NPC will lower its health and cause it to initiate its 'reaction-to-being-attacked' procedure
     * based on its behaviour.
     * @param damage The amount of health to deduct from the NPC.
     * @return True if NPC was killed due to the attack.
     */
    public boolean attack(float damage) {
        health -= damage;
        if (health <= 0) {
            health = 0;
            return true;
        }
        return false;
    }

    public Behaviour getBehaviour() {
        return behaviour;
    }

    /**
     * Determines if the NPC can engage in a dialogue.
     */
    public boolean canTalkTo() {
        return !dialogueMap.isEmpty();
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
