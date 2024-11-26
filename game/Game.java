package game;

import game.item.Inventory;
import game.sound.Sound;
import game.sound.SoundPlayer;
import game.states.GameState;
import game.states.IntroState;
import game.ui.GameView;

/**
 *  This class is the main class of the "World of Dudi" application.
 *  "World of Dudi" is a very simple, text based adventure game.  Users
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * <p>
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * <p>
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling, David J. Barnes, George Novachuk
 * @version 2024.11.28
 */

public class Game 
{
    private static final float MAX_HEALTH = 100f;
    private static final float MAX_SATURATION = 100f;
    private static final float HUNGER_DUE_TO_MOVING = 5f;

    private final GameView view;
    private final SoundPlayer<Sound> soundPlayer;

    private GameState gameState;
    private Location currentLocation;
    private Sound currentMusic;
    private final Inventory inventory;
    private float saturation;
    private float health;

    /**
     * Create the game and initialise its starting location.
     */
    public Game(Location startingLocation)
    {
        this.view = new GameView();
        view.show();

        soundPlayer = new SoundPlayer<>();
        soundPlayer.loadSounds(Sound.class);

        this.inventory = new Inventory();
        this.saturation = MAX_SATURATION;
        this.health = MAX_HEALTH;
        moveTo(startingLocation, false);
        this.gameState = new IntroState(this);
    }

    public void play()
    {            
        gameState.enter();
    }

    public void changeState(GameState newState) {
        gameState.exit();
        gameState = newState;
        gameState.enter();
    }

    public void requestQuit() {
        view.close();
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void moveTo(Location newLocation) {
        moveTo(newLocation, true);
    }

    public void increaseSaturation(float amount) {
        saturation += amount;
        if (saturation > MAX_SATURATION) {
            float healAmount = saturation - MAX_SATURATION;
            saturation = MAX_SATURATION;
            heal(healAmount);

        }
    }

    public void decreaseSaturation(float amount) {
        saturation -= amount;
        if (saturation < 0f) saturation = 0f;
    }

    public float getHealth() {
        return health;
    }

    public float getSaturation() {
        return saturation;
    }

    public void heal(float amount) {
        health += amount;
        if (health > MAX_HEALTH) {
            health = MAX_HEALTH;
        }
    }

    public void damage(float amount) {
        soundPlayer.playSoundOnDifferentThread(Sound.Damage);
        health -= amount;
        if (health < 0f) {
            health = 0f;
            die();
        }
    }

    public void moveTo(Location newLocation, boolean playSound) {
        this.currentLocation = newLocation;
        view.setTitle(currentLocation.getTitle());

        // Handle changing background music and image.
        soundPlayer.stopSound(currentMusic);
        if (currentLocation.hasMusic()) {
            switchMusic(Sound.fromString(currentLocation.getMusic()));
        }
        if (currentLocation.hasImage()) {
            view.setBackgroundImage(BackgroundImage.fromString(currentLocation.getImage()));
        }
        if (playSound) soundPlayer.playSoundOnDifferentThread(Sound.HorseTrot);

        decreaseSaturation(HUNGER_DUE_TO_MOVING);
    }

    public GameView getView() {
        return view;
    }

    public SoundPlayer<Sound> getSoundPlayer() {
        return soundPlayer;
    }

    public void endGame() {
        view.end();

        // Wait 5 seconds on new thread, then quit application.
        new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println("Couldn't sleep thread.");
            }
            requestQuit();
        }).start();



    }

    private void switchMusic(Sound newMusic) {
        soundPlayer.stopSound(currentMusic);
        currentMusic = newMusic;
        soundPlayer.playSoundOnDifferentThread(newMusic, true);
    }

    private void die() {
        view.addText("☠☠ You died. Game over. ☠☠");
        view.setBackgroundImage(BackgroundImage.Death);
        switchMusic(Sound.MusicLoss);
        endGame();
    }
}
