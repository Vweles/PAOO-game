package PaooGame.Graphics;

import java.awt.image.BufferedImage;


public class Assets
{
    public static final int FRAME_SIZE = 32;


    public static BufferedImage soil;
    public static BufferedImage grass;
    public static BufferedImage mountain;
    public static BufferedImage townGrass;
    public static BufferedImage townGrassDestroyed;
    public static BufferedImage townSoil;
    public static BufferedImage water;
    public static BufferedImage rockUp;
    public static BufferedImage rockDown;
    public static BufferedImage rockLeft;
    public static BufferedImage rockRight;
    public static BufferedImage tree;
    public static  BufferedImage Abis;
    public static  BufferedImage Tile1;
    public static  BufferedImage Wall;
    // invata cum se implementeaza astea de deasupra
    public static  BufferedImage endDf;
    public static  BufferedImage endAf;
    public static  BufferedImage tent;


    public static BufferedImage[] walkDown  = new BufferedImage[3];
    public static BufferedImage[] walkLeft  = new BufferedImage[3];
    public static BufferedImage[] walkRight = new BufferedImage[3];
    public static BufferedImage[] walkUp    = new BufferedImage[3];

    public static BufferedImage[] crawlDown  = new BufferedImage[2];
    public static BufferedImage[] crawlLeft  = new BufferedImage[2];
    public static BufferedImage[] crawlRight = new BufferedImage[2];
    public static BufferedImage[] crawlUp    = new BufferedImage[2];


    /** @deprecated Use walkLeft[0] / walkLeft[1] instead */
    public static BufferedImage playerLeft;
    /** @deprecated Use walkRight[0] / walkRight[1] instead */
    public static BufferedImage playerRight;
    /** @deprecated Use walkUp[0] / walkUp[1] instead */
    public static BufferedImage playerUp;
    /** @deprecated Use walkDown[0] / walkDown[1] instead */
    public static BufferedImage playerDown;
    /** @deprecated Use crawlLeft[0] instead */
    public static BufferedImage playerCrawlLeft;
    /** @deprecated Use crawlRight[0] instead */
    public static BufferedImage playerCrawlRight;

    /*! \fn public static void Init()
        \brief Initializaza toate referintele catre elementele grafice.
    */
    public static void Init()
    {
        SpriteSheet baseTiles = new SpriteSheet(ImageLoader.LoadImage("/LVL1/pnguri/abyss_tile.png"));
        Abis = baseTiles.crop(0, 0); // Ajustează x, y după poziția în PNG


        SpriteSheet GrndT = new SpriteSheet(ImageLoader.LoadImage("/LVL1/pnguri/Tiles.png"));
        Tile1 = GrndT.crop(0, 0);


        SpriteSheet wall = new SpriteSheet(ImageLoader.LoadImage("/LVL1/pnguri/Cracks.png"));
        Wall = wall.crop(0, 0);


        SpriteSheet TNETT = new SpriteSheet(ImageLoader.LoadImage("/LVL1/pnguri/Tentacle 1 Sheet.png"));
        tent = TNETT.crop(0, 0);


        SpriteSheet eldritchDSheet = new SpriteSheet(ImageLoader.LoadImage("/LVL1/pnguri/Eldritch Entity Dormant.png"));
        endDf = eldritchDSheet.crop(0, 0, 96, 192); // 3*32 latime, 6*32 inaltime

        SpriteSheet tentacleASheet = new SpriteSheet(ImageLoader.LoadImage("/LVL1/pnguri/Eldritch Entity Awaken.png"));
        endAf = tentacleASheet.crop(0, 0, 96, 192);




        SpriteSheet sheet = new SpriteSheet(
                ImageLoader.LoadImage("/textures/PaooGameSpriteSheet.png")
        );


        grass              = sheet.crop(0, 0);
        soil               = sheet.crop(1, 0);
        water              = sheet.crop(2, 0);
        mountain           = sheet.crop(3, 0);
        townGrass          = sheet.crop(0, 1);
        townGrassDestroyed = sheet.crop(1, 1);
        townSoil           = sheet.crop(2, 1);
        tree               = sheet.crop(3, 1);
        rockUp             = sheet.crop(2, 2);
        rockDown           = sheet.crop(3, 2);
        rockLeft           = sheet.crop(0, 3);
        rockRight          = sheet.crop(1, 3);

        SpriteSheet charSheet = new SpriteSheet(
                ImageLoader.LoadImage("/MC/2.png")
        );

        for (int i = 0; i < 3; i++) walkDown[i] = cropChar(charSheet, i, 0);
        for (int i = 0; i < 3; i++) walkRight[i] = cropChar(charSheet, i, 1);
        for (int i = 0; i < 3; i++) walkLeft[i] = cropChar(charSheet, i, 2);
        for (int i = 0; i < 3; i++) walkUp[i] = cropChar(charSheet, i, 3);

        crawlDown[0] = cropChar(charSheet, 0, 4);
        crawlRight[0] = cropChar(charSheet, 1, 5);
        crawlLeft[0] = cropChar(charSheet, 0, 6);
        crawlUp[0] = cropChar(charSheet, 1, 7);

        playerDown       = walkDown[0];
        playerLeft       = walkLeft[0];
        playerRight      = walkRight[0];
        playerUp         = walkUp[0];
        playerCrawlLeft  = crawlLeft[0];
        playerCrawlRight = crawlRight[0];
    }


    private static BufferedImage cropChar(SpriteSheet sheet, int col, int row)
    {

        return sheet.crop(col, row, 48, 48);
    }
}