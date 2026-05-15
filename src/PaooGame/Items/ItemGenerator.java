package PaooGame.Items;

import PaooGame.Graphics.Assets;
import java.awt.image.BufferedImage;
import java.util.Random;

public class ItemGenerator {
    private static Random rand = new Random();

    private static final int TILE_W = Assets.itemSheet.getWidth() / 8;
    private static final int TILE_H = Assets.itemSheet.getHeight() / 6;

    public static Iteme generateRandomItem(Iteme.ItemType type) {
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
}