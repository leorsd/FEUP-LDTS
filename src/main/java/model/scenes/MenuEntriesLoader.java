package model.scenes;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MenuEntriesLoader {

    public static List<String> readFile(String menu) throws IOException {
        List<String> entries = new ArrayList<>();
        BufferedReader br;
        Path path = Paths.get(menu);
        try {
            br = Files.newBufferedReader(path);
        } catch (IOException e) {
            throw new IOException("Error while trying to open menu configs file while trying to load menu");
        }
        String line;
        try {
            line = br.readLine();
        } catch (IOException e) {
            throw new IOException("Error while trying to read line from menu configs file while trying to load menu");
        }
        while (line  != null) {
            entries.add(line);
            if ("EXIT".equals(line)) {
                break;
            }
            try {
                line = br.readLine();
            } catch (IOException e) {
                throw new IOException("Error while trying to read line from menu configs file while trying to load menu");
            }
        }
        return entries;
    }
}

