package model.scenes

import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.Path

class MenuEntriesLoaderTest extends Specification  {
    def menuEntriesLoader = Mock(MenuEntriesLoader)

    def "should handle empty file"() {
        given:
            def tempFile = Files.createTempFile("menu", ".txt")

        when:
            def entries = menuEntriesLoader.readFile(tempFile.toString())

        then:
            entries.isEmpty()

        cleanup:
            Files.delete(tempFile)
    }

    def "should return empty list when IOException is thrown"() {
        given:
            GroovyMock(Files, global: true)
            Files.newBufferedReader(_ as Path) >> { throw new IOException("Mocked IOException") }

        when:
            def result = MenuEntriesLoader.readFile("mocked_menu.txt")

        then:
            result.isEmpty()
    }
}
