package game;

import game.enums.Behaviour;
import game.item.Inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class Room - a room in an adventure game.
 * <p>
 * This class is part of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game.
 * <p>
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Location
{
    private final String description;
    private final Map<String, Exit> exits;        // stores exits of this room.
    private final List<Npc> npcs;
    private final Inventory locationInventory;
    private final String title;
    private String music;
    private String image;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open courtyard".
     * @param description The room's description.
     */
    public Location(String title, String description)
    {
        this.title = title;
        this.description = description;
        this.exits = new HashMap<>();
        this.npcs = new ArrayList<>();

        // Location inventories can hold as many items as one could dream of.
        this.locationInventory = new Inventory(Float.MAX_VALUE);
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void addNpc(Npc npc) {
        npcs.add(npc);
    }

    public void removeNpc(Npc npc) {
        npcs.remove(npc);
    }

    public List<Npc> getNpcList() {
        return npcs;
    }

    public Inventory getLocationInventory() {
        return locationInventory;
    }

    public Npc getNpc(String name) {
        return npcs.stream()
                .filter(npc -> npc.getName().toLowerCase().equals(name))
                .findFirst()
                .orElse(null);
    }

    /**
     *
     */
    public List<Npc> getTalkableNpcs() {
        return npcs.stream()
                .filter(Npc::canTalkTo)
                .toList();
    }

    public boolean hasNpc(String name) {
        return npcs.stream().anyMatch(npc -> npc.getName().toLowerCase().equals(name));
    }

    /**
     * Determines if the player can leave the current location.
     * The player can leave if there are no NPCs with aggressive behavior or NPCs with required dialogue.
     *
     * @return {@code true} if the player can leave, {@code false} otherwise.
     */
    public boolean canLeave() {
        return npcs
                .stream()
                .noneMatch(npc -> npc.getBehaviour().equals(Behaviour.Aggressive) || npc.isRequiredDialogue());
    }

    /**
     * Generates a message explaining why the player cannot leave the current location.
     * This method checks for NPCs with aggressive behavior and lists their names
     * as obstacles preventing the player from leaving the location.
     *
     * @return A string message listing aggressive NPCs and stating they are stopping the player from leaving.
     */
    public String getLeaveReason() {
        if (hasAggressiveNpc()) {
            return npcs
                    .stream()
                    .filter(npc -> npc.getBehaviour().equals(Behaviour.Aggressive))
                    .map(npc -> npc.getName() + ", ")
                    .collect(Collectors.joining()) + " stand(s) in the way. Stopping you from leaving.";
        }

        return npcs
                .stream()
                .filter(npc -> npc.isRequiredDialogue())
                .findFirst()
                .get()
                .getName() + " would like to talk and stands in the way. Stopping you from leaving.";
    }

    /**
     * Define an exit from this room.
     * @param name The exit name.
     * @param exit  The object containing the requirement to take the ext and the location to which the exit leads.
     */
    public void setExit(String name, Exit exit)
    {
        exits.put(name, exit);
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    public String getTitle() {
        return title;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Exit getExit(String direction)
    {
        return exits.get(direction);
    }

    public Map<String, Exit> getExits() {
        return exits;
    }

    public void setMusic(String music) {
        this.music = music;
    }

    public String getMusic() {
        return music;
    }

    public boolean hasMusic() {
        return music != null;
    }

    public boolean hasImage() {
        return image != null;
    }

    private boolean hasAggressiveNpc() {
        return npcs.stream().anyMatch(npc -> npc.getBehaviour().equals(Behaviour.Aggressive));
    }
}

