import java.util.Arrays;

import jsonParser.Lexer;
import persistence.JsonReader;

public class Main {
    public static void main(String[] args) {
        String contents = new JsonReader().readGameDataFiles().get(0).getContents();
        System.out.println(contents);
        System.out.println(Arrays.toString(new Lexer(contents).scanTokens().toArray()));
    }
}
