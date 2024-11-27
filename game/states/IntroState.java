package game.states;

import game.Game;
import game.commands.HelpCommand;

public class IntroState implements GameState {
    private final Game game;

    public IntroState(Game game) {
        this.game = game;
    }

    @Override
    public void enter() {
        printWelcome();
        HelpCommand.printHelp(game.getView());
        game.changeState(new CommandState(game));
    }

    @Override
    public void exit() {}

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        game.getView().addText("Type 'help' if you need help. ");
    }
}
