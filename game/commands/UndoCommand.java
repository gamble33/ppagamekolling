package game.commands;

import game.Game;
import game.states.CommandState;

public class UndoCommand extends Command {

    public UndoCommand(Game game, CommandState commandState) {
        super(game, commandState);
    }

    @Override
    public boolean execute(RawCommand rawCommand) {
        if (rawCommand.hasArgs()) {
            game.getView().addText("Darling, just type undo... nothing after that.");
            return false;
        }

        if (commandState.getCommandStack().isEmpty()) {
            game.getView().addText("Nothing to undo.");
            return false;
        }

        Command command = commandState.getCommandStack().pop();
        commandState.getRedoCommandStack().push(command);
        command.undo();
        return true;
    }
}
