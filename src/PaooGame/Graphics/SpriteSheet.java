package PaooGame.Graphics;

import java.awt.image.BufferedImage;

public class SpriteSheet {
    private BufferedImage spriteSheet;
    private static final int tileWidth = 32;
    private static final int tileHeight = 32;

    public SpriteSheet(BufferedImage buffImg) {
        spriteSheet = buffImg;
    }


    public BufferedImage crop(int x, int y) {
        return spriteSheet.getSubimage(x * tileWidth, y * tileHeight, tileWidth, tileHeight);
    }

    public BufferedImage crop(int x, int y, int width, int height) {

        return spriteSheet.getSubimage(x, y, width, height);
    }
}