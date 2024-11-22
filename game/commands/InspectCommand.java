package game.commands;

import game.Game;
import game.item.Inventory;
import game.item.InventoryItem;
import game.states.CommandState;

public class InspectCommand extends Command {
    public InspectCommand(Game game, CommandState commandState) {
        super(game, commandState);
    }

    @Override
    public boolean execute(RawCommand rawCommand) {
        String itemName = String.join(" ", rawCommand.getArgs()).toLowerCase();
        Inventory playerInventory = game.getInventory();
        Inventory locationInventory = game.getCurrentLocation().getLocationInventory();

        if (playerInventory.isEmpty() && locationInventory.isEmpty()) {
            game.getView().addText("Your inventory is empty and there's nothing around you.");
            return false;
        }

        if (!playerInventory.has(itemName) && !locationInventory.has(itemName)) {
            game.getView().addText(itemName + " is not an item you have or that is around you.");
            displayItems(playerInventory);
            return false;
        }

        InventoryItem item;
        if (playerInventory.has(itemName)) {
            item = playerInventory.getItem(itemName).get();
        } else {
            item = locationInventory.getItem(itemName).get();
        }
        item.getItem().onInspect(game);
        return true;
    }

    public void displayItems(Inventory inventory) {
        inventory.displayItems("You have: ", game.getView());
    }
}
