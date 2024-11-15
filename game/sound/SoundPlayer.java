package game.sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * The SoundPlayer class uses an enum which defines a set of sound files, each of which has an associated
 * file path. The sounds are loaded and initialised during the startup of the game and thus can be played
 * without lazy initialisation. Sounds can be played synchronously or asynchronously.
 * @param <T>
 */
public class SoundPlayer<T extends Enum<T> & FilePathProvider> {
    HashMap<T, Clip> soundClips = new HashMap<>();

    /**
     * Loads every sound file from the path of each enum variant and associates each enum variant with
     * the respective audio Clip.
     * @param enumType The enum of sound variants.
     */
    public void loadSounds(Class<T> enumType) {
        for (T enumConstant : enumType.getEnumConstants()) {
            soundClips.put(
                    enumConstant,
                    loadSound(enumConstant.getFilePath())
            );
        }
    }

    public void playSoundOnDifferentThread(T soundVariant) {
        // todo: use a pool of threads for each sound.
        new Thread(() -> playSound(soundVariant, false)).start();
    }

    public void playSoundOnDifferentThread(T soundVariant, boolean loop) {
        // todo: use a pool of threads for each sound.
        new Thread(() -> playSound(soundVariant, loop)).start();
    }

    public void playSound(T soundVariant, boolean loop) {
        /*
        Todo: when the same clip is stopped and restarted quickly in succession, the clip doesn't always play
        as intended. Maybe creating a pool of clips is the solution.
         */
        Clip clip = soundClips.get(soundVariant);

        if (clip.isRunning())
            clip.stop();

        if (loop) clip.loop(Clip.LOOP_CONTINUOUSLY);
        else clip.loop(0);

        clip.setFramePosition(0);
        clip.start();
    }

    public void stopSound(T soundVariant) {
        Clip clip = soundClips.get(soundVariant);
        if (clip != null) clip.stop();
    }

    /**
     * Sets the volume of a sound clip.
     * @param volume A value between 0.0 and 1.0 (linear scale).
     */
    public void setVolume(T soundVariant, float volume) {
        Clip clip = soundClips.get(soundVariant);
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(linearToDecibels(volume));
    }

    public static float decibelToLinearScale(float dB) {
        return (float) Math.pow(10.0, dB / 20.0);
    }

    public static float linearToDecibels(double linear) {
        // Check for the boundary condition
        if (linear == 0) {
            return Float.NEGATIVE_INFINITY; // -Infinity dB represents silence
        } else {
            return 20.0f * (float) Math.log10(linear);
        }
    }
    private Clip loadSound(String filePath) {
        try {
            // Open an audio input stream.
            File file = new File(filePath);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
            // Get a sound clip resource.
            Clip clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioIn);
            return clip;
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            System.err.println("Couldn't load sound: `" + filePath + "`.");
            throw new RuntimeException(e);
        }
    }
}