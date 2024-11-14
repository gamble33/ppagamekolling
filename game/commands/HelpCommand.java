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

        String helpMessage = "Your command words are: ";

        String validCommands = String.join(" ", commandState.getCommands());
        helpMessage += validCommands;
        game.getView().addText(helpMessage);
        return true;
    }
}
