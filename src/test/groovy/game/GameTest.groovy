package game

import gui.GUI
import spock.lang.Specification
import sound.BackgroundSoundPlayer

class GameTest extends Specification {
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

    def "Game singleton creation"() {
        when:
            def game = Game.getInstance(mockGui, mockGameManager, mockBackgroundSoundPlayer)
        then:
            game != null
            game.getGui() == mockGui
            game.getGameManager() == mockGameManager
            game.getBackgroundSoundPlayer() == mockBackgroundSoundPlayer
    }

    def "Game singleton reuse"() {
        given:
            def firstInstance = Game.getInstance(mockGui, mockGameManager, mockBackgroundSoundPlayer)
        when:
            def secondInstance = Game.getInstance(null, null, null)
        then:
            firstInstance.is(secondInstance)
    }

    def "Game start and stop"() {
        given:
            def game = Game.getInstance(mockGui, mockGameManager, mockBackgroundSoundPlayer)
            mockGameManager.step(_, _) >>> [false]
        when:
            game.start()
        then:
            1 * mockGui.start()
            1 * mockBackgroundSoundPlayer.start()
            1 * mockGameManager.step(_, _)
            1 * mockBackgroundSoundPlayer.stop()
            1 * mockGui.close()
    }

    def "Game step frame timing"() {
        given:
            mockGameManager.step(_, _) >> false
            def game = Game.getInstance(mockGui, mockGameManager, mockBackgroundSoundPlayer)
            def startTime = System.currentTimeMillis()
        when:
            game.start()
            def elapsedTime = System.currentTimeMillis() - startTime
        then:
            def FPS = 20
            def frameTime = 1000 / FPS
            elapsedTime >= frameTime - 50 && elapsedTime <= frameTime + 50
    }

    def "Setters and getters"() {
        given:
            def game = Game.getInstance(mockGui, mockGameManager, mockBackgroundSoundPlayer)
            def newGui = Mock(GUI)
            def newBackgroundSoundPlayer = Mock(BackgroundSoundPlayer)
            def newGameManager = Mock(GameManager)
        when:
            game.setGui(newGui)
            game.setBackgroundSoundPlayer(newBackgroundSoundPlayer)
            game.setGameManager(newGameManager)
        then:
            game.getGui() == newGui
            game.getBackgroundSoundPlayer() == newBackgroundSoundPlayer
            game.getGameManager() == newGameManager
    }
}
