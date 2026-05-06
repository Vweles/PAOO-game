package PaooGame.Graphics;

import java.awt.image.BufferedImage;

public class Assets {
    public static final int FRAME_SIZE = 32;

    public static BufferedImage soil, grass, mountain, water, tree, Abis, Tile1, Wall, tent, endDf, endAf;
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
        // 1. Dale de baza (32x32) - Aici metoda crop(x, y) inca inmulteste cu 32, e corect
        Abis = new SpriteSheet(ImageLoader.LoadImage("/LVL1/pnguri/abyss_tile.png")).crop(0, 0);
        Tile1 = new SpriteSheet(ImageLoader.LoadImage("/LVL1/pnguri/Tiles.png")).crop(0, 0);
        Wall = new SpriteSheet(ImageLoader.LoadImage("/LVL1/pnguri/Cracks.png")).crop(0, 0);
        tent = new SpriteSheet(ImageLoader.LoadImage("/LVL1/pnguri/Tentacle 1 Sheet.png")).crop(0, 0);

        // 2. Eldritch Entity - Folosim dimensiuni sigure (96x192 conform grilei de 3x6 dale)
        SpriteSheet eldritchDSheet = new SpriteSheet(ImageLoader.LoadImage("/LVL1/pnguri/Eldritch Entity Dormant.png"));
        endDf = eldritchDSheet.crop(0, 0, 96, 192);

        SpriteSheet tentacleASheet = new SpriteSheet(ImageLoader.LoadImage("/LVL1/pnguri/Eldritch Entity Awaken.png"));
        endAf = tentacleASheet.crop(0, 0, 96, 192);

        // 3. Sub-tile-uri Eldritch
        for (int i = 0; i < 18; i++) {
            int row = i / 3;
            int col = i % 3;
            // Trimitem pixelii calculati: (0, 32, 64...)
            eldritchAwaken[i] = tentacleASheet.crop(col * 32, row * 32, 32, 32);
            eldritchDormant[i] = eldritchDSheet.crop(col * 32, row * 32, 32, 32);
        }

        // 4. SpriteSheet joc (iarba, munte etc.)
        SpriteSheet sheet = new SpriteSheet(ImageLoader.LoadImage("/textures/PaooGameSpriteSheet.png"));
        grass = sheet.crop(0, 0);
        soil = sheet.crop(1, 0);
        water = sheet.crop(2, 0);
        mountain = sheet.crop(3, 0);
        tree = sheet.crop(3, 1);

        // 5. Personaj (2.png) - REPARAT COMPLET
        SpriteSheet charSheet = new SpriteSheet(ImageLoader.LoadImage("/MC/2.png"));

        // Walk (Randurile 0-3)
        for (int i = 0; i < 3; i++) {
            walkDown[i]  = cropChar(charSheet, i, 0);
            walkRight[i] = cropChar(charSheet, i, 1);
            walkLeft[i]  = cropChar(charSheet, i, 2);
            walkUp[i]    = cropChar(charSheet, i, 3);
        }

        // Crawl (Randurile 4-7) - Folosim doar 2 cadre pentru ca spritesheet-ul e limitat
        for (int i = 0; i < 2; i++) {
            crawlDown[i]  = cropChar(charSheet, i, 4);
            crawlRight[i] = cropChar(charSheet, i, 5);
            crawlLeft[i]  = cropChar(charSheet, i, 6);
            crawlUp[i]    = cropChar(charSheet, i, 7);
        }
    }

    private static BufferedImage cropChar(SpriteSheet sheet, int col, int row) {
        // Trimitem pixelii exacti: coloana * 48 si randul * 48
        return sheet.crop(col * 48, row * 48, 48, 48);
    }
}