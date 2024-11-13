package game.sound;

public enum Sound implements FilePathProvider {
    MusicHome("data/sound/Balzhan.wav");


    private final String filePath;

    Sound(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String getFilePath() {
        return filePath;
    }

}
