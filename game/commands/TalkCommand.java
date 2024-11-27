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
            game.getView().addText("talk command requires 1 argument (the name of whom you would like to talk with).");
            listTalkableNpcs(location);
            return false;
        }

        String npcName = rawCommand.getArgs().get(0).toLowerCase();
        if (!location.hasNpc(npcName)) {
            game.getView().addText(npcName + " is not here.");
            return false;
        }

        Npc npc = location.getNpc(npcName);
        if (!npc.canTalkTo()) {
            game.getView().addText(npcName + " would not like to talk.");
            listTalkableNpcs(location);
            return false;
        }
        game.getView().addText("\n");
        game.changeState(new TalkState(game, npc));
        npc.setRequiredDialogue(false);
        return true;
    }

    private void listTalkableNpcs(Location location) {
        StringBuilder sb = new StringBuilder();
        sb.append("You can talk to: ");
        location.getTalkableNpcs().forEach(npc -> {
            sb.append(npc.getName() + " ");
        });
        sb.append("\n");
        game.getView().addText(sb.toString());
    }
}
