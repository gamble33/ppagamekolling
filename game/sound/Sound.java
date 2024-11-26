package game.sound;

import java.util.HashMap;
import java.util.Map;

public enum Sound implements FilePathProvider {
    Attack("data/sound/attack.wav"),
    Damage("data/sound/damage.wav"),
    Teleport("data/sound/teleport.wav"),
    TakeItem("data/sound/pack.wav"),
    DropItem("data/sound/drop.wav"),
    Eat("data/sound/eat.wav"),
    MusicCamp("data/sound/camp.wav"),
    MusicHome("data/sound/Balzhan.wav"),

    /**
     * Credit: A cappella - Persian loneliness by Vrymaa
     * -- <a href="https://freesound.org/s/738780/">freesound.org</a> -- License: Attribution 4.0
     */
    MusicLoss("data/sound/loss.wav"), //

    /**
     * Credit: Au dé qui Roule
     * <a href="https://youtu.be/a9_saAUM-yk?si=VK8aXpi7q49iFYmG">YouTube Video</a>
     */
    MusicIndoorAmbience("data/sound/indoor_ambience.wav"),
    HorseTrot("data/sound/horse_trot.wav"),
    MaleSpeech1("data/sound/male_speech_1.wav"),
    MaleSpeech2("data/sound/male_speech_2.wav"),
    MaleSpeech3("data/sound/male_speech_3.wav"),
    MaleSpeech4("data/sound/male_speech_4.wav"),
    MaleSpeech5("data/sound/male_speech_5.wav");

    private final String filePath;
    private static final Map<String, Sound> soundMap = new HashMap<>();

    static {
        for (Sound sound : Sound.values()) {
            soundMap.put(sound.name(), sound);
        }
    }

    Sound(String filePath) {
        this.filePath = filePath;
    }

    public static Sound fromString(String name) {
        return soundMap.get(name);
    }

    @Override
    public String getFilePath() {
        return filePath;
    }

}
