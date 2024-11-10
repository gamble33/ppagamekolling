package game.commands;

import game.Game;

public class QuitCommand extends Command {
    public QuitCommand(Game game) {
        super(game);
    }

    @Override
    public boolean execute(RawCommand command) {
        if (command.hasArgs()) {
            System.out.println("Quit what?");
            return false;
        }

        game.requestQuit();
        return true;
    }
}
