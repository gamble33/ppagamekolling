package game;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private final Map<String, Location> exits;        // stores exits of this room.
    private final List<Npc> npcs;
    private final String title;
    private String music;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Location(String title, String description)
    {
        this.title = title;
        this.description = description;
        this.exits = new HashMap<>();
        this.npcs = new ArrayList<>();
    }

    public void addNpc(Npc npc) {
        npcs.add(npc);
    }

    public List<Npc> getNpcList() {
        return npcs;
    }

    public Npc getNpc(String name) {
        return npcs.stream()
                .filter(npc -> npc.getName().toLowerCase().equals(name))
                .findFirst()
                .orElse(null);
    }

    public List<Npc> getTalkableNpcs() {
        return npcs.stream()
                .filter(Npc::canTalkTo)
                .toList();
    }

    public boolean hasNpc(String name) {
        return npcs.stream().anyMatch(npc -> npc.getName().toLowerCase().equals(name));
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Location neighbor)
    {
        exits.put(direction, neighbor);
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
    public Location getExit(String direction)
    {
        return exits.get(direction);
    }

    public Map<String, Location> getExits() {
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
}

