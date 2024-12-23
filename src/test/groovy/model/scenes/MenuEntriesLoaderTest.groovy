package model.scenes

import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.Path

class MenuEntriesLoaderTest extends Specification  {
    def menuEntriesLoader = Mock(MenuEntriesLoader)

    def "should not handle files under 6 entries"() {
        given:
            def tempFile = Files.createTempFile("menu", ".txt")

        when:
            def exception = null
            try {
                def entries = menuEntriesLoader.readFile(tempFile.toString())
            } catch (Exception e) {
                exception = e.message
            }

        then:
        exception != null
        exception == "Error: Menu should have 6 entries"

        cleanup:
            Files.delete(tempFile)
    }

    def "should catch exception when wrong file path is used"() {
        given:
            GroovyMock(Files, global: true)
            Files.newBufferedReader(_ as Path) >> { throw new IOException("Mocked IOException") }

        when:
            def exception = null
            try {
                MenuEntriesLoader.readFile("wrong_path_for_menu.txt")
            } catch (Exception e) {
                exception = e.message
            }

        then:
            exception != null
            exception == "Error while trying to open menu configs file while trying to load menu"
    }
}
