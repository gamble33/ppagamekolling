package game.item;

import game.Game;

public class Item {
    private final String name;
    private final float weight;
    private final boolean canPickUp;

    public Item(String name, float weight, boolean canPickUp) {
        this.weight = weight;
        this.name = name;
        this.canPickUp = canPickUp;
    }

    public boolean canPickUp() {
        return canPickUp;
    }

    public String getName() {
        return name;
    }

    public float getWeight() {
        return weight;
    }

    public void onUse(Game game) {
        game.getView().addText("You try to use " + getName() + "... Nothing happens.");
    }

    public void onInspect(Game game) {
        game.getView().addText("Nothing special. Just a normal " + getName() + ".");
    }
}
