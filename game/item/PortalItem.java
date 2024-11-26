package game.item;

import game.Game;
import game.Location;
import game.factories.LocationFactory;
import game.sound.Sound;

import java.util.Random;

public class PortalItem extends Item {
    private final Random random;

    public PortalItem(String name, float weight, boolean canPickUp, float damage) {
        super(name, weight, canPickUp, damage);
        random = new Random();
    }

    @Override
    public void onInspect(Game game) {
        game.getView().addText("AHH!!! YOU ARE BEING TELEPORTED...");
        teleportPlayer(game);
    }

    private void teleportPlayer(Game game) {
        // todo
        Location[] locations = LocationFactory.rooms.values().toArray(new Location[0]);
        int randomIndex = random.nextInt(locations.length);
        Location location = locations[randomIndex];
        game.moveTo(location, false);
        game.getSoundPlayer().playSoundOnDifferentThread(Sound.Teleport);
    }
}
