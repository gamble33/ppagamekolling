package game.factories;

import game.item.FoodItem;
import game.item.Item;
import game.item.PortalItem;
import jsonParser.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ItemFactory {
    private static Map<String, Item> items = new HashMap<>();

    public static Item getItem(String id) {
        return items.get(id);
    }

    public static void createItem(JSONObject json) {
        String id = json.getString("id");
        String name = json.getString("name");
        float weight = json.getFloat("weight");
        boolean canPickUp = json.getBoolean("canPickUp");
        String type = json.getString("type");

        float damage = json.has("damage") ? json.getFloat("damage") : 1.0f;

        Item item = switch (type) {
            case "FoodItem" -> {
                float saturation = json.getFloat("saturation");
                yield new FoodItem(name, weight, canPickUp, damage, saturation);
            }
            case "Portal" -> new PortalItem(name, weight, canPickUp, damage);
            case "Item" -> new Item(name, weight, canPickUp, damage);
            default -> null;
        };

        items.put(id, item);
    }
}
