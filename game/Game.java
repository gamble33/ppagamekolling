package game;

import game.commands.*;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

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
    private final Stack<Command> commandStack = new Stack<>();
    private final Stack<Command> redoCommandStack = new Stack<>();
    private final Map<String, Class<? extends Command>> commandDispatch;

    private Location currentLocation;
    private boolean gameRunning = true;

    /**
     * Create the game and initialise its internal map.
     */
    public Game(Location startingLocation)
    {
        this.currentLocation = startingLocation;
        parser = new Parser();

        this.commandDispatch = Map.of(
                "quit", QuitCommand.class,
                "help", HelpCommand.class,
                "go", GoCommand.class,
                "undo", UndoCommand.class,
                "redo", RedoCommand.class
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
        System.out.println("Thank you for playing. Good bye.");
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

    public Stack<Command> getCommandStack() {
        return commandStack;
    }

    public Stack<Command> getRedoCommandStack() {
        return redoCommandStack;
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

        // Find the appropriate class of the command, instantiate it, push it to stack and execute it.
        Class<? extends Command> commandClass = commandDispatch.get(rawCommand.getCommandName());
        try {
            Command command = commandClass.getConstructor(Game.class).newInstance(this);
            boolean commandSuccessful = command.execute(rawCommand);
            if (commandSuccessful && command.canUndo()) commandStack.push(command);
            if (!(command instanceof RedoCommand) && !(command instanceof UndoCommand))
                redoCommandStack.clear();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException exception) {
            System.err.println("Creating command instance problem: " + exception.getMessage());
        }
    }

    // implementations of user commands:

}
