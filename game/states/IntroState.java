package game.states;

import game.Game;
import game.LocationDisplay;

public class IntroState implements GameState {
    private final Game game;

    public IntroState(Game game) {
        this.game = game;
    }

    @Override
    public void update() {
        printWelcome();
        game.changeState(new CommandState(game));
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        StringBuilder welcome = new StringBuilder();
        welcome.append("Welcome to the World of Zuul!\n");
        welcome.append("World of Zuul is a new, incredibly boring adventure game.\n");
        welcome.append("Type 'help' if you need help.\n");
        game.getView().addText(welcome.toString());
        new LocationDisplay(game.getView()).showLocationSummary(game.getCurrentLocation());
    }
}
