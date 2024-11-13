package game.states;

import game.Game;
import game.Parser;
import game.commands.*;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class CommandState implements GameState {
    private static final Map<String, Class<? extends Command>> commandDispatch = Map.of(
            "quit", QuitCommand.class,
            "help", HelpCommand.class,
            "go", GoCommand.class,
            "undo", UndoCommand.class,
            "redo", RedoCommand.class,
            "talk", TalkCommand.class
    );
    private final Game game;
    private final Parser parser;
    private final Stack<Command> commandStack = new Stack<>();
    private final Stack<Command> redoCommandStack = new Stack<>();
    private String currentInput = "";

    public CommandState(Game game) {
        this.game = game;
        this.parser = new Parser();
        game.getView().addUIListener(input -> {
            System.out.println(input.length());
            currentInput = input;
        });
    }

    @Override
    public void update() {
        if (currentInput.length() == 0) return;
        processCommand();
    }

    public Set<String> getCommands() {
        return commandDispatch.keySet();
    }

    public Stack<Command> getCommandStack() {
        return commandStack;
    }

    public Stack<Command> getRedoCommandStack() {
        return redoCommandStack;
    }

    private void processCommand() {
        RawCommand rawCommand = parser.getRawCommand(currentInput);
        currentInput = "";

        if (!commandDispatch.containsKey(rawCommand.getCommandName())) {
            StringBuilder errorMessage = new StringBuilder();
            errorMessage.append(rawCommand.getCommandName()).append(" is not a valid command.");
            errorMessage.append("Type 'help' to see a list of valid commands.");
            game.getView().addText(errorMessage.toString());
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
