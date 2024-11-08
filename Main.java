import game.Game;
import game.Room;
import game.factories.RoomFactory;
import jsonParser.JSONParser;
import persistence.GameContentFile;
import persistence.JsonReader;

public class Main {
    public static void main(String[] args) {
        new Main().initializeGame();
    }
    
    public void initializeGame() {
        for (GameContentFile roomFile : new JsonReader().readGameDataFiles("rooms")) {
            RoomFactory.createRoom(new JSONParser(roomFile.getContents()).parse());
        }
        RoomFactory.injectDependencies();
        Room startingRoom = RoomFactory.getRoom("Home");

        Game game = new Game(startingRoom);
        game.play();
    }
}
