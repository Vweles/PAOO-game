package PaooGame.Items;

import java.awt.image.BufferedImage;

public class Iteme {
    public enum ItemType {
        HEAD, CHEST, HANDS,
        BOOTS, CLOAK, RING,
        ARTIFACT,
        POTION_HP, POTION_MANA
    }

    public String name;
    public ItemType type;
    public BufferedImage texture;


    public int bonusStr, bonusInte, bonusAgi, bonusTho;

    public Iteme(String name, ItemType type, BufferedImage texture, int s, int i, int a, int t) {
        this.name = name;
        this.type = type;
        this.texture = texture;
        this.bonusStr = s;
        this.bonusInte = i;
        this.bonusAgi = a;
        this.bonusTho = t;
    }
}