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