package jsonParser;

import java.util.ArrayList;
import java.util.List;

public class JSONArray {
    private List<Object> objects;

    public JSONArray(List<Object> objects) {
        this.objects = objects;
    }

    public <T> List<T> getList() {
        List<T> list = new ArrayList<T>();
        for (Object object : objects) {
            list.add((T) object);
        }
        return list;
    }

    public void debugPrintArray(int depth) {
        System.out.print("[");
        for (Object object : objects) {
            if (object instanceof JSONObject) ((JSONObject) object).debugPrintTree(depth);
            else if (object instanceof JSONArray) ((JSONArray) object).debugPrintArray(depth);
            else System.out.print(object);
            System.out.print(", ");
        }
        System.out.print("]");
    }
}
