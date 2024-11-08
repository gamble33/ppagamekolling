package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class JsonReader {
    public List<GameContentFile> readGameDataFiles(String path) {
        Path gameDataPath = Paths.get("data/" + path);
        List<GameContentFile> gameContentFiles = new ArrayList<>();
        try {
            Stream<Path> files = Files.list(gameDataPath);
            files.filter(filePath -> filePath.toString().endsWith(".json")).forEach(filePath -> {
                try {
                    String contents = String.join("\n", Files.readAllLines(filePath, StandardCharsets.UTF_8));
                    String fileName = filePath.toString().split("\\.")[0];
                    gameContentFiles.add(new GameContentFile(fileName, contents));
                } catch (IOException exception) {
                    System.err.println("Couldn't read file " + filePath.toString() + ": " + exception.getMessage());
                }
            });
            files.close();
        } catch (IOException exception) {
            System.err.println("Error accessing game data directory: " + exception.getMessage());
        }
        return gameContentFiles;
    }
}
