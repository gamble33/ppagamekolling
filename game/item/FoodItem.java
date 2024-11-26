package game.item;

import game.Game;
import game.sound.Sound;

public class FoodItem extends Item {
    private final float saturation;

    public FoodItem(String name, float weight, boolean canPickUp, float damage, float saturation) {
        super(name, weight, canPickUp, damage);
        this.saturation = saturation;
    }

    @Override
    public void onUse(Game game) {
        game.getInventory().removeItem(getName(), 1);
        game.getView().addText("You eat " + getName() + " and feel better (+" + saturation + "🍖).");
        game.increaseSaturation(saturation);
        game.getSoundPlayer().playSoundOnDifferentThread(Sound.Eat);
    }
}
