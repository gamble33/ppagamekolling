package game.commands;

import game.Game;
import game.Location;
import game.LocationDisplay;

/**
 * Try to in to one direction. If there is an exit, enter the new
 * room, otherwise print an error message.
 */
public class GoCommand implements Command {
    private Game game;
    private LocationDisplay locationDisplay;

    public GoCommand(Game game) {
        this.game = game;
        this.locationDisplay = new LocationDisplay();
    }

    @Override
    public void execute(RawCommand command) {
        if (!command.hasArgs()) {
            // if there is no second argument, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getArgs().get(0);

        // Try to leave current room.
        Location nextLocation = game.getCurrentLocation().getExit(direction);

        if (nextLocation == null) {
            System.out.println(direction + " is not a valid direction!");
        } else {
            game.MoveTo(nextLocation);
            locationDisplay.showLocationSummary(nextLocation);
        }
    }
}
