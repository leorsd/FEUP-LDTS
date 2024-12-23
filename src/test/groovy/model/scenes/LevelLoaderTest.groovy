package model.scenes

import spock.lang.Specification
import java.awt.image.BufferedImage
import java.nio.file.Files;

class LevelLoaderTest extends Specification {

    def "should resize image correctly"() {
        given:
        def width = 100
        def height = 200
        def originalImage = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB)
        def levelLoader = new LevelLoader()

        when:
        def resizedImage = levelLoader.resizeImage(originalImage, width, height)

        then:
        resizedImage.getWidth() == width
        resizedImage.getHeight() == height
    }

    def "loadLevel should correctly parse level data and create a Level object"() {
        given:

        def levelData = """320,180,src/main/resources/images/backgrounds/wallBackgroundLevel0.png,src/main/resources/images/backgrounds/backgroundLevel0.png,src/main/resources/levels/level1

15,158,11,15,28,4

15,135,11,15,28,4

Lavena,65,48,7,9,src/main/resources/images/elements/lavenakey.png
Tergon,55,48,7,9,src/main/resources/images/elements/tergonkey.png

120,44,16,16,120,150

Lavena,110,93,90,2,src/main/resources/images/backgrounds/trapP2Background.png
Tergon,110,118,90,2,src/main/resources/images/backgrounds/trapP1Background.png
Both,210,60,35,2,src/main/resources/images/backgrounds/trapBothBackground.png

0,0,320,7
0,0,7,180

282,31,17,19

1,265,72,5,46,src/main/resources/images/buttons/toggleableWall3.png

1,230,116,8,2,src/main/resources/images/buttons/button3.png
1,190,161,8,2,src/main/resources/images/buttons/button3.png
        """

        def tempFile = Files.createTempFile("testLevel", ".txt").toFile()
        tempFile.text = levelData

        and: "A LevelLoader instance"
        def levelLoader = new LevelLoader()

        when: "The level is loaded"
        Level level = levelLoader.loadLevel(tempFile.path)

        then: "The Level object is created and populated correctly"
        level != null
        level.xBoundary == 320
        level.yBoundary == 180
        level.nextLevel == "src/main/resources/levels/level1"
        level.walls.size() == 2
        level.traps.size() == 3
        level.keys.size() == 2
        level.toggleableWalls.size() == 1
        level.buttons.size() == 2
        level.monsters.size() == 1
        level.player1 != null
        level.player2 != null
        level.levelEndingDoor != null

        cleanup:
        tempFile.delete()
    }
}
