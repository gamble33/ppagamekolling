package game.commands;

import game.Game;
import game.Location;
import game.LocationDisplay;

/**
 * Try to in to one direction. If there is an exit, enter the new
 * room, otherwise print an error message.
 */
public class GoCommand extends Command {
    private LocationDisplay locationDisplay;
    private Location previousLocation;

    public GoCommand(Game game) {
        super(game);
        this.canUndo = true;
        this.locationDisplay = new LocationDisplay();
    }

    @Override
    public boolean execute(RawCommand command) {
        if (!command.hasArgs()) {
            // if there is no second argument, we don't know where to go...
            System.out.println("Go where?");
            return false;
        }

        String direction = command.getArgs().get(0);

        // Try to leave current room.
        Location currentLocation = game.getCurrentLocation();
        Location nextLocation = currentLocation.getExit(direction);

        if (nextLocation == null) {
            System.out.println(direction + " is not a valid direction!");
            return false;
        }

        game.MoveTo(nextLocation);
        locationDisplay.showLocationSummary(nextLocation);
        previousLocation = currentLocation;
        setRawCommand(command);
        return true;
    }

    @Override
    public void undo() {
        game.MoveTo(previousLocation);
        locationDisplay.showLocationSummary(previousLocation);
    }
}
