package sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;

public class SoundLoader {
    public Clip loadSound(AudioInputStream audioInput, Clip musicClip) throws IOException {
        try {
            musicClip.open(audioInput);
            return musicClip;
        } catch (LineUnavailableException | IOException e) {
            throw new IOException("Unable to load sound file!");
        }
    }
}
