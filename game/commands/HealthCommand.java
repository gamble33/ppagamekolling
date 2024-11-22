package game.commands;

import game.Game;
import game.states.CommandState;

public class HealthCommand extends Command {
    public HealthCommand(Game game, CommandState commandState) {
        super(game, commandState);
    }

    @Override
    public boolean execute(RawCommand rawCommand) {
        if (rawCommand.hasArgs()) {
            System.out.println("Darling, just type health... nothing after that.");
            return false;
        }

        game.getView().addText("Saturation: " + game.getSaturation() + "🍖");
        game.getView().addText("Health: " + game.getHealth() + "❤");
        return true;
    }
}
