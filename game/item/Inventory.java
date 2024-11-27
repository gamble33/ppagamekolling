package game.item;

import game.ui.GameView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Inventory {
    private final List<InventoryItem> items;

    public Inventory() {
        this.items = new ArrayList<>();
    }

    public boolean has(String itemName) {
        return items
                .stream()
                .anyMatch(i -> i.getItem().getName().equalsIgnoreCase(itemName));
    }

    public Optional<InventoryItem> getItem(String itemName) {
        return items.stream().filter(i -> i.getItem().getName().equalsIgnoreCase(itemName)).findFirst();
    }

    public void displayItems(String message, GameView view) {
        if (isEmpty()) {
            view.addText("You have nothing.");
            return;
        }
        StringBuilder itemString = new StringBuilder();
        for (InventoryItem item : items) {
            itemString.append(item.getItem().getName());
            if (item.getQuantity() > 1) {
                itemString.append(" ").append(item.getQuantity()).append("x");
            }
            itemString.append(", ");
        }
        itemString = new StringBuilder(itemString.substring(0, itemString.length() - 2));
        view.addText(message + itemString);
    }

    public void removeItem(String itemName, int quantity) {
        InventoryItem inventoryItem = items
                .stream()
                .filter(i -> i.getItem().getName().equalsIgnoreCase(itemName))
                .findFirst()
                .get();
        if (inventoryItem.getQuantity() == quantity) {
            items.remove(inventoryItem);
        } else {
            inventoryItem.reduceQuantity(quantity);
        }
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void addItem(InventoryItem item){
        items
                .stream()
                .filter(i -> i.getItem().getName().equalsIgnoreCase(item.getItem().getName()))
                .findFirst()
                .ifPresentOrElse(
                        inventoryItem -> inventoryItem.increaseQuantity(item.getQuantity()),
                        () -> items.add(item)
                );
    }

    public List<InventoryItem> getItemList() {
        return items;
    }
}
