package game.item;

import game.BackgroundImage;
import game.Game;

import java.util.Random;

public class LeverItem extends Item {
    private final Random random;

    public LeverItem(String name, float weight, boolean canPickUp, float damage) {
        super(name, weight, canPickUp, damage);
        random = new Random();
    }

    @Override
    public void onInspect(Game game) {
        freeWife(game);
    }

    private void freeWife(Game game) {
        game.getView().setBackgroundImage(BackgroundImage.Win);
        game.getView().addText("The pulchritudinous wife, Anastasia, has been freed. Good job! You gained honour and won at life!");
        game.endGame();
    }
}
