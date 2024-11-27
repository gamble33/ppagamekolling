package game.commands;

import game.Damage;
import game.Game;
import game.Npc;
import game.item.Inventory;
import game.item.Item;
import game.sound.Sound;
import game.states.CommandState;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The AttackCommand class handles the logic for attacking NPCs within the game using various items.
 * An attack can be executed using either fists as a default weapon or an item from the player's inventory.
 * The class checks if the specified NPC and item exist, calculates the damage, and then performs the attack.
 * If the NPC is killed in the attack, it is removed from the current location.
 */
public class AttackCommand extends Command {
    private final static float DAMAGE_FISTS = 9f;
    private final static String SWORD_EMOJI = "\uD83D\uDDE1";

    public AttackCommand(Game game, CommandState commandState) {
        super(game, commandState);
    }

    @Override
    public boolean execute(RawCommand rawCommand) {
        List<String> args = rawCommand.getArgs();
        if (args.size() < 2) {
            game.getView().addText("To attack write: attack <npc> <item>");
            return false;
        }

        String npcName = rawCommand.getArgs().get(0);
        if (!game.getCurrentLocation().hasNpc(npcName)) {
            game.getView().addText(npcName + " is not here.");
            return false;
        }

        Npc npc = game.getCurrentLocation().getNpc(npcName);
        String attackItem = String.join(" ", args.subList(1, args.size())).toLowerCase();
        float damage;

        if (attackItem.equalsIgnoreCase("fists")) {
            damage = DAMAGE_FISTS;
        } else {
            Inventory playerInv = game.getInventory();
            if (!playerInv.has(attackItem)) {
                game.getView().addText("You do not have " + attackItem);
                game.getView().addText("You can always attack with `fists`.");
                game.getInventory().displayItems("You have: ", game.getView());
                return false;
            }

            Item item = playerInv.getItem(attackItem).get().getItem();
            damage = item.getDamage();
        }

        boolean npcKilled = npc.attack(damage);

        game.getSoundPlayer().playSoundOnDifferentThread(Sound.Attack);

        npcReact(npc);
        game.getView().addText(SWORD_EMOJI + " Attacked " + npc.getName() + " for " + damage + " damage " + SWORD_EMOJI);
        if (npcKilled) {
            game.getView().addText("💀 " + npc.getName() + " was killed. 💀");
            game.getCurrentLocation().removeNpc(npc);
            game.getSoundPlayer().playSoundOnDifferentThread(Sound.Kill);

            if (npc.hasDrops()) {
                npc.getDrops().forEach(drop -> {
                    game.getCurrentLocation().getLocationInventory().addItem(drop);
                });
                String drops = npc
                        .getDrops()
                        .stream()
                        .map(drop -> drop.getItem().getName() + " ")
                        .collect(Collectors.joining());
                game.getView().addText(npc.getName() + " dropped: " + drops);
            }

        }
        return true;
    }

    private void npcReact(Npc npc) {
        switch (npc.getBehaviour()) {
            case Passive -> {
                // todo
                // An NPC with passive behaviour will flee from the current location.
            }
            case Neutral, Aggressive -> {
                // An aggressive and neutral NPC will attack the player and remain in the same location.
                Damage damage = npc.getBehaviour().getDamage();
                game.getView().addText(npc.getName() + " " + damage.getDescription());
                game.getView().addText("This deals 🔪💔" + damage.getAmount() + " damage.");
                game.damage(damage.getAmount());
            }
        }
    }
}
