package game;

public class Damage {
    private final float amount;
    private final String description;

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
