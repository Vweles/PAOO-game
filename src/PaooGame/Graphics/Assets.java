package PaooGame.Graphics;

import java.awt.image.BufferedImage;

public class Assets {
    public static final int FRAME_SIZE = 32;

    public static BufferedImage soil, grass, mountain, water, tree, Abis, Tile1, Wall, tent, endDf, endAf;
    public static BufferedImage minion_full, minion_no_arm, boss, lvl2Sheet;
    public static BufferedImage minion_lvl2, boss_lvl2;
    public static BufferedImage lvl3Sheet;
    public static BufferedImage lvl3minion,lvl3bosss;

    public static BufferedImage[] btn_attack = new BufferedImage[2];
    public static BufferedImage[] btn_items = new BufferedImage[2];
    public static BufferedImage[] btn_identify = new BufferedImage[2];
    public static BufferedImage[] btn_heal_hp = new BufferedImage[2];

    public static BufferedImage[] walkDown = new BufferedImage[3];
    public static BufferedImage[] walkLeft = new BufferedImage[3];
    public static BufferedImage[] walkRight = new BufferedImage[3];
    public static BufferedImage[] walkUp = new BufferedImage[3];
    public static BufferedImage[] crawlDown = new BufferedImage[2];
    public static BufferedImage[] crawlLeft = new BufferedImage[2];
    public static BufferedImage[] crawlRight = new BufferedImage[2];
    public static BufferedImage[] crawlUp = new BufferedImage[2];
    public static BufferedImage[] eldritchAwaken = new BufferedImage[18];
    public static BufferedImage[] eldritchDormant = new BufferedImage[18];

    public static void Init() {
        // 1. Dale de baza
        Abis = new SpriteSheet(ImageLoader.LoadImage("/LVL1/pnguri/abyss_tile.png")).crop(0, 0);
        Tile1 = new SpriteSheet(ImageLoader.LoadImage("/LVL1/pnguri/Tiles.png")).crop(0, 0);
        Wall = new SpriteSheet(ImageLoader.LoadImage("/LVL1/pnguri/Cracks.png")).crop(0, 0);
        tent = new SpriteSheet(ImageLoader.LoadImage("/LVL1/pnguri/Tentacle 1 Sheet.png")).crop(0, 0);

        // 2. Eldritch Entity
        SpriteSheet eldritchDSheet = new SpriteSheet(ImageLoader.LoadImage("/LVL1/pnguri/Eldritch Entity Dormant.png"));
        endDf = eldritchDSheet.crop(0, 0, 96, 192);

        SpriteSheet tentacleASheet = new SpriteSheet(ImageLoader.LoadImage("/LVL1/pnguri/Eldritch Entity Awaken.png"));
        endAf = tentacleASheet.crop(0, 0, 96, 192);

        // 3. Sub-tile-uri Eldritch
        for (int i = 0; i < 18; i++) {
            int row = i / 3;
            int col = i % 3;
            eldritchAwaken[i] = tentacleASheet.crop(col * 32, row * 32, 32, 32);
            eldritchDormant[i] = eldritchDSheet.crop(col * 32, row * 32, 32, 32);
        }

        // 4. SpriteSheet joc (profesor)
        SpriteSheet sheet = new SpriteSheet(ImageLoader.LoadImage("/textures/PaooGameSpriteSheet.png"));
        grass = sheet.crop(0, 0);
        soil = sheet.crop(1, 0);
        water = sheet.crop(2, 0);
        mountain = sheet.crop(3, 0);
        tree = sheet.crop(3, 1);

        // 5. Personaj (Animații 48x48)
        SpriteSheet charSheet = new SpriteSheet(ImageLoader.LoadImage("/MC/2.png"));
        for (int i = 0; i < 3; i++) {
            walkDown[i]  = cropChar(charSheet, i, 0);
            walkRight[i] = cropChar(charSheet, i, 1);
            walkLeft[i]  = cropChar(charSheet, i, 2);
            walkUp[i]    = cropChar(charSheet, i, 3);
        }
        for (int i = 0; i < 2; i++) {
            crawlDown[i]  = cropChar(charSheet, i, 4);
            crawlRight[i] = cropChar(charSheet, i, 5);
            crawlLeft[i]  = cropChar(charSheet, i, 6);
            crawlUp[i]    = cropChar(charSheet, i, 7);
        }

        // 6. Inamici
        minion_full = ImageLoader.LoadImage("/LVL1/Characaters/silva_fiull.png");
        minion_no_arm = ImageLoader.LoadImage("/LVL1/Characaters/silva_faramana_dreapta.png");
        boss = ImageLoader.LoadImage("/LVL1/Characaters/Bosslvl1.png");
    }

    private static BufferedImage cropChar(SpriteSheet sheet, int col, int row) {
        return sheet.crop(col * 48, row * 48, 48, 48);
    }



    public static BufferedImage getLvl2Crop(int x, int y, int width, int height) {
        if (lvl2Sheet == null) return null;
        try {
            // Folosim direct getSubimage pentru precizie pe pixeli
            return lvl2Sheet.getSubimage(x, y, width, height);
        } catch (Exception e) {
            System.err.println("Eroare crop Lvl2 la: " + x + "," + y);
            return null;
        }
    }
    public static BufferedImage getLvl3Crop(int x, int y, int width, int height) {
        if (lvl3Sheet == null) return null;
        try {
            return lvl3Sheet.getSubimage(x, y, width, height);
        } catch (Exception e) {
            System.err.println("Eroare crop Lvl3 la: " + x + "," + y);
            return null;
        }
    }
    public static void InitLevel2() {
        lvl2Sheet = ImageLoader.LoadImage("/LVL2/tileset_lvl2.png");
        // Placeholders sau imaginile reale pentru inamici Lvl 2
        minion_lvl2 = ImageLoader.LoadImage("/LVL2/implvl2.png");
        boss_lvl2 = ImageLoader.LoadImage("/LVL2/boslvl2.png");
    }
    public static void InitLevel3() {

        lvl3Sheet = ImageLoader.LoadImage("/LVL3/tilelvl3.png");
        lvl3minion = ImageLoader.LoadImage("/LVL3/minionlvl3.png");
        lvl3bosss = ImageLoader.LoadImage("/LVL3/bossslvl3.png");


    }
}