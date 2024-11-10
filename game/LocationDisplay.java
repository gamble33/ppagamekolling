package game;

import java.util.Set;

/**
 * The RoomDisplay class is responsible for presenting room information.
 */
public class LocationDisplay {

    public LocationDisplay() {
    }

    public void showLocationSummary(Location location) {
        String description = "You are " + location.getShortDescription() +
                ".\n" + getExitString(location);
        System.out.println(description);
        displayNpcs(location);
    }

    public void displayNpcs(Location location) {
        if (location.getNpcList().isEmpty()) {
            System.out.println("Nobody is around you. You are alone.");
        } else {
            System.out.print("The following are around you: ");
            location.getNpcList().forEach(npc -> {
                System.out.print(npc.getName() + " ");
            });
            System.out.println();
        }
    }

    /**
     * Return a string describing the location's exits, for example
     * "Exits: north-west".
     *
     * @return Details of the location's exits.
     */
    private String getExitString(Location location) {
        String returnString = "Exits:";
        Set<String> keys = location.getExits().keySet();
        for (String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }
}