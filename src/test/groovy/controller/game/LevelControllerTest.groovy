package controller.game


import game.GameManager
import model.Position
import model.elements.dynamicelements.Door
import model.elements.dynamicelements.Player
import model.elements.dynamicelements.Monster
import model.elements.staticelements.Button
import model.elements.staticelements.Key
import model.elements.staticelements.ToggleableWall
import model.elements.staticelements.Trap
import model.scenes.Level
import model.scenes.LevelLoader
import model.scenes.Menu
import spock.lang.Specification
import gui.GUI

class LevelControllerTest extends Specification {
    def level
    def player1
    def player2
    def monster
    def button
    def player1Controller
    def player2Controller
    def monsterController
    def toggleableWall
    def key
    def trap
    def door
    def gameManager
    def controller

    def defaultSetup() {
        level = Mock(Level)
        player1 = Mock(Player)
        player2 = Mock(Player)
        monster = Mock(Monster)
        button = Mock(Button)
        player1Controller = Mock(Player1Controller)
        player2Controller = Mock(Player2Controller)
        monsterController = Mock(MonsterController)
        toggleableWall = Mock(ToggleableWall)
        key = Mock(Key)
        trap = Mock(Trap)
        door = Mock(Door)
        gameManager = Mock(GameManager)
        level.getPlayer1() >> player1
        level.getPlayer2() >> player2
        level.getMonsters() >> [monster]
        level.getTraps() >> [trap]
        level.getButtons() >> [button]
        level.getToggleableWalls() >> [toggleableWall]
        level.getLevelEndingDoor() >> door

        controller = new LevelController(level, player1Controller, player2Controller, monsterController)
    }

    def defaultCleanup() {
        level = null
        player1 = null
        player2 = null
        monster = null
        button = null
        player1Controller = null
        player2Controller = null
        monsterController = null
        toggleableWall = null
        key = null
        trap = null
        door = null
        gameManager = null
        controller = null
    }

    def "checkPlayerDead should return true when player1 collides with a monster, trap, or active wall"() {
        given:
            defaultSetup()
            monster.getPosition() >> new Position(10, 20)
            player1.hasCollided(monster.getPosition(), monster.getSizeX(), monster.getSizeY()) >> monsterCollision
            trap.getPosition() >> new Position(30, 40)
            player1.hasCollided(trap.getPosition(), trap.getSizeX(), trap.getSizeY()) >> trapCollision
            trap.getTarget() >> trapTarget
            player1.getName() >> "Player1"
            player1.hasCollided(toggleableWall.getPosition(), toggleableWall.getSizeX(), toggleableWall.getSizeY()) >> wallCollision
            toggleableWall.isActive() >> wallActive

        when:
            def result = controller.checkPlayerDead(player1)

        then:
            result == expectedResult

        cleanup:
            defaultCleanup()

        where:
            monsterCollision | trapCollision | trapTarget | wallCollision | wallActive | expectedResult
            true             | false         | "Player1"  | false         | false      | true
            true             | true          | "Player1"  | false         | false      | true
            false            | true          | "Both"     | false         | false      | true
            false            | false         | "Player1"  | true          | true       | true
            false            | false         | "Player1"  | false         | false      | false
            false            | true          | "Player2"  | false         | false      | false
    }

    def "checkButtonsClicked should deactivate the wall when only player1 collides with the button"() {
        given:
            defaultSetup()
            player1.getPosition() >> new Position(8,9)
            button.hasCollided(player1.getPosition(), player1.getSizeX(), player1.getSizeY()) >> true
            player2.getPosition() >> new Position(6,9)
            button.hasCollided(player2.getPosition(), player2.getSizeX(), player2.getSizeY()) >> false
            button.getToggleableWall() >> toggleableWall

        when:
            controller.checkButtonsClicked()

        then:
            1 * button.setPressed(true)
            1 * toggleableWall.setActive(false)

        cleanup:
            defaultCleanup()
    }

    def "checkButtonsClicked should deactivate the wall when only player2 collides with the button"() {
        given:
            defaultSetup()
            player1.getPosition() >> new Position(8,9)
            button.hasCollided(player1.getPosition(), player1.getSizeX(), player1.getSizeY()) >> false
            player2.getPosition() >> new Position(6,9)
            button.hasCollided(player2.getPosition(), player2.getSizeX(), player2.getSizeY()) >> true
            button.getToggleableWall() >> toggleableWall

        when:
            controller.checkButtonsClicked()

        then:
            1 * button.setPressed(true)
            1 * toggleableWall.setActive(false)

        cleanup:
            defaultCleanup()
    }

    def "checkButtonsClicked should deactivate the wall when both players collide with the button"() {
        given:
            defaultSetup()
            player1.getPosition() >> new Position(8,9)
            button.hasCollided(player1.getPosition(), player1.getSizeX(), player1.getSizeY()) >> true
            player2.getPosition() >> new Position(6,9)
            button.hasCollided(player2.getPosition(), player2.getSizeX(), player2.getSizeY()) >> true
            button.getToggleableWall() >> toggleableWall

        when:
            controller.checkButtonsClicked()

        then:
            1 * button.setPressed(true)
            1 * toggleableWall.setActive(false)

        cleanup:
            defaultCleanup()
    }

    def "checkButtonsClicked should leave the wall active when none of the players collide with the button"() {
        given:
            defaultSetup()
            player1.getPosition() >> new Position(8,9)
            button.hasCollided(player1.getPosition(), player1.getSizeX(), player1.getSizeY()) >> false
            player2.getPosition() >> new Position(6,9)
            button.hasCollided(player2.getPosition(), player2.getSizeX(), player2.getSizeY()) >> false
            button.getToggleableWall() >> toggleableWall

        when:
            controller.checkButtonsClicked()

        then:
            1 * button.setPressed(false)
            1 * toggleableWall.setActive(true)

        cleanup:
            defaultCleanup()
    }

    def "key targets player 1 and player 1 collides"() {
        given:
            defaultSetup()
            player1.getName() >> "Player1"
            player1.getPosition() >> new Position(5, 5)
            player2.getName() >> "Player2"
            player2.getPosition() >> new Position(8, 10)

            key.getTarget() >> "Player1"
            player1.hasCollided(key.getPosition(), key.getSizeX(), key.getSizeY()) >> true
            key.isCollected() >> false
            level.getKeys() >> [key]

        when:
            controller.collectKeys()

        then:
            1 * key.setCollected(true)

        cleanup:
            defaultCleanup()
    }

    def "key targets player 1 but player 1 does not collide"() {
        given:
            defaultSetup()
            player1.getName() >> "Player1"
            player1.getPosition() >> new Position(5, 5)
            player2.getName() >> "Player2"
            player2.getPosition() >> new Position(8, 10)

            key.getTarget() >> "Player1"
            player1.hasCollided(key.getPosition(), key.getSizeX(), key.getSizeY()) >> false
            key.isCollected() >> false
            level.getKeys() >> [key]

        when:
            controller.collectKeys()

        then:
            0 * key.setCollected(true)

        cleanup:
            defaultCleanup()
    }

    def "key targets player 2 and player 2 collides"() {
        given:
            defaultSetup()
            player1.getName() >> "Player1"
            player1.getPosition() >> new Position(5, 5)
            player2.getName() >> "Player2"
            player2.getPosition() >> new Position(8, 10)

            key.getTarget() >> "Player2"
            player2.hasCollided(key.getPosition(), key.getSizeX(), key.getSizeY()) >> true
            key.isCollected() >> false
            level.getKeys() >> [key]

        when:
            controller.collectKeys()

        then:
            1 * key.setCollected(true)

        cleanup:
            defaultCleanup()
    }

    def "key targets player 2 but player 2 does not collide"() {
        given:
            defaultSetup()
            player1.getName() >> "Player1"
            player1.getPosition() >> new Position(5, 5)
            player2.getName() >> "Player2"
            player2.getPosition() >> new Position(8, 10)

            key.getTarget() >> "Player2"
            player2.hasCollided(key.getPosition(), key.getSizeX(), key.getSizeY()) >> false
            key.isCollected() >> false
            level.getKeys() >> [key]

        when:
            controller.collectKeys()

        then:
            0 * key.setCollected(true)

        cleanup:
            defaultCleanup()
    }

    def "key does not target either player"() {
        given:
            defaultSetup()
            player1.getName() >> "Player1"
            player1.getPosition() >> new Position(5, 5)
            player2.getName() >> "Player2"
            player2.getPosition() >> new Position(8, 10)

            key.getTarget() >> "None"
            key.isCollected() >> false
            level.getKeys() >> [key]

        when:
            controller.collectKeys()

        then:
            0 * key.setCollected(true)

        cleanup:
            defaultCleanup()
    }

    def "allKeysCollected returns true and opens door when all keys are collected"() {
        given:
            defaultSetup()
            def key1 = Mock(Key)
            key1.isCollected() >> true
            def key2 = Mock(Key)
            key2.isCollected() >> true
            level.getKeys() >> [key1, key2]

        when:
            def result = controller.allKeysCollected()

        then:
            result == true
            1 * door.setState(Door.STATE.OPEN)

        cleanup:
            defaultCleanup()
    }

    def "allKeysCollected returns false when at least one key is not collected"() {
        given:
            defaultSetup()
            def key1 = Mock(Key)
            key1.isCollected() >> true
            def key2 = Mock(Key)
            key2.isCollected() >> false
            level.getKeys() >> [key1, key2]


        when:
            def result = controller.allKeysCollected()

        then:
            result == false
            0 * door.setState(Door.STATE.OPEN)

        cleanup:
            defaultCleanup()
    }

    def "playerDied resets position, un-collects a key that matches and is collected, and closes the door"() {
        given:
            defaultSetup()
            def spawnPosition = new Position(5, 5)
            level.getPlayerSpawnPosition(player1) >> spawnPosition
            player1.getName() >> "Player1"

            def matchingKey = Mock(Key)
            matchingKey.getPosition() >> new Position(40,60)
            matchingKey.getTarget() >> "Player1"
            matchingKey.isCollected() >> true

            level.getKeys() >> [matchingKey]

        when:
            controller.playerDied(player1)

        then:
            1 * player1.setPosition(spawnPosition)
            1 * matchingKey.setCollected(false)
            1 * door.setState(Door.STATE.CLOSED)

        cleanup:
            defaultCleanup()
    }

    def "playerDied resets position, does not un-collect a key that matches but is not collected, and closes the door"() {
        given:
            defaultSetup()
            def spawnPosition = new Position(5, 5)
            level.getPlayerSpawnPosition(player1) >> spawnPosition
            player1.getName() >> "Player1"

            def matchingKey = Mock(Key)
            matchingKey.getPosition() >> new Position(50,60)
            matchingKey.getTarget() >> "Player1"
            matchingKey.isCollected() >> false

            level.getKeys() >> [matchingKey]
            level.getLevelEndingDoor() >> door

        when:
            controller.playerDied(player1)

        then:
            1 * player1.setPosition(spawnPosition)
            0 * matchingKey.setCollected(_)
            1 * door.setState(Door.STATE.CLOSED)

        cleanup:
            defaultCleanup()
    }

    def "playerDied resets position, does not un-collect a key that doesn't match but is not collected, and closes the door"() {
        given:
            defaultSetup()
            def spawnPosition = new Position(5, 5)
            level.getPlayerSpawnPosition(player1) >> spawnPosition
            player1.getName() >> "Player1"

            def unmatchingKey = Mock(Key)
            unmatchingKey.getPosition() >> new Position(60,60)
            unmatchingKey.getTarget() >> "Player2"
            unmatchingKey.isCollected() >> true

            level.getKeys() >> [unmatchingKey]
            level.getLevelEndingDoor() >> door

        when:
            controller.playerDied(player1)

        then:
            1 * player1.setPosition(spawnPosition)
            0 * unmatchingKey.setCollected(_)
            1 * door.setState(Door.STATE.CLOSED)

        cleanup:
            defaultCleanup()
    }

    def "checkLevelTransition returns #expectedResult for given conditions"() {
        given:
            defaultSetup()
            level.getKeys() >> [key]
            key.isCollected() >> keyCollected
            player1.isInside(door) >> player1Inside
            player2.isInside(door) >> player2Inside
            level.getPlayer1() >> player1
            level.getPlayer2() >> player2
            level.getLevelEndingDoor() >> door

        when:
            def result = controller.checkLevelTransition()

        then:
            result == expectedResult

        cleanup:
            defaultCleanup()

        where:
        keyCollected     | player1Inside | player2Inside || expectedResult
        true             | true          | true          || true
        false            | true          | true          || false
        true             | false         | true          || false
        true             | true          | false         || false
        true             | false         | false         || false
        false            | false         | true          || false
        false            | true          | false         || false
        false            | false         | false         || false
    }


    def "should reset player position and keys when player dies"() {
        given:
            defaultSetup()
            player1.hasCollided(_ as Position, _ as int, _ as int) >> true
            key.getTarget() >> "Tergon"
            player1.getName() >> "Tergon"
            key.isCollected() >> true
            level.getKeys() >> [key]

        and:
            def spawnPosition = new Position(5, 5)
            level.getPlayerSpawnPosition(player1) >> spawnPosition

        when:
            controller.playerDied(player1)

        then:
            1 * player1.setPosition(new Position(5, 5))
            1 * key.setCollected(false)
            1 * door.setState(Door.STATE.CLOSED)

        cleanup:
            defaultCleanup()
    }

    def "should return to menu when quit action is triggered"() {
        given:
            defaultSetup()
        when:
            controller.update(gameManager, [GUI.ACTION.QUIT] as Set, 0)

        then:
            1 * gameManager.setCurrentScene(_ as Menu)

        cleanup:
            defaultCleanup()
    }

    def "test update method with players on ground"() {
        given:
            def actions = Mock(Set)
            def actionList = [GUI.ACTION.A, GUI.ACTION.LEFT] as Set
            actions.contains(GUI.ACTION.QUIT) >> false
            actions.iterator() >> { actionList.iterator() }
            def updateTime = 1000
            def customLevel = Mock(Level)
            def customGameManager = Mock(GameManager)
            def player1CustomController = Mock(Player1Controller)
            def player2CustomController = Mock(Player2Controller)
            def monsterCustomController = Mock(MonsterController)
            customLevel.getPlayer1().getName() >> "Tergon"
            customLevel.getPlayer2().getName() >> "Lavena"
            def customPlayer1 = Mock(Player)
            def customPlayer2 = Mock(Player)
            customLevel.getPlayer1() >> customPlayer1
            customLevel.getPlayer2() >> customPlayer2
            def key1 = Mock(Key)
            def key2 = Mock(Key)
            customLevel.getKeys() >> [key1, key2]
            key1.getTarget() >> "Tergon"
            key1.getSizeX() >> 10
            key1.getSizeY() >> 10
            key1.isCollected() >> false
            key1.getPosition() >> new Position(50, 50)
            key2.getTarget() >> "Lavena"
            key2.getSizeX() >> 10
            key2.getSizeY() >> 10
            key2.isCollected() >> false
            key2.getPosition() >> new Position(100, 100)
            customLevel.getButtons() >> []
            customLevel.getToggleableWalls() >> []
            customLevel.getMonsters() >> []
            customLevel.getTraps() >> []
            customLevel.getPlayerSpawnPosition(customPlayer1) >> new Position(0, 0)
            customLevel.getPlayerSpawnPosition(customPlayer2) >> new Position(0, 0)
            def levelEndingDoor = Mock(Door)
            customLevel.getLevelEndingDoor() >> levelEndingDoor
            customPlayer1.isInside(levelEndingDoor) >> true
            customPlayer2.isInside(levelEndingDoor) >> true
            player1CustomController.isOnGround() >> true
            player2CustomController.isOnGround() >> true
            def levelController = new LevelController(customLevel, player1CustomController, player2CustomController, monsterCustomController)

        when:
            levelController.update(customGameManager, actions, updateTime)

        then:
            1 * actions.contains(GUI.ACTION.QUIT)
            1 * player1CustomController.update(customGameManager, [GUI.ACTION.LEFT] as Set, 1000)
            1 * player2CustomController.update(customGameManager, [GUI.ACTION.A] as Set, 1000)
            1 * monsterCustomController.update(customGameManager, _, 1000)
    }

    def "test update method with players not on ground"() {
        given:
            def actions = Mock(Set)
            def actionList = [GUI.ACTION.A, GUI.ACTION.LEFT] as Set
            actions.contains(GUI.ACTION.QUIT) >> false
            actions.iterator() >> { actionList.iterator() }
            def updateTime = 1000
            def customLevel = Mock(Level)
            def customGameManager = Mock(GameManager)
            def player1CustomController = Mock(Player1Controller)
            def player2CustomController = Mock(Player2Controller)
            def monsterCustomController = Mock(MonsterController)
            customLevel.getPlayer1().getName() >> "Tergon"
            customLevel.getPlayer2().getName() >> "Lavena"
            def customPlayer1 = Mock(Player)
            def customPlayer2 = Mock(Player)
            customLevel.getPlayer1() >> customPlayer1
            customLevel.getPlayer2() >> customPlayer2
            def key1 = Mock(Key)
            def key2 = Mock(Key)
            customLevel.getKeys() >> [key1, key2]
            key1.getTarget() >> "Tergon"
            key1.getSizeX() >> 10
            key1.getSizeY() >> 10
            key1.isCollected() >> false
            key1.getPosition() >> new Position(50, 50)
            key2.getTarget() >> "Lavena"
            key2.getSizeX() >> 10
            key2.getSizeY() >> 10
            key2.isCollected() >> false
            key2.getPosition() >> new Position(100, 100)
            customLevel.getButtons() >> []
            customLevel.getToggleableWalls() >> []
            def monster = Mock(Monster)
            monster.getPosition() >> new Position(70, 70)
            monster.getSizeX() >> 10
            monster.getSizeY() >> 10
            customLevel.getMonsters() >> [monster]
            customLevel.getTraps() >> []
            customPlayer1.hasCollided(new Position(70, 70), 10, 10) >> true
            customPlayer2.hasCollided(new Position(70, 70), 10, 10) >> true
            customLevel.getPlayerSpawnPosition(customPlayer1) >> new Position(0, 0)
            customLevel.getPlayerSpawnPosition(customPlayer2) >> new Position(0, 0)
            def levelEndingDoor = Mock(Door)
            customLevel.getLevelEndingDoor() >> levelEndingDoor
            customPlayer1.isInside(levelEndingDoor) >> true
            customPlayer2.isInside(levelEndingDoor) >> true
            player1CustomController.isOnGround() >> false
            player2CustomController.isOnGround() >> false
            def levelController = new LevelController(customLevel, player1CustomController, player2CustomController, monsterCustomController)

        when:
            levelController.update(customGameManager, actions, updateTime)

        then:
            1 * actions.contains(GUI.ACTION.QUIT)
            1 * player1CustomController.update(customGameManager, [GUI.ACTION.DOWN, GUI.ACTION.LEFT] as Set, 1000)
            1 * player2CustomController.update(customGameManager, [GUI.ACTION.S, GUI.ACTION.A] as Set, 1000)
            1 * monsterCustomController.update(customGameManager, _, 1000)
    }

    def "check transition to menu"() {
        given:
            def actions = Mock(Set)
            def actionList = [GUI.ACTION.A, GUI.ACTION.LEFT] as Set
            actions.contains(GUI.ACTION.QUIT) >> false
            actions.iterator() >> { actionList.iterator() }
            def updateTime = 1000
            def customLevel = Mock(Level)
            customLevel.getNextLevel() >> "menu"
            def customGameManager = Mock(GameManager)
            def player1CustomController = Mock(Player1Controller)
            def player2CustomController = Mock(Player2Controller)
            def monsterCustomController = Mock(MonsterController)
            customLevel.getPlayer1().getName() >> "Tergon"
            customLevel.getPlayer2().getName() >> "Lavena"
            def customPlayer1 = Mock(Player)
            def customPlayer2 = Mock(Player)
            customLevel.getPlayer1() >> customPlayer1
            customLevel.getPlayer2() >> customPlayer2
            def key1 = Mock(Key)
            def key2 = Mock(Key)
            customLevel.getKeys() >> [key1, key2]
            key1.getTarget() >> "Tergon"
            key1.getSizeX() >> 10
            key1.getSizeY() >> 10
            key1.isCollected() >> true
            key1.getPosition() >> new Position(50, 50)
            key2.getTarget() >> "Lavena"
            key2.getSizeX() >> 10
            key2.getSizeY() >> 10
            key2.isCollected() >> true
            key2.getPosition() >> new Position(100, 100)
            customLevel.getButtons() >> []
            customLevel.getToggleableWalls() >> []
            def monster = Mock(Monster)
            monster.getPosition() >> new Position(70, 70)
            monster.getSizeX() >> 10
            monster.getSizeY() >> 10
            customLevel.getMonsters() >> [monster]
            customLevel.getTraps() >> []
            customPlayer1.hasCollided(new Position(70, 70), 10, 10) >> true
            customPlayer2.hasCollided(new Position(70, 70), 10, 10) >> true
            customLevel.getPlayerSpawnPosition(customPlayer1) >> new Position(0, 0)
            customLevel.getPlayerSpawnPosition(customPlayer2) >> new Position(0, 0)
            def levelEndingDoor = Mock(Door)
            customLevel.getLevelEndingDoor() >> levelEndingDoor
            customPlayer1.isInside(levelEndingDoor) >> true
            customPlayer2.isInside(levelEndingDoor) >> true
            player1CustomController.isOnGround() >> false
            player2CustomController.isOnGround() >> false
            def levelController = new LevelController(customLevel, player1CustomController, player2CustomController, monsterCustomController)

        when:
            levelController.update(customGameManager, actions, updateTime)

        then:
            1 * customGameManager.setCurrentScene(new Menu())
    }

    def "check transition to another level"() {
        given:
            def actions = Mock(Set)
            def actionList = [GUI.ACTION.A, GUI.ACTION.LEFT] as Set
            actions.contains(GUI.ACTION.QUIT) >> false
            actions.iterator() >> { actionList.iterator() }
            def updateTime = 1000
            def customLevel = Mock(Level)
            customLevel.getNextLevel() >> "src/main/resources/levels/level1"
            def customGameManager = Mock(GameManager)
            def player1CustomController = Mock(Player1Controller)
            def player2CustomController = Mock(Player2Controller)
            def monsterCustomController = Mock(MonsterController)
            customLevel.getPlayer1().getName() >> "Tergon"
            customLevel.getPlayer2().getName() >> "Lavena"
            def customPlayer1 = Mock(Player)
            def customPlayer2 = Mock(Player)
            customLevel.getPlayer1() >> customPlayer1
            customLevel.getPlayer2() >> customPlayer2
            def key1 = Mock(Key)
            def key2 = Mock(Key)
            customLevel.getKeys() >> [key1, key2]
            key1.getTarget() >> "Tergon"
            key1.getSizeX() >> 10
            key1.getSizeY() >> 10
            key1.isCollected() >> true
            key1.getPosition() >> new Position(50, 50)
            key2.getTarget() >> "Lavena"
            key2.getSizeX() >> 10
            key2.getSizeY() >> 10
            key2.isCollected() >> true
            key2.getPosition() >> new Position(100, 100)
            customLevel.getButtons() >> []
            customLevel.getToggleableWalls() >> []
            def monster = Mock(Monster)
            monster.getPosition() >> new Position(70, 70)
            monster.getSizeX() >> 10
            monster.getSizeY() >> 10
            customLevel.getMonsters() >> [monster]
            customLevel.getTraps() >> []
            customPlayer1.hasCollided(new Position(70, 70), 10, 10) >> true
            customPlayer2.hasCollided(new Position(70, 70), 10, 10) >> true
            customLevel.getPlayerSpawnPosition(customPlayer1) >> new Position(0, 0)
            customLevel.getPlayerSpawnPosition(customPlayer2) >> new Position(0, 0)
            def levelEndingDoor = Mock(Door)
            customLevel.getLevelEndingDoor() >> levelEndingDoor
            customPlayer1.isInside(levelEndingDoor) >> true
            customPlayer2.isInside(levelEndingDoor) >> true
            player1CustomController.isOnGround() >> false
            player2CustomController.isOnGround() >> false
            def levelController = new LevelController(customLevel, player1CustomController, player2CustomController, monsterCustomController)

        when:
            levelController.update(customGameManager, actions, updateTime)

        then:
            1 * actions.contains(GUI.ACTION.QUIT)
            1 * customGameManager.setCurrentScene(_)
    }

    def "should collect both keys during update"() {
        given:
            def customGameManager = Mock(GameManager)
            def customPlayer1 = Mock(Player)
            def customPlayer2 = Mock(Player)
            def key1 = Mock(Key)
            def key2 = Mock(Key)
            def player1CustomController = Mock(Player1Controller)
            def player2CustomController = Mock(Player2Controller)
            def monsterCustomController = Mock(MonsterController)

            def customLevel = Mock(Level)
            customLevel.getPlayer1() >> customPlayer1
            customLevel.getPlayer2() >> customPlayer2
            customLevel.getKeys() >> [key1, key2]
            customLevel.getButtons() >> []
            customLevel.getToggleableWalls() >> []
            customLevel.getTraps() >> []
            def monster = Mock(Monster)
            monster.getPosition() >> new Position(70, 70)
            monster.getSizeX() >> 10
            monster.getSizeY() >> 10
            customLevel.getMonsters() >> [monster]
            def levelEndingDoor = Mock(Door)
            customLevel.getLevelEndingDoor() >> levelEndingDoor
            customPlayer1.isInside(levelEndingDoor) >> false
            customPlayer2.isInside(levelEndingDoor) >> false
            player1CustomController.isOnGround() >> false
            player2CustomController.isOnGround() >> false

            def customController = new LevelController(customLevel, player1CustomController, player2CustomController, monsterCustomController)

        and:
            customPlayer1.getName() >> "Tergon"
            customPlayer2.getName() >> "Lavena"
            key1.getTarget() >> customPlayer1.getName()
            key2.getTarget() >> customPlayer2.getName()
            customPlayer1.hasCollided(key1.getPosition(), key1.getSizeX(), key1.getSizeY()) >> true
            customPlayer2.hasCollided(key2.getPosition(), key2.getSizeX(), key2.getSizeY()) >> true

        and:
            def actions = [] as Set

        when:
            customController.update(customGameManager, actions, 0)

        then:
            1 * key1.setCollected(true)
            1 * key2.setCollected(true)
    }

    def "should check if buttons are clicked during update"() {
        given:
            def customGameManager = Mock(GameManager)
            def customPlayer1 = Mock(Player)
            def customPlayer2 = Mock(Player)
            def key1 = Mock(Key)
            def key2 = Mock(Key)
            def player1CustomController = Mock(Player1Controller)
            def player2CustomController = Mock(Player2Controller)
            def monsterCustomController = Mock(MonsterController)

            def customLevel = Mock(Level)
            customLevel.getPlayer1() >> customPlayer1
            customLevel.getPlayer2() >> customPlayer2
            customLevel.getKeys() >> [key1, key2]
            def toggleableWall = Mock(ToggleableWall)
            customLevel.getToggleableWalls() >> [toggleableWall]
            def button1 = Mock(Button)
            def button2 = Mock(Button)
            button1.getToggleableWall() >> toggleableWall
            button2.getToggleableWall() >> toggleableWall
            customLevel.getButtons() >> [button1,button2]
            customLevel.getTraps() >> []
            def monster = Mock(Monster)
            monster.getPosition() >> new Position(70, 70)
            monster.getSizeX() >> 10
            monster.getSizeY() >> 10
            customLevel.getMonsters() >> [monster]
            def levelEndingDoor = Mock(Door)
            customLevel.getLevelEndingDoor() >> levelEndingDoor
            customPlayer1.isInside(levelEndingDoor) >> false
            customPlayer2.isInside(levelEndingDoor) >> false
            player1CustomController.isOnGround() >> false
            player2CustomController.isOnGround() >> false

            def customController = new LevelController(customLevel, player1CustomController, player2CustomController, monsterCustomController)

        and:
            customPlayer1.getName() >> "Tergon"
            customPlayer2.getName() >> "Lavena"
            key1.getTarget() >> customPlayer1.getName()
            key2.getTarget() >> customPlayer2.getName()
            button1.hasCollided(customPlayer1.getPosition(), customPlayer1.getSizeX(), customPlayer1.getSizeY()) >> true

        and:
            def actions = [] as Set

        when:
            customController.update(customGameManager, actions, 0)

        then:
            1 * toggleableWall.setActive(false)
    }
}