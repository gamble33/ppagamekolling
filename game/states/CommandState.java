package game.states;

import game.Game;
import game.Parser;
import game.callbacks.TextEntered;
import game.commands.*;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class CommandState implements GameState {
    private static final Map<String, Class<? extends Command>> commandDispatch = Map.ofEntries(
            Map.entry("quit", QuitCommand.class),
            Map.entry("help", HelpCommand.class),
            Map.entry("go", GoCommand.class),
            Map.entry("undo", UndoCommand.class),
            Map.entry("redo", RedoCommand.class),
            Map.entry("talk", TalkCommand.class),
            Map.entry("take", TakeCommand.class),
            Map.entry("drop", DropCommand.class),
            Map.entry("use", UseCommand.class),
            Map.entry("health", HealthCommand.class),
            Map.entry("inspect", InspectCommand.class)
    );
    private final Game game;
    private final Parser parser;
    private final Stack<Command> commandStack = new Stack<>();
    private final Stack<Command> redoCommandStack = new Stack<>();

    private final TextEntered entered = (input -> {
        processCommand(input);
    });

    public CommandState(Game game) {
        this.game = game;
        this.parser = new Parser();

    }

    @Override
    public void enter() {
        game.getView().addUIListener(entered);
    }

    @Override
    public void exit() {
        game.getView().removeUIListener(entered);
    }

    public static Set<String> getCommands() {
        return commandDispatch.keySet();
    }

    public Stack<Command> getCommandStack() {
        return commandStack;
    }

    public Stack<Command> getRedoCommandStack() {
        return redoCommandStack;
    }

    private void processCommand(String input) {
        RawCommand rawCommand = parser.getRawCommand(input);

        if (!commandDispatch.containsKey(rawCommand.getCommandName())) {
            String errorMessage = rawCommand.getCommandName() + " is not a valid command." +
                    '\n' +
                    "Type 'help' to see a list of valid commands.";
            game.getView().addText(errorMessage);
            return;
        }

        // Find the appropriate class of the command, instantiate it, push it to stack and execute it.
        Class<? extends Command> commandClass = commandDispatch.get(rawCommand.getCommandName());
        try {
            Command command = commandClass.getConstructor(Game.class, CommandState.class).newInstance(game, this);
            boolean commandSuccessful = command.execute(rawCommand);
            if (commandSuccessful && command.canUndo()) commandStack.push(command);
            if (!(command instanceof RedoCommand) && !(command instanceof UndoCommand))
                redoCommandStack.clear();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException exception) {
            System.err.println("Creating command instance problem: " + exception.getMessage());
        }
    }
}
