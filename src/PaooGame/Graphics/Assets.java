package PaooGame.Graphics;

import java.awt.image.BufferedImage;


public class Assets
{
    public static final int FRAME_SIZE = 48;


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


    public static BufferedImage[] walkDown  = new BufferedImage[11];
    public static BufferedImage[] walkLeft  = new BufferedImage[11];
    public static BufferedImage[] walkRight = new BufferedImage[11];
    public static BufferedImage[] walkUp    = new BufferedImage[11];


    public static BufferedImage[] crawlDown  = new BufferedImage[6];
    public static BufferedImage[] crawlLeft  = new BufferedImage[6];
    public static BufferedImage[] crawlRight = new BufferedImage[6];
    public static BufferedImage[] crawlUp    = new BufferedImage[6];


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

        //nui bine
        for (int i = 0; i < 11; i++)
            walkDown[i] = cropChar(charSheet, i, 0);

       //nu bine
        for (int i = 0; i < 11; i++)
            walkRight[i] = cropChar(charSheet, i, 1);

        // nui bine
        for (int i = 0; i < 11; i++)
            walkLeft[i] = cropChar(charSheet, i, 2);

        // Rnui bine
        for (int i = 0; i < 11; i++)
            walkUp[i] = cropChar(charSheet, i, 3);

        // Rnui bine
        for (int i = 0; i < 6; i++)
            crawlDown[i] = cropChar(charSheet, i, 4);

        // nui bine
        for (int i = 0; i < 6; i++)
            crawlRight[i] = cropChar(charSheet, i, 5);

        // inverted
        for (int i = 0; i < 6; i++)
            crawlLeft[i] = cropChar(charSheet, i, 6);

        // nui bine
        for (int i = 0; i < 6; i++)
            crawlUp[i] = cropChar(charSheet, i, 7);

        //e prea multe pt fiecare trebe doar 1 max 2 pnguri de acolo ca leam pus pe toate
        // poti sa dai la ai ca din tot vectoru ala  tre sa alegem 2 max 3 pas stanga pas dreapta


        playerDown       = walkDown[0];
        playerLeft       = walkLeft[0];
        playerRight      = walkRight[0];
        playerUp         = walkUp[0];
        playerCrawlLeft  = crawlLeft[0];
        playerCrawlRight = crawlRight[0];
    }

    /**
     * Crops a 48x48 frame from the character sprite sheet.
     * @param sheet  the SpriteSheet wrapping the character PNG
     * @param col    column index (0-based)
     * @param row    row index (0-based)
     */
    private static BufferedImage cropChar(SpriteSheet sheet, int col, int row)
    {

         return sheet.crop(col, row);
    }
}