package game.commands;

import game.Game;
import game.Location;
import game.item.Inventory;
import game.item.InventoryItem;
import game.item.Item;
import game.sound.Sound;
import game.states.CommandState;

import java.util.List;

public class TakeCommand extends Command {
    private InventoryItem item;

    public TakeCommand(Game game, CommandState commandState) {
        super(game, commandState);
        this.canUndo = true;
    }

    @Override
    public boolean execute(RawCommand rawCommand) {
        String itemName = String.join(" ", rawCommand.getArgs()).toLowerCase();
        Location location = game.getCurrentLocation();
        Inventory locationInventory = location.getLocationInventory();

        if (locationInventory.isEmpty()) {
            game.getView().addText("There are no items around you.");
            locationInventory.displayItems("These items lay around you: ", game.getView());
            return false;
        }

        if (!rawCommand.hasArgs()) {
            game.getView().addText("You must specify at least one item.");
            game.getView().addText("Type: take <item name>");
            locationInventory.displayItems("These items lay around you: ", game.getView());
            return false;
        }

        if (!locationInventory.has(itemName)) {
            game.getView().addText(itemName + " is not an item near you.");
            locationInventory.displayItems("These items lay around you: ", game.getView());
            return false;
        }

        item = locationInventory.getItem(itemName).get();

        Item actualItem = item.getItem();
        if (!actualItem.canPickUp()) {
            game.getView().addText(actualItem.getName() + " cannot be picked up.");
            return false;
        }

        locationInventory.removeItem(itemName, item.getQuantity());
        InventoryItem inventoryItem = new InventoryItem(item.getItem(), item.getQuantity());
        game.getInventory().addItem(inventoryItem);
        game.getView().addText("You pick up " + item.getQuantity() + "x " + item.getItem().getName());
        game.getSoundPlayer().playSoundOnDifferentThread(Sound.TakeItem);
        return true;
    }

    @Override
    public void undo() {
        if (!game.getInventory().has(item.getItem().getName())) {
            game.getView().addText("Couldn't drop " + item.getItem().getName());
            return;
        }
        new DropCommand(game, commandState)
                .execute(new RawCommand(
                        "drop",
                        List.of(item.getItem().getName(), String.valueOf(item.getQuantity()))
                ));
    }
}
