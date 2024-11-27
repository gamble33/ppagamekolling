package game;

import game.sound.Sound;

import java.util.HashMap;
import java.util.Map;

public enum BackgroundImage {
    Win("data/images/win.png"),

    /**
     * Credit: DALL-E by OpenAI.
     */
    Tribe("data/images/bg.png"),

    /**
     * Credit: DALL-E by OpenAI.
     */
    Wilderness("data/images/wilderness.png"),

    /**
     * Credit: DALL-E by OpenAI.
     */
    Forest("data/images/forest.png"),

    /**
     * Credit: DALL-E by OpenAI.
     */
    Oasis("data/images/oasis.png"),

    /**
     * Credit: DALL-E by OpenAI.
     */
    Waterfall("data/images/waterfall.png"),

    /**
     * Credit: DALL-E by OpenAI.
     */
    Indoor("data/images/indoor.png"),

    /**
     * Credit: DALL-E by OpenAI.
     */
    Portal("data/images/portal.png"),

    /**
     * Credit: DALL-E by OpenAI.
     */
    Death("data/images/death.png");

    private final String filePath;
    private static final Map<String, BackgroundImage> imageMap = new HashMap<>();

    static {
        for (BackgroundImage image : BackgroundImage.values()) {
            imageMap.put(image.name(), image);
        }
    }

    BackgroundImage(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public static BackgroundImage fromString(String name) {
        return imageMap.get(name);
    }
}
