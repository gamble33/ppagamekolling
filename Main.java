import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import game.Game;
import game.Npc;
import game.Room;
import game.factories.NpcFactory;
import game.factories.RoomFactory;
import jsonParser.JSONParser;
import persistence.GameContentFile;
import persistence.JsonReader;

public class Main {
    public static void main(String[] args) {
        new Main().initializeGame();
    }
    
    public void initializeGame() {
        List<Npc> npcList = new ArrayList<>();
        for (GameContentFile npcFile : new JsonReader().readGameDataFiles("npc")) {
            npcList.add(NpcFactory.createNpc(new JSONParser(npcFile.getContents()).parse()));
        }

        System.out.println(Arrays.toString(npcList.toArray()));

        for (GameContentFile roomFile : new JsonReader().readGameDataFiles("rooms")) {
            RoomFactory.createRoom(new JSONParser(roomFile.getContents()).parse());
        }
        RoomFactory.injectDependencies();
        Room startingRoom = RoomFactory.getRoom("Home");

        Game game = new Game(startingRoom);
        game.play();
    }
}
