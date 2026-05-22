package PaooGame.Items;

import PaooGame.Characters.MC;
import java.util.ArrayList;

public class Inventory {
    public ArrayList<Iteme> bag = new ArrayList<>();
    public Iteme head, chest, hands, boots, cloak, ring;
    public Iteme[] artifacts = new Iteme[3];
    private MC player;
    private int baseStr, baseInte, baseAgi, baseTho;

    public Inventory(MC player) {
        this.player = player;
        // Folosim getterele pentru a inițializa valorile de bază
        this.baseStr = player.getStr();
        this.baseInte = player.getInte();
        this.baseAgi = player.getAgi();
        this.baseTho = player.getTho();
    }

    public void applyTotalStats() {
        int bonusStr = 0, bonusInte = 0, bonusAgi = 0, bonusTho = 0;

        Iteme[] equipped = {head, chest, hands, boots, cloak, ring};
        for (Iteme i : equipped) {
            if (i != null) {
                bonusStr += i.bonusStr;
                bonusInte += i.bonusInte;
                bonusAgi += i.bonusAgi;
                bonusTho += i.bonusTho;
            }
        }
        for (Iteme art : artifacts) {
            if (art != null) {
                bonusStr += art.bonusStr;
                bonusInte += art.bonusInte;
                bonusAgi += art.bonusAgi;
                bonusTho += art.bonusTho;
            }
        }

        // Folosim setterele pentru a actualiza statisticile player-ului
        player.setStr(baseStr + bonusStr);
        player.setInte(baseInte + bonusInte);
        player.setAgi(baseAgi + bonusAgi);
        player.setTho(baseTho + bonusTho);

        // Apelăm metoda de actualizare după modificarea statisticilor
        player.updateStatsAfterInjury();
    }

    public void equip(Iteme item) {
        if (item == null) return;

        if (item.type == Iteme.ItemType.HEAD) {
            if (head != null) bag.add(head);
            head = item;
        }
        else if (item.type == Iteme.ItemType.CHEST) {
            if (chest != null) bag.add(chest);
            chest = item;
        }
        else if (item.type == Iteme.ItemType.HANDS) {
            if (hands != null) bag.add(hands);
            hands = item;
        }
        else if (item.type == Iteme.ItemType.BOOTS) {
            if (boots != null) bag.add(boots);
            boots = item;
        }
        else if (item.type == Iteme.ItemType.CLOAK) {
            if (cloak != null) bag.add(cloak);
            cloak = item;
        }
        else if (item.type == Iteme.ItemType.RING) {
            if (ring != null) bag.add(ring);
            ring = item;
        }
        else if (item.type == Iteme.ItemType.ARTIFACT) {
            boolean slotGasit = false;
            for (int i = 0; i < artifacts.length; i++) {
                if (artifacts[i] == null) {
                    artifacts[i] = item;
                    slotGasit = true;
                    break;
                }
            }
            if (!slotGasit) {
                bag.add(artifacts[0]);
                artifacts[0] = item;
            }
        }

        applyTotalStats();
    }
}