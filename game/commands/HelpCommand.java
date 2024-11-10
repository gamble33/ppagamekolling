package game.commands;

import game.Game;

public class HelpCommand implements Command {
    public Game game;

    public HelpCommand(Game game) {
        this.game = game;
    }

    @Override
    public void execute(RawCommand command) {
        // TODO: show help for specific command.
        if (command.hasArgs()) return;

        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");

        String validCommands = String.join(" ", game.getCommands());
        System.out.println(validCommands);
    }
}
