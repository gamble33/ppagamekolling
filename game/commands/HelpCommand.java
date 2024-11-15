package game.commands;

import game.Game;
import game.states.CommandState;
import game.ui.GameView;

public class HelpCommand extends Command {

    public HelpCommand(Game game, CommandState commandState) {
        super(game, commandState);
    }

    public static void printHelp(GameView view) {
        String helpMessage = "Your command words are: ";
        String validCommands = String.join(" ", CommandState.getCommands());
        helpMessage += validCommands;
        view.addText(helpMessage);
    }

    @Override
    public boolean execute(RawCommand command) {
        // TODO: show help for specific command.
        if (command.hasArgs()) return false;
        printHelp(game.getView());
        return true;
    }
}
