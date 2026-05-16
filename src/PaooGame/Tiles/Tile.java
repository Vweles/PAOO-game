package PaooGame.Tiles;

import PaooGame.Graphics.Assets;
import PaooGame.Graphics.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {
    public static Tile[] tiles = new Tile[5000];

    public static Tile abisTile = new Tile(Assets.Abis, 1, true);
    public static Tile groundTile = new Tile(Assets.Tile1, 2, false);
    public static Tile wallTile = new Tile(Assets.Wall, 6, true);

    public static Tile grassTile = new GrassTile(9);
    public static Tile soilTile = new SoilTile(4);
    public static Tile waterTile = new WaterTile(5);
    public static Tile treeTile = new TreeTile(7);
    public static Tile mountainTile = new MountainTile(8);

    public static final int TILE_WIDTH = 32;
    public static final int TILE_HEIGHT = 32;

    protected BufferedImage img;
    protected final int id;
    protected boolean isSolid;

    public Tile(BufferedImage image, int idd, boolean solid) {
        this.img = image;
        this.id = idd;
        this.isSolid = solid;
        if (id >= 0 && id < tiles.length) {
            tiles[id] = this;
        }
    }

    public static void InitCustomTiles() {
        for(int i = 0; i < 18; i++) {
            if(Assets.eldritchAwaken[i] != null)
                new Tile(Assets.eldritchAwaken[i], 144 + i, true);
        }
        for(int i = 0; i < 18; i++) {
            if(Assets.eldritchDormant[i] != null)
                new Tile(Assets.eldritchDormant[i], 180 + i, true);
        }
    }

    public static void InitLevel2Tiles() {
        if(Assets.lvl2Sheet == null) return;

        new Tile(Assets.getLvl2Crop(0, 0, 32, 32), 3, false);
        new Tile(Assets.getLvl2Crop(0, 256, 32, 32), 257, true);
        new Tile(Assets.getLvl2Crop(384, 0, 32, 32), 13, true);
    }
    public static void InitLevel3Tiles() {
        if(Assets.lvl3Sheet == null) return;


        new Tile(Assets.getLvl3Crop(64, 64, 32, 32),91 , false);
        new Tile(Assets.getLvl3Crop(384, 64, 32, 32), 101, true);
        new Tile(Assets.getLvl3Crop(1088, 288, 32, 32), 431, true);


    }

    public void Draw(Graphics g, int x, int y) {
        if(img != null) {
            g.drawImage(img, x, y, TILE_WIDTH, TILE_HEIGHT, null);
        }
    }



    public boolean IsSolid() { return isSolid; }
    public int GetId() { return id; }
}