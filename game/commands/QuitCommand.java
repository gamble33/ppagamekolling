package game.commands;

import game.Game;

public class QuitCommand implements Command {
    private final Game game;

    public QuitCommand(Game game) {
        this.game = game;
    }

    @Override
    public void execute(RawCommand command) {
        if (command.hasArgs()) {
            System.out.println("Quit what?");
            return;
        }

        game.requestQuit();
    }
}
