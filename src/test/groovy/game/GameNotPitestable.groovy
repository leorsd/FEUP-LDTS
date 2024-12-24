package game

import gui.GUI
import sound.BackgroundSoundPlayer
import spock.lang.Specification

class GameNotPitestable extends Specification {
    GUI mockGui
    GameManager mockGameManager
    BackgroundSoundPlayer mockBackgroundSoundPlayer

    def setup() {
        mockGui = Mock(GUI)
        mockGameManager = Mock(GameManager)
        mockBackgroundSoundPlayer = Mock(BackgroundSoundPlayer)
        def field = Game.getDeclaredField("gameInstance")
        field.accessible = true
        field.set(null, null)
    }


    def "Game singleton creation with default values"() {
        when:
        def game = Game.getInstance(null,null,null)
        then:
        game != null
        game.getGui() != null
        game.getGameManager() != null
        game.getBackgroundSoundPlayer() != null
    }
}
