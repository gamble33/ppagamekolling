package game.commands;

import game.Game;

public class RedoCommand extends Command {
    public RedoCommand(Game game) {
        super(game);
    }

    @Override
    public boolean execute(RawCommand rawCommand) {
        if (rawCommand.hasArgs()) {
            System.out.println("Darling, just type redo... nothing after that.");
            return false;
        }

        if (game.getRedoCommandStack().isEmpty()) {
            System.out.println("Nothing to redo.");
            return false;
        }

        Command command = game.getRedoCommandStack().pop();

        if (command.getRawCommand() == null) {
            System.err.println("Something went wrong. Redoing command has no raw command.");
            return false;
        }

        boolean commandResult = command.execute(command.getRawCommand());
        if (commandResult && command.canUndo()) {
            game.getCommandStack().push(command);
        }
        return true;
    }
}
