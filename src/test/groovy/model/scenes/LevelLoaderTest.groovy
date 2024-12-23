package model.scenes

import spock.lang.Specification
import java.nio.file.Files
import java.awt.image.BufferedImage

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
1,190,161,8,2,src/main/resources/images/buttons/button3.png"""

        def tempFile = Files.createTempFile("testLevel", ".txt").toFile()
        tempFile.text = levelData

        and:
        def levelLoader = new LevelLoader()

        when:
        Level level = levelLoader.loadLevel(tempFile.path)

        then:
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

    def "test readLevelConfig throws exceptions for invalid inputs"() {
        given:
        BufferedReader reader = Mock(BufferedReader)
        def loader = new LevelLoader()

        when:
        reader.readLine() >> {throw new IOException("Could not read line for level config") }
        loader.readLevelConfig(reader)
        then:
        def e = thrown(IOException)
        assert e.message == "Could not read line for level config"

        when:
        reader = Mock(BufferedReader)
        reader.readLine() >> null
        loader.readLevelConfig(reader)
        then:
        def e1 = thrown(IOException)
        assert e1.message == "Null line caused error while trying to read level configuration"

        when:
        reader = Mock(BufferedReader)
        reader.readLine() >> "invalid_number_of_arguments"
        loader.readLevelConfig(reader)
        then:
        def e2 = thrown(IOException)
        assert e2.message == "Level config line needs to be like: xBoundary,yBoundary,regularWallsImagePath,backgroundImagePath,nextLevelPath"

        when:
        reader = Mock(BufferedReader)
        reader.readLine() >> "invalidValue,100,src/main/resources/images/backgrounds/wallBackgroundLevel0.png,src/main/resources/images/backgrounds/backgroundLevel0.png,nextLevel"
        loader.readLevelConfig(reader)
        then:
        def e3 = thrown(IOException)
        assert e3.message == "Invalid xBoundary number when loading level"

        when:
        reader = Mock(BufferedReader)
        reader.readLine() >> "100,invalidValue,src/main/resources/images/backgrounds/wallBackgroundLevel0.png,src/main/resources/images/backgrounds/backgroundLevel0.png,nextLevel"
        loader.readLevelConfig(reader)
        then:
        def e4 = thrown(IOException)
        assert e4.message == "Invalid yBoundary number when loading level"

        when:
        reader = Mock(BufferedReader)
        reader.readLine() >> "100,100,invalidPath.png,src/main/resources/images/backgrounds/backgroundLevel0.png,nextLevel"
        loader.readLevelConfig(reader)
        then:
        def e5 = thrown(IOException)
        assert e5.message == "Could not read wall background image when loading level"

        when:
        reader = Mock(BufferedReader)
        reader.readLine() >> "100,100,src/main/resources/images/backgrounds/wallBackgroundLevel0.png,invalidBackground.png,nextLevel"
        loader.readLevelConfig(reader)
        then:
        def e6 = thrown(IOException)
        assert e6.message == "Could not read background image when loading level"
    }

    def "test readPlayer1 method throws exceptions for invalid inputs"() {
        given:
        BufferedReader reader = Mock(BufferedReader)
        def loader = new LevelLoader()

        when:
        reader.readLine() >> {throw new IOException("Could not read line for player 1 when loading level") }
        loader.readPlayer1(reader)
        then:
        def e = thrown(IOException)
        assert e.message == "Could not read line for player 1 when loading level"

        when:
        reader = Mock(BufferedReader)
        reader.readLine() >> null
        loader.readPlayer1(reader)
        then:
        def e1 = thrown(IOException)
        assert e1.message == "Null line caused error when trying to read player1's information"

        when:
        reader = Mock(BufferedReader)
        reader.readLine() >> "invalid_number_of_arguments"
        loader.readPlayer1(reader)
        then:
        def e2 = thrown(IOException)
        assert e2.message == "Player 1 specification needs to be like: x,y,sizeX,sizeY,maxJumpHeight,speed"

        when:
        reader = Mock(BufferedReader)
        reader.readLine() >> "invalid,20,30,40,50,60"
        loader.readPlayer1(reader)
        then:
        def e3 = thrown(IOException)
        assert e3.message == "Invalid x when trying to read player1's information when loading level"

        when:
        reader = Mock(BufferedReader)
        reader.readLine() >> "10,invalid,30,40,50,60"
        loader.readPlayer1(reader)
        then:
        def e4 = thrown(IOException)
        assert e4.message == "Invalid y when trying to read player1's information when loading level"

        when:
        reader = Mock(BufferedReader)
        reader.readLine() >> "10,20,invalid,40,50,60"
        loader.readPlayer1(reader)
        then:
        def e5 = thrown(IOException)
        assert e5.message == "Invalid sizeX when trying to read player1's information when loading level"

        when:
        reader = Mock(BufferedReader)
        reader.readLine() >> "10,20,30,invalid,50,60"
        loader.readPlayer1(reader)
        then:
        def e6 = thrown(IOException)
        assert e6.message == "Invalid sizeY when trying to read player1's information when loading level"

        when:
        reader = Mock(BufferedReader)
        reader.readLine() >> "10,20,30,40,invalid,60"
        loader.readPlayer1(reader)
        then:
        def e7 = thrown(IOException)
        assert e7.message == "Invalid maxJumpHeight for player 1 when loading level"

        when:
        reader = Mock(BufferedReader)
        reader.readLine() >> "10,20,30,40,50,invalid"
        loader.readPlayer1(reader)
        then:
        def e8 = thrown(IOException)
        assert e8.message == "Invalid speed for player 1 when loading level"
    }

    def "test readPlayer2 method throws exceptions for invalid inputs"() {
        given:
        BufferedReader reader = Mock(BufferedReader)
        def loader = new LevelLoader()

        when:
        reader.readLine() >> {throw new IOException("Could not read line for player 2 when loading level") }
        loader.readPlayer2(reader)
        then:
        def e = thrown(IOException)
        assert e.message == "Could not read line for player 2 when loading level"

        when:
        reader = Mock(BufferedReader)
        reader.readLine() >> null
        loader.readPlayer2(reader)
        then:
        def e1 = thrown(IOException)
        assert e1.message == "Null line caused error when trying to read player2's information"

        when:
        reader = Mock(BufferedReader)
        reader.readLine() >> "invalid_number_of_arguments"
        loader.readPlayer2(reader)
        then:
        def e2 = thrown(IOException)
        assert e2.message == "Player 2 specification needs to be like: x,y,sizeX,sizeY,maxJumpHeight,speed"

        when:
        reader = Mock(BufferedReader)
        reader.readLine() >> "invalid,20,30,40,50,60"
        loader.readPlayer2(reader)
        then:
        def e3 = thrown(IOException)
        assert e3.message == "Invalid x when trying to read player2's information when loading level"

        when:
        reader = Mock(BufferedReader)
        reader.readLine() >> "10,invalid,30,40,50,60"
        loader.readPlayer2(reader)
        then:
        def e4 = thrown(IOException)
        assert e4.message == "Invalid y when trying to read player2's information when loading level"

        when:
        reader = Mock(BufferedReader)
        reader.readLine() >> "10,20,invalid,40,50,60"
        loader.readPlayer2(reader)
        then:
        def e5 = thrown(IOException)
        assert e5.message == "Invalid sizeX when trying to read player2's information when loading level"

        when:
        reader = Mock(BufferedReader)
        reader.readLine() >> "10,20,30,invalid,50,60"
        loader.readPlayer2(reader)
        then:
        def e6 = thrown(IOException)
        assert e6.message == "Invalid sizeY when trying to read player2's information when loading level"

        when:
        reader = Mock(BufferedReader)
        reader.readLine() >> "10,20,30,40,invalid,60"
        loader.readPlayer2(reader)
        then:
        def e7 = thrown(IOException)
        assert e7.message == "Invalid maxJumpHeight for player 2 when loading level"

        when:
        reader = Mock(BufferedReader)
        reader.readLine() >> "10,20,30,40,50,invalid"
        loader.readPlayer2(reader)
        then:
        def e8 = thrown(IOException)
        assert e8.message == "Invalid speed for player 2 when loading level"
    }

    def "test readKey method throws exceptions for invalid inputs"() {
        given:
        def loader = new LevelLoader()

        when:
        loader.readKey("invalid_number_of_arguments")
        then:
        def e1 = thrown(IOException)
        assert e1.message == "Key specification needs to be like: target,x,y,sizeX,sizeY,imagePath"

        when:
        loader.readKey("target,invalid,20,30,40,src/main/resources/images/elements/lavenakey.png")
        then:
        def e2 = thrown(IOException)
        assert e2.message == "Invalid x when trying to read key when loading level"

        when:
        loader.readKey("target,10,invalid,30,40,src/main/resources/images/elements/lavenakey.png")
        then:
        def e3 = thrown(IOException)
        assert e3.message == "Invalid y when trying to read key when loading level"

        when:
        loader.readKey("target,10,20,30,40,invalid/path/image.png")
        then:
        def e4 = thrown(IOException)
        assert e4.message == "Could not read image for key when loading level"

        when:
        loader.readKey("target,10,20,invalid,40,src/main/resources/images/elements/lavenakey.png")
        then:
        def e5 = thrown(IOException)
        assert e5.message == "Invalid sizeX when trying to read key when loading level"

        when:
        loader.readKey("target,10,20,30,invalid,src/main/resources/images/elements/lavenakey.png")
        then:
        def e6 = thrown(IOException)
        assert e6.message == "Invalid sizeY when trying to read key when loading level"
    }

    def "test readMonster method throws exceptions for invalid inputs"() {
        given:
        def loader = new LevelLoader()

        when:
        loader.readMonster("invalid_number_of_arguments")
        then:
        def e1 = thrown(IOException)
        assert e1.message == "Monster specification needs to be like: x,y,sizeX,sizeY,minX,maxX"

        when:
        loader.readMonster("invalid,200,50,50,10,20")
        then:
        def e2 = thrown(IOException)
        assert e2.message == "Invalid x when trying to read monster when loading level"

        when:
        loader.readMonster("100,invalid,50,50,10,20")
        then:
        def e3 = thrown(IOException)
        assert e3.message == "Invalid y when trying to read monster when loading level"

        when:
        loader.readMonster("100,200,invalid,50,10,20")
        then:
        def e4 = thrown(IOException)
        assert e4.message == "Invalid sizeX when trying to read key when loading level"

        when:
        loader.readMonster("100,200,50,invalid,10,20")
        then:
        def e5 = thrown(IOException)
        assert e5.message == "Invalid sizeY when trying to read key when loading level"

        when:
        loader.readMonster("100,200,50,50,invalid,20")
        then:
        def e6 = thrown(IOException)
        assert e6.message == "Invalid minX when trying to read key when loading level"

        when:
        loader.readMonster("100,200,50,50,10,invalid")
        then:
        def e7 = thrown(IOException)
        assert e7.message == "Invalid maxX when trying to read key when loading level"
    }

    def "test readTrap method throws exceptions for invalid inputs"() {
        given:
        def loader = new LevelLoader()

        when:
        loader.readTrap("invalid_number_of_arguments")
        then:
        def e1 = thrown(IOException)
        assert e1.message == "Trap specification needs to be like: target,x,y,sizeX,sizeY,imagePath"

        when:
        loader.readTrap("target,invalid,200,50,50,src/main/resources/images/backgrounds/trapP1Background.png")
        then:
        def e2 = thrown(IOException)
        assert e2.message == "Invalid x when trying to read trap when loading level"

        when:
        loader.readTrap("target,100,invalid,50,50,src/main/resources/images/backgrounds/trapP1Background.png")
        then:
        def e3 = thrown(IOException)
        assert e3.message == "Invalid y when trying to read trap when loading level"

        when:
        loader.readTrap("target,100,200,invalid,50,src/main/resources/images/backgrounds/trapP1Background.png")
        then:
        def e4 = thrown(IOException)
        assert e4.message == "Invalid sizeX when trying to read trap when loading level"

        when:
        loader.readTrap("target,100,200,50,invalid,src/main/resources/images/backgrounds/trapP1Background.png")
        then:
        def e5 = thrown(IOException)
        assert e5.message == "Invalid sizeY when trying to read trap when loading level"

        when:
        loader.readTrap("target,100,200,50,50,invalid/path/to/image.png")
        then:
        def e6 = thrown(IOException)
        assert e6.message == "Could not read image for trap when loading level"
    }

    def "test readWall method throws exceptions for invalid inputs"() {
        given:
        def loader = new LevelLoader()

        when:
        loader.readWall("invalid_number_of_arguments")
        then:
        def e1 = thrown(IOException)
        assert e1.message == "Wall specification needs to be like: x,y,sizeX,sizeY"

        when:
        loader.readWall("invalid,200,50,50")
        then:
        def e2 = thrown(IOException)
        assert e2.message == "Invalid x when trying to read wall when loading level"

        when:
        loader.readWall("100,invalid,50,50")
        then:
        def e3 = thrown(IOException)
        assert e3.message == "Invalid y when trying to read wall when loading level"

        when:
        loader.readWall("100,200,invalid,50")
        then:
        def e4 = thrown(IOException)
        assert e4.message == "Invalid width when trying to read wall when loading level"

        when:
        loader.readWall("100,200,50,invalid")
        then:
        def e5 = thrown(IOException)
        assert e5.message == "Invalid height when trying to read wall when loading level"
    }

    def "test readToggleableWall method throws exceptions for invalid inputs"() {
        given:
        def loader = new LevelLoader()

        when:
        loader.readToggleableWall("invalid_number_of_arguments")
        then:
        def e1 = thrown(IOException)
        assert e1.message == "ToggleableWall specification needs to be like: id,x,y,sizeX,sizeY,imagePath"

        when:
        loader.readToggleableWall("invalid,100,200,50,50,src/main/resources/images/buttons/toggleableWall1.png")
        then:
        def e2 = thrown(IOException)
        assert e2.message == "Invalid id when trying to read toggleable wall when loading level"

        when:
        loader.readToggleableWall("1,invalid,200,50,50,src/main/resources/images/buttons/toggleableWall1.png")
        then:
        def e3 = thrown(IOException)
        assert e3.message == "Invalid x when trying to read toggleable wall when loading level"

        when:
        loader.readToggleableWall("1,100,invalid,50,50,src/main/resources/images/buttons/toggleableWall1.png")
        then:
        def e4 = thrown(IOException)
        assert e4.message == "Invalid y when trying to read toggleable wall when loading level"

        when:
        loader.readToggleableWall("1,100,200,invalid,50,src/main/resources/images/buttons/toggleableWall1.png")
        then:
        def e5 = thrown(IOException)
        assert e5.message == "Invalid width when trying to read toggleable wall when loading level"

        when:
        loader.readToggleableWall("1,100,200,50,invalid,src/main/resources/images/buttons/toggleableWall1.png")
        then:
        def e6 = thrown(IOException)
        assert e6.message == "Invalid height when trying to read toggleable wall when loading level"

        when:
        loader.readToggleableWall("1,100,200,50,50,invalid/path/to/image.png")
        then:
        def e7 = thrown(IOException)
        assert e7.message == "Could not read image for toggleable wall when loading level"
    }

    def "test readButtons method throws exceptions for invalid inputs"() {
        given:
        def loader = new LevelLoader()

        when:
        loader.readButtons("invalid_number_of_arguments")
        then:
        def e1 = thrown(IOException)
        assert e1.message == "Button specification needs to be like: idToggleableWall,x,y,sizeX,sizeY,imagePath"

        when:
        loader.readButtons("invalid,100,200,50,50,src/main/resources/images/buttons/button1.png")
        then:
        def e2 = thrown(IOException)
        assert e2.message == "Invalid id when trying to read button when loading level"

        when:
        loader.readButtons("1,invalid,200,50,50,src/main/resources/images/buttons/button1.png")
        then:
        def e3 = thrown(IOException)
        assert e3.message == "Invalid x when trying to read button when loading level"

        when:
        loader.readButtons("1,100,invalid,50,50,src/main/resources/images/buttons/button1.png")
        then:
        def e4 = thrown(IOException)
        assert e4.message == "Invalid y when trying to read button when loading level"

        when:
        loader.readButtons("1,100,200,invalid,50,src/main/resources/images/buttons/button1.png")
        then:
        def e5 = thrown(IOException)
        assert e5.message == "Invalid width when trying to read button when loading level"

        when:
        loader.readButtons("1,100,200,50,invalid,src/main/resources/images/buttons/button1.png")
        then:
        def e6 = thrown(IOException)
        assert e6.message == "Invalid height when trying to read button when loading level"

        when:
        loader.readButtons("1,100,200,50,50,invalid/path/to/image.png")
        then:
        def e7 = thrown(IOException)
        assert e7.message == "Could not read image for button when loading level"
    }

    def "test readLevelTransitionWall method throws exceptions for invalid inputs"() {
        given:
        def loader = new LevelLoader()

        when:
        loader.readLevelTransitionWall("invalid_number_of_arguments")
        then:
        def e1 = thrown(IOException)
        assert e1.message == "Level Transition Door specification needs to be like: x,y,sizeX,sizeY"

        when:
        loader.readLevelTransitionWall("invalid,200,50,50")
        then:
        def e2 = thrown(IOException)
        assert e2.message == "Invalid x when trying to read levelTransitionWall when loading level"

        when:
        loader.readLevelTransitionWall("100,invalid,50,50")
        then:
        def e3 = thrown(IOException)
        assert e3.message == "Invalid y when trying to read levelTransitionWall when loading level"

        when:
        loader.readLevelTransitionWall("100,200,invalid,50")
        then:
        def e4 = thrown(IOException)
        assert e4.message == "Invalid width when trying to read levelTransitionWall when loading level"

        when:
        loader.readLevelTransitionWall("100,200,50,invalid")
        then:
        def e5 = thrown(IOException)
        assert e5.message == "Invalid height when trying to read levelTransitionWall when loading level"
    }

    def "loadLevel exception with file that not exists"(){
        given:
        def loader = new LevelLoader()

        when:
        loader.loadLevel("invalidPath")
        then:
        def e = thrown(IOException)
        assert e.message == "Error in LevelLoader when trying to open file: invalidPath"
    }
}
