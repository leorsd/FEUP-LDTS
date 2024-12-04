import spock.lang.Specification
import Model.Scenes.Menu

class MenuTest extends Specification {

    def "should correctly highlight the next entry"() {
        given:"a Menu with three entries"
        def entries = ["Entry1", "Entry2", "Entry3"]
        def menu = new Menu(entries)

        when:"selectNextEntry is called"
        menu.selectNextEntry()

        then:"the highlighted entry should be the second one"
        menu.getHighlightedEntryIndex() == 1
        menu.getHighlightedEntry() == "Entry2"

        when:"selectNextEntry is called again"
        menu.selectNextEntry()

        then:"the highlighted entry should be the third one"
        menu.getHighlightedEntryIndex() == 2
        menu.getHighlightedEntry() == "Entry3"

        when:"selectNextEntry is called again (wrapping around)"
        menu.selectNextEntry()

        then:"the highlighted entry should be the first one"
        menu.getHighlightedEntryIndex() == 0
        menu.getHighlightedEntry() == "Entry1"
    }

    def "should correctly highlight the previous entry"() {
        given:"a Menu with three entries"
        def entries = ["Entry1", "Entry2", "Entry3"]
        def menu = new Menu(entries)

        when:"selectPreviousEntry is called (wrapping around)"
        menu.selectPreviousEntry()

        then:"the highlighted entry should be the last one"
        menu.getHighlightedEntryIndex() == 2
        menu.getHighlightedEntry() == "Entry3"

        when:"selectPreviousEntry is called again"
        menu.selectPreviousEntry()

        then:"the highlighted entry should be the second one"
        menu.getHighlightedEntryIndex() == 1
        menu.getHighlightedEntry() == "Entry2"

        when:"selectPreviousEntry is called again"
        menu.selectPreviousEntry()

        then:"the highlighted entry should be the first one"
        menu.getHighlightedEntryIndex() == 0
        menu.getHighlightedEntry() == "Entry1"
    }

    def "should return null if trying to get highlighted entry when no entries exist"() {
        given:"a Menu with no entries"
        def menu = new Menu([])

        expect:"getHighlightedEntry should return null"
        menu.getHighlightedEntry() == null
    }

    def "should return 0 when trying to get the size of a menu with no entries"() {
        given:"a Menu with no entries"
        def menu = new Menu([])

        expect: "getEntriesSize should return 0"
        menu.getEntriesSize() == 0
    }

    def "should correctly return the number of entries"() {
        given:"a Menu with specific entries"
        def entries = ["Entry1", "Entry2", "Entry3"]
        def menu = new Menu(entries)

        expect:"getEntriesSize should return the number of entries"
        menu.getEntriesSize() == 3
    }

    def "should handle initialization with default entries"() {
        given:"a Menu initialized without entries"
        def menu = new Menu()

        expect:"the Menu should have default entries"
        menu.getEntriesSize() == 2
        menu.getHighlightedEntry() == "src/main/resources/Levels/level1"

        when:"select the next entry"
        menu.selectNextEntry()

        then:"it should highlight the second one"
        menu.getHighlightedEntry() == "EXIT"

    }
}
