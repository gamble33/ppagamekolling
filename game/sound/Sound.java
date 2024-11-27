package game.sound;

import java.util.HashMap;
import java.util.Map;

public enum Sound implements FilePathProvider {
    /**
     * Credit: WACFX_Low_Impact_01_ F_Bass.wav by WAProd -- https://freesound.org/s/670530/ --
     * License: Creative Commons 0
     */
    Kill("data/sound/kill.wav"),
    Attack("data/sound/attack.wav"),
    Damage("data/sound/damage.wav"),
    Teleport("data/sound/teleport.wav"),

    /**
     * Credit: Cartoon Scary Hit by tyops -- https://freesound.org/s/448204/ -- License: Attribution 4.0
     */
    ScaryHit("data/sound/scary_hit.wav"),

    /**
     * Credit: grab-item by 1bob -- https://freesound.org/s/651515/ -- License: Creative Commons 0
     */
    TakeItem("data/sound/grab_item.wav"),
    DropItem("data/sound/drop.wav"),
    Eat("data/sound/eat.wav"),
    MusicCamp("data/sound/camp.wav"),
    MusicHome("data/sound/Balzhan.wav"),

    /**
     * Credit: Atmosphere very low pitch sinusoid 01 by OneiroidState -- https://freesound.org/s/192378/ --
     * License: Creative Commons 0
     */
    MusicTeleportDevice("data/sound/teleporter.wav"),

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
