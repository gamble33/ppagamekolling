package game.commands;

import game.Game;
import game.states.CommandState;

public class RedoCommand extends Command {

    public RedoCommand(Game game, CommandState commandState) {
        super(game, commandState);
    }

    @Override
    public boolean execute(RawCommand rawCommand) {
        if (rawCommand.hasArgs()) {
            System.out.println("Darling, just type redo... nothing after that.");
            return false;
        }

        if (commandState.getRedoCommandStack().isEmpty()) {
            System.out.println("Nothing to redo.");
            return false;
        }

        Command command = commandState.getRedoCommandStack().pop();

        if (command.getRawCommand() == null) {
            System.err.println("Something went wrong. Redoing command has no raw command.");
            return false;
        }

        boolean commandResult = command.execute(command.getRawCommand());
        if (commandResult && command.canUndo()) {
            commandState.getCommandStack().push(command);
        }
        return true;
    }
}
