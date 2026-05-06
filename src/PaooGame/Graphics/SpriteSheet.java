package PaooGame.Graphics;

import java.awt.image.BufferedImage;

public class SpriteSheet {
    private BufferedImage spriteSheet;
    private static final int tileWidth = 32;
    private static final int tileHeight = 32;

    public SpriteSheet(BufferedImage buffImg) {
        spriteSheet = buffImg;
    }

    // Metoda pentru dale standard (32x32) - foloseste indexul dalei (0, 1, 2...)
    public BufferedImage crop(int x, int y) {
        return spriteSheet.getSubimage(x * tileWidth, y * tileHeight, tileWidth, tileHeight);
    }

    // REPARAT: Metoda pentru dimensiuni custom - acum foloseste PIXELI DIRECTI
    public BufferedImage crop(int x, int y, int width, int height) {
        // Am eliminat (x * width) pentru ca acum trimitem pixelii gata calculati
        return spriteSheet.getSubimage(x, y, width, height);
    }
}