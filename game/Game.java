package game;

import game.states.GameState;
import game.states.IntroState;
import game.ui.GameView;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * <p>
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * <p>
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game 
{
    private final GameView view;

    private GameState gameState;
    private Location currentLocation;

    /**
     * Create the game and initialise its starting location.
     */
    public Game(Location startingLocation)
    {
        this.view = new GameView();
        view.show();

        this.currentLocation = startingLocation;
        this.gameState = new IntroState(this);

    }

    public void play()
    {            
        new Thread(() -> gameState.enter()).start();
    }

    public void changeState(GameState newState) {
        gameState.exit();
        gameState = newState;
        gameState.enter();
    }

    public void requestQuit() {
        // TODO
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void MoveTo(Location newLocation) {
        this.currentLocation = newLocation;
    }

    public GameView getView() {
        return view;
    }


}
