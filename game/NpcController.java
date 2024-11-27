package game;

import game.enums.Behaviour;

public class NpcController {

    private final Game game;

    public NpcController(Game game) {
        this.game = game;
    }

    public void updateNpcs(Location currentLocation) {
        currentLocation.getNpcList().forEach(npc -> {
            if (npc.getBehaviour().equals(Behaviour.Aggressive)) {
                Damage damage = npc.getBehaviour().getDamage();
                game.damage(damage.getAmount());
                game.getView().addText(npc.getName() + " " + damage.getDescription());
                game.getView().addText("This deals 🔪💔" + damage.getAmount() + " damage.");
            }
        });
    }
}
