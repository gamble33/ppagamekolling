package game.enums;

import game.Damage;

public enum Behaviour {
    Passive, Neutral, Aggressive;

    private Damage damage;

    public Damage getDamage() {
        return damage;
    }

    public void setDamage(Damage damage) {
        this.damage = damage;
    }
}
