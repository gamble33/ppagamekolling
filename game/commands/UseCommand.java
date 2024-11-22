package game.commands;

import game.Game;
import game.item.Inventory;
import game.item.InventoryItem;
import game.sound.Sound;
import game.states.CommandState;

public class UseCommand extends Command {

    public UseCommand(Game game, CommandState commandState) {
        super(game, commandState);
    }

    @Override
    public boolean execute(RawCommand rawCommand) {
        String itemName = String.join(" ", rawCommand.getArgs()).toLowerCase();
        Inventory playerInventory = game.getInventory();

        if (playerInventory.isEmpty()) {
            game.getView().addText("Your inventory is empty.");
            displayItems(playerInventory);
            return false;
        }

        if (!playerInventory.has(itemName)) {
            game.getView().addText(itemName + " is not an item you have.");
            displayItems(playerInventory);
            return false;
        }

        InventoryItem item = playerInventory.getItem(itemName).get();
        item.getItem().onUse(game);
        return true;
    }

    public void displayItems(Inventory inventory) {
        inventory.displayItems("You have: ", game.getView());
    }
}
