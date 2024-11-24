package Model.Scenes;

import java.util.ArrayList;
import java.util.List;

public class Menu implements Scene{
    private List<String> entries;
    private int highlightedEntryIndex = 0;

    public Menu(List<String> entries) {
        this.entries = entries;
    }

    public Menu() {
        this.entries = new ArrayList<>();
        // TODO: read default entries from file
    }

    public void selectNextEntry() {
        if (entries.isEmpty()) return;
        highlightedEntryIndex = (highlightedEntryIndex + 1) % entries.size();
    }

    public void selectPreviousEntry() {
        if (entries.isEmpty()) return;
        highlightedEntryIndex = (highlightedEntryIndex - 1) % entries.size();
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
}
