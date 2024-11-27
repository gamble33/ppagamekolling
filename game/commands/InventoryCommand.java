package game.commands;

import game.Game;
import game.states.CommandState;

public class InventoryCommand extends Command {
    public InventoryCommand(Game game, CommandState commandState) {
        super(game, commandState);
    }

    @Override
    public boolean execute(RawCommand rawCommand) {
        if (rawCommand.hasArgs()) {
            game.getView().addText("Just write `inventory`, no arguments please.");
            return false;
        }

        game.getInventory().displayItems("You have", game.getView());
        return true;
    }
}
