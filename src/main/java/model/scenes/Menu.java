package model.scenes;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class Menu implements Scene{
    private final List<String> entries;
    private int highlightedEntryIndex = 0;

    public Menu(List<String> entries) {
        this.entries = entries;
    }

    public Menu() throws IOException {
        this.entries = MenuEntriesLoader.readFile("src/main/resources/levels/menu");
    }

    public void selectNextEntry() {
        if (entries.isEmpty()) return;
        highlightedEntryIndex = (highlightedEntryIndex + 1) % entries.size();
    }

    public void selectPreviousEntry() {
        if (entries.isEmpty()) return;
        if (highlightedEntryIndex == 0) {
            highlightedEntryIndex = entries.size() - 1;
        } else {
            highlightedEntryIndex = (highlightedEntryIndex - 1) % entries.size();
        }
    }

    public String getHighlightedEntry() {
        if (entries.isEmpty())
            return null;
        return entries.get(highlightedEntryIndex);
    }

    public int getEntriesSize() {
        return entries.size();
    }

    public int getHighlightedEntryIndex() {
        return highlightedEntryIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Menu menu)) return false;
        return highlightedEntryIndex == menu.highlightedEntryIndex && Objects.equals(entries, menu.entries);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entries, highlightedEntryIndex);
    }
}
