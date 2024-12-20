package game
import spock.lang.Specification
import gui.LanternaGUI
import sound.BackgroundSoundPlayer

class GameTest extends Specification {

    def mockGUI = Mock(LanternaGUI)
    def mockGameManager = Mock(GameManager)
    def mockSoundPlayer = Mock(BackgroundSoundPlayer)

    def "getInstance should return a singleton instance"() {
        when:
            def game1 = Game.getInstance()
            def game2 = Game.getInstance()
        then:
            game1 == game2
    }

    def "start should interact with dependencies correctly"() {
        given:
            def game = Game.getInstance()
            game.setGui(mockGUI)
            game.setGameManager(mockGameManager)
            game.setBackgroundSoundPlayer(mockSoundPlayer)
            mockGameManager.step(mockGUI, _ as long) >> false
        when:
            game.start()
        then:
            1 * mockSoundPlayer.start()
            1 * mockSoundPlayer.stop()
            1 * mockGUI.close()
    }

    def "getInstance should create the game instance with proper dependencies"() {
        given:
            def game = Game.getInstance()
        when:
            def instance = Game.getInstance()
        then:
            instance != null
            instance.gui instanceof LanternaGUI
            instance.gameManager instanceof GameManager
            instance.backgroundSoundPlayer instanceof BackgroundSoundPlayer
    }
}
