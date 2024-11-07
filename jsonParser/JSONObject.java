package jsonParser;

import java.util.Map;

public class JSONObject {
    private final Map<String, Object> properties;

    public JSONObject(Map<String, Object> properties) {
        this.properties = properties;
    }

    public String getString(String key) {
        return (String) properties.get(key);
    }

    public void debugPrintTree(int depth) {
        System.out.print("{");
        System.out.println();

        for (Map.Entry<String, Object> property : properties.entrySet()) {
            System.out.print(" ".repeat(3 * depth + 3));
            System.out.print(property.getKey() + ": ");
            if (property.getValue() == null) {
                System.out.print("null");
            }
            else if (property.getValue() instanceof JSONObject) {
                ((JSONObject) property.getValue()).debugPrintTree(depth + 1);
            } else {
                System.out.print(property.getValue().toString());
            }
            System.out.println();
        }

        System.out.print(" ".repeat(3 * depth));
        System.out.print("}");
    }
}
