package game;
import game.commands.Command;
import game.commands.CommandWords;
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
    private final Scanner reader;         // source of command input

    /**
     * Create a parser to read from the terminal window.
     */
    public Parser() 
    {
        reader = new Scanner(System.in);
    }

    /**
     * @return The next raw command from the user.
     */
    public RawCommand getRawCommand(String inputLine)
    {
        List<String> arguments = new ArrayList<>();
        inputLine = reader.nextLine();

        // Find all words on the line.
        Scanner tokenizer = new Scanner(inputLine);
        while (tokenizer.hasNext()) {
            arguments.add(tokenizer.next());
        }

        String commandName = arguments.get(0);

        return new RawCommand(commandName, arguments.subList(1, arguments.size()));
    }
}
