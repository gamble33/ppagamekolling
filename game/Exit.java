package game;

public class Exit {
    private final String name;
    private final String itemRequirement;
    private final Location location;

    public Exit(String name, Location location, String itemRequirement) {
        this.name = name;
        this.itemRequirement = itemRequirement;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public String getItemRequirement() {
        return itemRequirement;
    }

    public Location getLocation() {
        return location;
    }

    public boolean hasItemRequirement() {
        return itemRequirement != null;
    }
}
