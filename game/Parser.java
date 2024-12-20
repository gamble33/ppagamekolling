package game;
import game.commands.RawCommand;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 * <p>
 * This parser reads user input and tries to interpret it as an "Adventure"
 * command. Every time it is called it reads a line from the terminal and
 * tries to interpret the line as a two word command. It returns the command
 * as an object of class Command.
 * <p>
 * The parser has a set of known command words. It checks user input against
 * the known commands, and if the input is not one of the known commands, it
 * returns a command object that is marked as an unknown command.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */
public class Parser 
{
    /**
     * @return The next raw command from the user.
     */
    public RawCommand getRawCommand(String inputLine)
    {
        inputLine = inputLine.trim().toLowerCase();
        List<String> arguments = new ArrayList<>();

        // Find all words on the line.
        Scanner tokenizer = new Scanner(inputLine);
        while (tokenizer.hasNext()) {
            arguments.add(tokenizer.next());
        }

        String commandName = arguments.stream().findFirst().orElse("");

        if (arguments.isEmpty()) return new RawCommand(commandName, arguments);
        return new RawCommand(commandName, arguments.subList(1, arguments.size()));
    }
}
