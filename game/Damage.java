package game;

public class Damage {
    float amount;
    String description;

    public Damage(float amount, String description) {
        this.amount = amount;
        this.description = description;
    }

    public float getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }
}
