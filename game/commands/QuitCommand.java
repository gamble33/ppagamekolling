package game.commands;

import game.Game;
import game.states.CommandState;

public class QuitCommand extends Command {
    public QuitCommand(Game game, CommandState commandState) {
        super(game, commandState);
    }

    @Override
    public boolean execute(RawCommand command) {
        if (command.hasArgs()) {
            game.getView().addText("Quit what?");
            return false;
        }

        game.requestQuit();
        return true;
    }
}
