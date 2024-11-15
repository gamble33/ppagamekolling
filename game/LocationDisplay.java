package game;

import game.ui.GameView;

import java.util.Set;

/**
 * The RoomDisplay class is responsible for presenting room information.
 */
public class LocationDisplay {
    private final GameView view;

    public LocationDisplay(GameView view) {
        this.view = view;
    }

    public void showLocationSummary(Location location) {
        String description = "You are " + location.getShortDescription();
        view.addText(description);
        displayExits(location);
        displayNpcs(location);
    }

    public void displayNpcs(Location location) {
        if (location.getNpcList().isEmpty()) {
            view.addText("Nobody is around you. You are alone.");
        } else {
            StringBuilder names = new StringBuilder("The following are around you: ");
            location.getNpcList().forEach(npc -> {
                names.append(npc.getName() + " ");
            });
            view.addText(names.toString());
        }
    }

    /**
     * Return a string describing the location's exits, for example
     * "Exits: north-west".
     *
     * @return Details of the location's exits.
     */
    public void displayExits(Location location) {
        String returnString = "Exits:";
        Set<String> keys = location.getExits().keySet();
        for (String exit : keys) {
            returnString += " " + exit;
        }
        view.addText(returnString);
    }
}