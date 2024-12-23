package sound

import javax.sound.sampled.Clip
import spock.lang.Specification

class BackgroundSoundPlayerTest extends Specification {

    def "test start method starts and loops sound"() {
        given:
            Clip mockClip = Mock()
            def player = new BackgroundSoundPlayer(mockClip)
        when:
            player.start()
        then:
            1 * mockClip.setMicrosecondPosition(0)
            1 * mockClip.start()
            1 * mockClip.loop(Clip.LOOP_CONTINUOUSLY)
    }

    def "test stop method stops sound"() {
        given:
            Clip mockClip = Mock()
            def player = new BackgroundSoundPlayer(mockClip)
        when:
            player.stop()
        then:
            1 * mockClip.stop()
    }

    def "test setSound method updates the Clip instance"() {
        given:
            Clip initialClip = Mock()
            Clip newClip = Mock()
            def player = new BackgroundSoundPlayer(initialClip)
        when:
            player.setSound(newClip)
        then:
            player.getSound() == newClip
    }

    def "test getSound method returns the current Clip"() {
        given:
            Clip mockClip = Mock()
            def player = new BackgroundSoundPlayer(mockClip)
        expect:
            player.getSound() == mockClip
    }
}
