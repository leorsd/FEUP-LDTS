import spock.lang.Specification
import Model.Scenes.Menu

class MenuTest extends Specification {

    def "test menu with entries"() {
        given: "a menu with 3 entries"
        def entries = ['Play', 'Settings', 'Exit']
        def menu = new Menu(entries)

        expect: "the first entry is highlighted by default"
        menu.getHighlightedEntry() == 'Play'
        menu.getHighlightedEntryIndex() == 0

        when: "selecting the next entry"
        menu.selectNextEntry()

        then: "the second entry should be highlighted"
        menu.getHighlightedEntry() == 'Settings'
        menu.getHighlightedEntryIndex() == 1

        when: "selecting next twice (cycling through the entries)"
        menu.selectNextEntry()
        menu.selectNextEntry()

        then: "the first entry should be highlighted again"
        menu.getHighlightedEntry() == 'Play'
        menu.getHighlightedEntryIndex() == 0

        when: "selecting next and then previous entry"
        menu.selectNextEntry()
        menu.selectPreviousEntry()

        then: "the first entry should still be highlighted"
        menu.getHighlightedEntryIndex() == 0
        menu.getHighlightedEntry() == 'Play'
    }

    def "test menu without entries"() {
        given: "a menu with no entries"
        def menu = new Menu()

        expect: "there should be no highlighted entry"
        menu.getHighlightedEntry() == null
        menu.getEntriesSize() == 0

        when: "trying to select next or previous entry"
        menu.selectNextEntry()
        menu.selectPreviousEntry()

        then: "no exception should be thrown and still no entry should be selected"
        menu.getHighlightedEntry() == null
    }
}
