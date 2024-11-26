package game.commands;

import game.Game;
import game.states.CommandState;

/**
 * This class is part of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game.
 * <p>
 * This class holds information about a command that was issued by the user.
 * A command currently consists of two strings: a command word and a second
 * word (for example, if the command was "take map", then the two strings
 * obviously are "take" and "map").
 * <p>
 * The way this is used is: Commands are already checked for being valid
 * command words. If the user entered an invalid command (a word that is not
 * known) then the command word is <null>.
 * <p>
 * If the command had only one word, then the second word is <null>.
 *
 * @author Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public abstract class Command {
    protected final Game game;
    protected final CommandState commandState;
    protected boolean canUndo = false;
    private RawCommand rawCommand = null;

    public Command(Game game, CommandState commandState) {
        this.game = game;
        this.commandState = commandState;
    }

    /**
     * Executes the command, if the command was not executed successfully (perhaps due to the user structuring
     * the request improperly), the method will return false. If the command was executed successfully, true is
     * returned.
     *
     * @param rawCommand The user's raw input (prior to validation).
     * @return False if command was not executed.
     */
    public boolean execute(RawCommand rawCommand) {
        return false;
    }

    public void undo() {
    }

    public boolean canUndo() {
        return canUndo;
    }

    public RawCommand getRawCommand() {
        return rawCommand;
    }

    protected void setRawCommand(RawCommand rawCommand) {
        this.rawCommand = rawCommand;
    }
}

