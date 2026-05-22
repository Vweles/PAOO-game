package PaooGame.Managers;

import PaooGame.Characters.Entity;
import PaooGame.Characters.MC;
import PaooGame.Items.ItemGenerator;
import PaooGame.Items.Iteme;
import PaooGame.State.BattleState;
import PaooGame.State.PlayState;
import PaooGame.State.State;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

public class CombatManager {
    private final PlayState playState;
    private final Random random = new Random();

    public CombatManager(PlayState playState) {
        this.playState = playState;
    }

    public void checkAndTriggerCombat(MC player, ArrayList<Entity> enemies) {
        if (enemies == null || enemies.isEmpty()) return;


        Rectangle pRect = new Rectangle((int)player.getX() + player.getSolidArea().x,
                (int)player.getY() + player.getSolidArea().y,
                player.getSolidArea().width, player.getSolidArea().height);

        for (int i = 0; i < enemies.size(); i++) {
            Entity e = enemies.get(i);
            Rectangle eRect = new Rectangle((int)e.getX() + e.getSolidArea().x,
                    (int)e.getY() + e.getSolidArea().y,
                    e.getSolidArea().width, e.getSolidArea().height);

            if (pRect.intersects(eRect)) {
                triggerBattle(player, e, enemies, i);
                break;
            }
        }
    }

    private void triggerBattle(MC player, Entity enemy, ArrayList<Entity> enemies, int index) {
        generateLootDrop(player);
        enemies.remove(index);


        playState.getKeyInput().up = false;
        playState.getKeyInput().down = false;
        playState.getKeyInput().left = false;
        playState.getKeyInput().right = false;

        State.SetState(new BattleState(playState.getKeyInput(), playState.getMouseManager(), player, enemy, playState));
    }

    private void generateLootDrop(MC player) {
        Iteme.ItemType[] dropTypes = {
                Iteme.ItemType.HEAD, Iteme.ItemType.CHEST, Iteme.ItemType.HANDS,
                Iteme.ItemType.BOOTS, Iteme.ItemType.CLOAK, Iteme.ItemType.RING
        };
        Iteme dropEquip = ItemGenerator.generateRandomItem(dropTypes[random.nextInt(dropTypes.length)]);
        player.getInventory().bag.add(dropEquip);

        Iteme.ItemType pType = random.nextBoolean() ? Iteme.ItemType.POTION_HP : Iteme.ItemType.POTION_MANA;
        player.getInventory().bag.add(ItemGenerator.generateRandomItem(pType));
    }
}