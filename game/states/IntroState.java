package game.states;

import game.Game;
import game.LocationDisplay;

public class IntroState implements GameState {
    private final Game game;

    public IntroState(Game game) {
        this.game = game;
    }

    @Override
    public void enter() {
        printWelcome();
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
        welcome.append("Welcome to the World of Zuul! ");
        welcome.append("World of Zuul is a new, incredibly boring adventure game. ");
        welcome.append("Type 'help' if you need help. ");
        game.getView().addText(welcome.toString());
        new LocationDisplay(game.getView()).showLocationSummary(game.getCurrentLocation());
    }
}
