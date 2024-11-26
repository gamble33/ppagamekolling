package game.item;

import game.Game;

public class Item {
    private final String name;
    private final float weight;
    private final boolean canPickUp;
    private final float damage;

    public Item(String name, float weight, boolean canPickUp, float damage) {
        this.weight = weight;
        this.name = name;
        this.canPickUp = canPickUp;
        this.damage = damage;
    }

    public float getDamage() {
        return damage;
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
