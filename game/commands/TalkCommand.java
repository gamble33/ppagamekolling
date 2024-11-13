package game.commands;

import game.Game;
import game.Location;
import game.Npc;
import game.states.CommandState;
import game.states.TalkState;

public class TalkCommand extends Command {
    public TalkCommand(Game game, CommandState commandState) {
        super(game, commandState);
    }

    @Override
    public boolean execute(RawCommand rawCommand) {
        Location location = game.getCurrentLocation();
        if (rawCommand.getArgs().size() != 1) {
            System.out.println("talk command requires 1 argument (the name of whom you would like to talk with).");
            listTalkableNpcs(location);
            return false;
        }

        String npcName = rawCommand.getArgs().get(0).toLowerCase();
        if (!location.hasNpc(npcName)) {
            System.out.println(npcName + " is not here.");
            return false;
        }

        Npc npc = location.getNpc(npcName);
        if (!npc.canTalkTo()) {
            System.out.println(npcName + " would not like to talk.");
            listTalkableNpcs(location);
            return false;
        }
        System.out.println();
        game.changeState(new TalkState(game, npc));
        return true;
    }

    private void listTalkableNpcs(Location location) {
        System.out.println("You can talk to: ");
        location.getTalkableNpcs().forEach(npc -> {
            System.out.print(npc.getName() + " ");
        });
        System.out.println();
    }
}
