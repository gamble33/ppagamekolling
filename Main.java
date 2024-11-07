import jsonParser.JSONObject;
import jsonParser.JsonParser;
import persistence.JsonReader;

public class Main {
    public static void main(String[] args) {
        String contents = new JsonReader().readGameDataFiles().get(0).getContents();
        System.out.println(contents);
        JSONObject object = new JsonParser(contents).parse();
        System.out.println(object.getString("name"));
        object.debugPrintTree(0);
    }
}
