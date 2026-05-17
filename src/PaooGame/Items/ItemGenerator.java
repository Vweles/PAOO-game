package PaooGame.Items;

import PaooGame.Graphics.Assets;
import java.awt.image.BufferedImage;
import java.util.Random;

public class ItemGenerator {
    private static Random rand = new Random();

    private static final int TILE_W = Assets.itemSheet.getWidth() / 8;
    private static final int TILE_H = Assets.itemSheet.getHeight() / 6;

    public static Iteme generateRandomItem(Iteme.ItemType type) {

        if (type == Iteme.ItemType.POTION_HP || type == Iteme.ItemType.POTION_MANA) {
            return generatePotion(type);
        }

        String arhetip = "";
        int s = 0, i = 0, a = 0, t = 0;

        int rol = rand.nextInt(4);
        int col = rol * 2;

        int mainStat = rand.nextInt(11) + 10;
        int sideStat = rand.nextInt(5) + 1;

        switch (rol) {
            case 0: // war
                arhetip = "Vanguard";
                s = mainStat; i = sideStat; a = sideStat; t = sideStat + 2;
                break;
            case 1: // mage
                arhetip = "Arcane";
                s = sideStat; i = mainStat; a = sideStat; t = sideStat;
                break;
            case 2: // assasins?ish
                arhetip = "Shadow";
                s = sideStat; i = sideStat; a = mainStat; t = sideStat;
                break;
            case 3: // tank
                arhetip = "Titan";
                s = sideStat + 2; i = sideStat; a = sideStat; t = mainStat;
                break;
        }

        int row = 0;
        if (type == Iteme.ItemType.HEAD) row = 0;
        else if (type == Iteme.ItemType.CLOAK) row = 1;
        else if (type == Iteme.ItemType.CHEST) row = 2;
        else if (type == Iteme.ItemType.HANDS) row = 3;
        else if (type == Iteme.ItemType.RING) row = 4;
        else if (type == Iteme.ItemType.BOOTS) row = 5;

        BufferedImage texture = Assets.itemSheet.getSubimage(col * TILE_W, row * TILE_H, TILE_W, TILE_H);

        String itemName = arhetip + " " + type.toString();
        return new Iteme(itemName, type, texture, s, i, a, t);
    }

    private static Iteme generatePotion(Iteme.ItemType type) {
        int marimeSansa = rand.nextInt(3);
        String marimeNume = "";
        int valoareRefacere = 0;
        BufferedImage texture = null;

        if (marimeSansa == 0) {
            marimeNume = "Mica";
            valoareRefacere = (type == Iteme.ItemType.POTION_HP) ? 30 : 25;

            texture = (type == Iteme.ItemType.POTION_HP) ? Assets.potionHP[0] : Assets.potionMana[0];
        } else if (marimeSansa == 1) {
            marimeNume = "Medie";
            valoareRefacere = (type == Iteme.ItemType.POTION_HP) ? 60 : 50;

            texture = (type == Iteme.ItemType.POTION_HP) ? Assets.potionHP[1] : Assets.potionMana[1];
        } else {
            marimeNume = "Mare";
            valoareRefacere = (type == Iteme.ItemType.POTION_HP) ? 100 : 80;

            texture = (type == Iteme.ItemType.POTION_HP) ? Assets.potionHP[2] : Assets.potionMana[2];
        }

        String tipNume = (type == Iteme.ItemType.POTION_HP) ? "Potiune de Viata" : "Potiune de Mana";
        String numeleFinal = tipNume + " " + marimeNume;

        return new Iteme(numeleFinal, type, texture, valoareRefacere, 0, 0, 0);
    }
}