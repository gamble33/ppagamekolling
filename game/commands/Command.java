package game.commands;
import java.util.List;

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
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public interface Command
{
    void execute(RawCommand rawCommand);
}

