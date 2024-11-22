import game.Game;
import game.Location;
import game.factories.ItemFactory;
import game.factories.NpcFactory;
import game.factories.LocationFactory;
import jsonParser.JSONObject;
import jsonParser.JSONParser;
import persistence.GameContentFile;
import persistence.JsonReader;

public class Main {
    public static void main(String[] args) {
        new Main().initializeGame();
    }
    
    public void initializeGame() {
        JsonReader reader = new JsonReader();

        reader.readGameDataFiles("item").forEach(file -> {
            JSONObject json = new JSONParser(file.getContents()).parse();
            ItemFactory.createItem(json);
        });

        for (GameContentFile npcFile : reader.readGameDataFiles("npc")) {
            NpcFactory.createNpc(new JSONParser(npcFile.getContents()).parse());
        }

        for (GameContentFile roomFile : reader.readGameDataFiles("location")) {
            LocationFactory.createRoom(new JSONParser(roomFile.getContents()).parse());
        }
        LocationFactory.injectDependencies();
        Location startingLocation = LocationFactory.getRoom("Home");

        Game game = new Game(startingLocation);
        game.play();
    }
}