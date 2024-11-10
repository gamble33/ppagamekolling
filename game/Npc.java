package game;

public class Npc {
    private String name;
    private String description;
    private int age;

    public Npc(String name, String description, int age) {
        this.name = name;
        this.description = description;
        this.age = age;
    }

    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        return name + ", " + description + ", " + age;
    }
    
}
