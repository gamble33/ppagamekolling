package persistence;

public class GameContentFile {
    private String name;
    private String contents;

    public GameContentFile(String name, String contents) {
        this.name = name;
        this.contents = contents;
    }

    public String getName() {
        return name;
    }

    public String getContents() {
        return contents;
    }

    @Override
    public String toString() {
        return name;
    }
}
