package game;

import game.commands.*;

import java.util.Map;
import java.util.Set;

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
    private final Parser parser;
    private Location currentLocation;
    private final Map<String, Command> commandDispatch;
    private boolean gameRunning = true;

    /**
     * Create the game and initialise its internal map.
     */
    public Game(Location startingLocation)
    {
        this.currentLocation = startingLocation;
        parser = new Parser();

        this.commandDispatch = Map.of(
                "quit", new QuitCommand(this),
                "help", new HelpCommand(this),
                "go", new GoCommand(this)
        );
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        while (gameRunning) {
            processCommand();

        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    public void requestQuit() {
        gameRunning = false;
    }

    public Set<String> getCommands() {
        return commandDispatch.keySet();
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void MoveTo(Location newLocation) {
        this.currentLocation = newLocation;
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        new LocationDisplay().showLocationSummary(currentLocation);
    }

    private void processCommand()
    {
        RawCommand rawCommand = parser.getRawCommand();

        if (!commandDispatch.containsKey(rawCommand.getCommandName())) {
            System.out.println(rawCommand.getCommandName() + " is not a valid command.");
            System.out.println("Type 'help' to see a list of valid commands.");
            return;
        }

        commandDispatch.get(rawCommand.getCommandName()).execute(rawCommand);
    }

    // implementations of user commands:

}
