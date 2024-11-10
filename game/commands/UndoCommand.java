package game.commands;

import game.Game;

public class UndoCommand extends Command {

    public UndoCommand(Game game) {
        super(game);
    }

    @Override
    public boolean execute(RawCommand rawCommand) {
        if (rawCommand.hasArgs()) {
            System.out.println("Darling, just type undo... nothing after that.");
            return false;
        }

        if (game.getCommandStack().isEmpty()) {
            System.out.println("Nothing to undo.");
            return false;
        }

        Command command = game.getCommandStack().pop();
        game.getRedoCommandStack().push(command);
        command.undo();
        return true;
    }
}
