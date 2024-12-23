package model.scenes

import spock.lang.Specification

class MenuTest extends Specification {

    def "test hashcode"() {
        given:
            def menu = new Menu()
            def menu2 = new Menu()
        expect:
            menu.hashCode() == menu2.hashCode()
        when:
            menu2.selectPreviousEntry()
        then:
            menu.hashCode() != menu2.hashCode()
    }

    def "should correctly compare menus for equality"() {
        given:
            def menu1 = new Menu(entries1)
            def menu2 = new Menu(entries2)
        expect:
            menu1.equals(menu1)
            (menu1 == menu2) == expectedResult
        where:
            entries1                       | entries2                       | expectedResult
            ["Start", "Options", "Exit"]   | ["Start", "Options", "Exit"]   | true
            ["Start", "Options", "Exit"]   | ["Play", "Settings", "Quit"]   | false
            ["Start", "Options", "Exit"]   | []                             | false

    }

    def "should correctly compare menus for equality using the correct highlight entry"() {
        given:
            def menu1 = new Menu(["Entry1", "Entry2", "Entry3"])
            def menu2 = new Menu(["Entry1", "Entry2", "Entry3"])
        expect:
            menu1.equals(menu2)
            !menu1.equals(10)
        when:
            menu2.selectPreviousEntry()
        then:
            !menu1.equals(menu2)
    }


    def "should correctly highlight the next entry"() {
        given:
            def entries = ["Entry1", "Entry2", "Entry3"]
            def menu = new Menu(entries)
        when:
            menu.selectNextEntry()
        then:
            menu.getHighlightedEntryIndex() == 1
            menu.getHighlightedEntry() == "Entry2"
        when:
            menu.selectNextEntry()
        then:
            menu.getHighlightedEntryIndex() == 2
            menu.getHighlightedEntry() == "Entry3"
        when:
            menu.selectNextEntry()
        then:
            menu.getHighlightedEntryIndex() == 0
            menu.getHighlightedEntry() == "Entry1"
    }

    def "should correctly highlight the previous entry"() {
        given:
            def entries = ["Entry1", "Entry2", "Entry3"]
            def menu = new Menu(entries)
        when:
            menu.selectPreviousEntry()
        then:
            menu.getHighlightedEntryIndex() == 2
            menu.getHighlightedEntry() == "Entry3"
        when:
            menu.selectPreviousEntry()
        then:
            menu.getHighlightedEntryIndex() == 1
            menu.getHighlightedEntry() == "Entry2"
        when:
            menu.selectPreviousEntry()
        then:
            menu.getHighlightedEntryIndex() == 0
            menu.getHighlightedEntry() == "Entry1"
    }

    def "all possibilities when the entries is empty"() {
        given:
            def menu = new Menu([])
        when:
            menu.selectNextEntry()
        then:
            menu.getHighlightedEntryIndex() == 0
        when:
            menu.selectPreviousEntry()
        then:
            menu.getHighlightedEntryIndex() == 0
        expect:
            menu.getHighlightedEntry() == null
            menu.getEntriesSize() == 0
    }

    def "should correctly return the number of entries"() {
        given:
            def entries = ["Entry1", "Entry2", "Entry3"]
            def menu = new Menu(entries)
        expect:
            menu.getEntriesSize() == 3
    }

    def "should handle initialization with default entries"() {
        given:
            def menu = new Menu()
        expect:
            menu.getEntriesSize() == 6
            menu.getHighlightedEntry() == "src/main/resources/levels/level0"
        when:
            menu.selectNextEntry()
        then:
            menu.getHighlightedEntry() == "src/main/resources/levels/level1"
    }
}
