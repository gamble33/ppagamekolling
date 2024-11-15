package game.sound;

import java.util.HashMap;
import java.util.Map;

public enum Sound implements FilePathProvider {
    MusicCamp("data/sound/camp.wav"),
    MusicHome("data/sound/Balzhan.wav"),
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
