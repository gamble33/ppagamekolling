package game.item;

public class LocationItem {
    private final Item item;
    private final int quantity;
    private final String description;

    public LocationItem(Item item, int quantity, String description) {
        this.item = item;
        this.quantity = quantity;
        this.description = description;
    }

    public Item getItem() {
        return item;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }
}
