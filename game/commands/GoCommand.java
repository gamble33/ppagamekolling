package game.commands;

import game.Exit;
import game.Game;
import game.Location;
import game.LocationDisplay;
import game.states.CommandState;

/**
 * Try to in to one direction. If there is an exit, enter the new
 * room, otherwise print an error message.
 */
public class GoCommand extends Command {
    private final LocationDisplay locationDisplay;
    private Location previousLocation;

    public GoCommand(Game game, CommandState commandState) {
        super(game, commandState);
        this.canUndo = true;
        this.locationDisplay = new LocationDisplay(game.getView());
    }

    @Override
    public boolean execute(RawCommand command) {
        if (!command.hasArgs()) {
            // if there is no second argument, we don't know where to go...
            game.getView().addText("type go <direction>");
            return false;
        }

        String direction = command.getArgs().get(0);

        // Try to leave current room.
        Location currentLocation = game.getCurrentLocation();

        Exit exit = currentLocation.getExit(direction);
        Location nextLocation = exit.getLocation();

        if (nextLocation == null) {
            game.getView().addText(direction + " is not a valid direction!");
            locationDisplay.displayExits(currentLocation);
            return false;
        }

        // Check if player has the required item in order to go through this exit. (If there exists any item requirement).
        if (exit.hasItemRequirement()) {
            String itemRequirement = exit.getItemRequirement();
            if (!game.getInventory().has(itemRequirement)) {
                game.getView().addText("To go through here, you need: " + itemRequirement);
                return false;
            }
        }

        game.moveTo(nextLocation);
        previousLocation = currentLocation;
        setRawCommand(command);
        return true;
    }

    @Override
    public void undo() {
        game.moveTo(previousLocation);
    }
}
