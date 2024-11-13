package game.commands;

import game.Game;
import game.states.CommandState;

public class HelpCommand extends Command {

    public HelpCommand(Game game, CommandState commandState) {
        super(game, commandState);
    }

    @Override
    public boolean execute(RawCommand command) {
        // TODO: show help for specific command.
        if (command.hasArgs()) return false;

        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");

        String validCommands = String.join(" ", commandState.getCommands());
        System.out.println(validCommands);
        return true;
    }
}
