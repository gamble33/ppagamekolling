package game.commands;

import game.Game;
import game.Location;
import game.item.Inventory;
import game.item.InventoryItem;
import game.sound.Sound;
import game.states.CommandState;

import java.util.List;

public class DropCommand extends Command {
    String itemName;

    public DropCommand(Game game, CommandState commandState) {
        super(game, commandState);
        this.canUndo = true;
    }

    @Override
    public boolean execute(RawCommand rawCommand) {
        itemName = "";
        int quantity;
        Inventory playerInventory = game.getInventory();
        Location location = game.getCurrentLocation();

        // Checks if the last argument is a number (optional quantity argument)
        // If yes, set item name to all arguments except last argument and set quantity accordingly.
        // Otherwise, set item name to all arguments combined into one string and set quantity to 1.
        if (rawCommand.hasArgs()) {
            List<String> args = rawCommand.getArgs();
            try {
                quantity = Integer.parseInt(args.get(args.size() - 1));
                itemName = String.join(" ", args.subList(0, args.size() - 1)).toLowerCase();
            } catch (NumberFormatException exception) {
                quantity = 1;
                itemName = String.join(" ", rawCommand.getArgs()).toLowerCase();
            }
        } else {
            // If command does not have arguments.
            game.getView().addText("You must specify at least one item.");
            game.getView().addText("Type: drop <item name> [quantity]");
            displayItems(playerInventory);
            return false;
        }

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

        if (quantity > item.getQuantity()) {
            game.getView().addText("You only have " + item.getQuantity() + " " + item.getItem().getName());
            return false;
        }

        game.getInventory().removeItem(itemName, quantity);
        InventoryItem locationItem = new InventoryItem(item.getItem(), quantity);
        location.getLocationInventory().addItem(locationItem);
        game.getView().addText("You drop " + quantity + "x " + item.getItem().getName());
        game.getSoundPlayer().playSoundOnDifferentThread(Sound.DropItem);
        return true;
    }

    public void displayItems(Inventory inventory) {
        inventory.displayItems("You have: ", game.getView());
    }

    @Override
    public void undo() {
        if (!game.getCurrentLocation().getLocationInventory().has(itemName)) {
            game.getView().addText("Couldn't pick up " + itemName);
            return;
        }

        new TakeCommand(game, commandState)
                .execute(new RawCommand(
                        "take",
                        List.of(itemName)
                ));
    }
}
