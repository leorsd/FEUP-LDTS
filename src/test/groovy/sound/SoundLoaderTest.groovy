package sound

import spock.lang.Specification
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.Clip

class SoundLoaderTest extends Specification{
    def "test loadSound successfully loads a sound"() {
        given:
            AudioInputStream mockAudioInput = Mock()
            Clip mockClip = Mock()
            def soundLoader = new SoundLoader()
        when:
            def resultClip = soundLoader.loadSound(mockAudioInput, mockClip)
        then:
            1 * mockClip.open(mockAudioInput)
            resultClip == mockClip
    }

    def "test loadSound throws exception when opening clip fails"() {
        given:
            AudioInputStream mockAudioInput = Mock()
            Clip mockClip = Mock()
            def soundLoader = new SoundLoader()
        and:
            mockClip.open(mockAudioInput) >> { throw new Exception("Clip failed to open") }
        when:
            soundLoader.loadSound(mockAudioInput, mockClip)
        then:
            def e = thrown(Exception)
            e.message == "Unable to load sound file!"
    }
}
