import Model.Scenes.Level
import spock.lang.Specification
import Model.Elements.MovingElements.Player
import Model.Elements.Wall
import Model.Position

class LevelTest extends Specification {

    def "test isPositionFree with no walls or obstacles"() {
        given: "a level with no walls, monsters, traps, or keys"
        def player1 = Mock(Player)
        def player2 = Mock(Player)
        def walls = []
        def monsters = []
        def traps = []
        def keys = []
        def level = new Level(walls, monsters, traps, keys, player1, player2, 10, 10)

        and: "a free position inside the boundaries"
        def position = new Position(5, 5)

        expect: "the position should be free"
        level.isPositionFree(position)
    }

    def "test isPositionFree with a wall at the position"() {
        given: "a level with one wall"
        def player1 = Mock(Player)
        def player2 = Mock(Player)
        def wall = new Wall(new Position(5, 5), null, 1, 1)
        def walls = [wall]
        def monsters = []
        def traps = []
        def keys = []
        def level = new Level(walls, monsters, traps, keys, player1, player2, 10, 10)

        and: "a position where a wall is present"
        def position = new Position(5, 5)

        expect: "the position should not be free"
        !level.isPositionFree(position)
    }

    def "test boundary check"() {
        given: "a level with boundaries"
        def player1 = Mock(Player)
        def player2 = Mock(Player)
        def walls = []
        def monsters = []
        def traps = []
        def keys = []
        def level = new Level(walls, monsters, traps, keys, player1, player2, 5, 5)

        and: "a position outside the boundaries"
        def position = new Position(6, 6)

        expect: "the position should not be free"
        !level.isPositionFree(position)
    }
}

