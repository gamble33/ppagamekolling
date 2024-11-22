package game.states;

import game.Game;
import game.LocationDisplay;
import game.commands.HelpCommand;
import game.commands.RawCommand;

import java.util.ArrayList;

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
        StringBuilder welcome = new StringBuilder();
        welcome.append("Welcome to the World of Dudi! ");
        welcome.append("World of Dudi is a new, incredibly boring adventure game. ");
        welcome.append("Type 'help' if you need help. ");
        game.getView().addText(welcome.toString());
        new LocationDisplay(game.getView()).showLocationSummary(game.getCurrentLocation());
    }
}
