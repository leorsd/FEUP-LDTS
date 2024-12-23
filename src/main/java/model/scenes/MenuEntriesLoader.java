package model.scenes;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MenuEntriesLoader {

    public static List<String> readFile(String menu) throws IOException {
        List<String> entries = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(menu))) {
            String line;
            while ((line = br.readLine()) != null) {
                entries.add(line);
                if ("EXIT".equals(line)) {
                    break;
                }
            }
        } catch (IOException e) {
            throw new IOException("Error while reading menu entries from file: " + menu);
        }
        return entries;
    }
}

