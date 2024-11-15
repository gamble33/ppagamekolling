import game.Game;
import game.Location;
import game.factories.NpcFactory;
import game.factories.LocationFactory;
import jsonParser.JSONParser;
import persistence.GameContentFile;
import persistence.JsonReader;

public class Main {
    public static void main(String[] args) {
        new Main().initializeGame();
    }
    
    public void initializeGame() {
        for (GameContentFile npcFile : new JsonReader().readGameDataFiles("npc")) {
            NpcFactory.createNpc(new JSONParser(npcFile.getContents()).parse());
        }

        for (GameContentFile roomFile : new JsonReader().readGameDataFiles("location")) {
            LocationFactory.createRoom(new JSONParser(roomFile.getContents()).parse());
        }
        LocationFactory.injectDependencies();
        Location startingLocation = LocationFactory.getRoom("Home");

        Game game = new Game(startingLocation);
        game.play();
    }
}