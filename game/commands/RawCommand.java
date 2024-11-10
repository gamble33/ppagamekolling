package game.commands;

import java.util.List;

/**
 * The RawCommand class represents a command that is yet to be completely parsed and validated.
 * It stores command in its raw form as inputted by the user.
 */
public class RawCommand {
    String commandName;
    List<String> args;
    public RawCommand(String commandName, List<String> args) {
        this.commandName = commandName;
        this.args = args;
    }

    public String getCommandName() {
        return commandName;
    }

    public List<String> getArgs() {
        return args;
    }

    public boolean hasArgs() {
        return !args.isEmpty();
    }
}
