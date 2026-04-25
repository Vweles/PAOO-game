package PaooGame.Map;

import PaooGame.CtHrt.Utils;
import PaooGame.Tiles.Tile;
import java.awt.Graphics;
// aici vezi ca nai bagt layer 2
// rezolva cu nostru si alea eld
public class Map {
    private int width, height;
    private int spawnX, spawnY;
    private int[][] tiles;

    public Map(String path) {

        loadMap(path);
    }
    public int getTileID(int col, int row) {
        if (col < 0 || row < 0 || col >= width || row >= height) {
            return 7; // aici poate fac cu nesimtitrea aia a mea de tile1 tile 2 tile 3
        }
        return tiles[row][col];
    }

    private void loadMap(String path) {
        String file = Utils.loadFileAsString(path);

        String[] tokens = file.split("\\s+");


        width = Utils.parseInt(tokens[0]);
        height = Utils.parseInt(tokens[1]);
        spawnX = Utils.parseInt(tokens[2]);
        spawnY = Utils.parseInt(tokens[3]);

        tiles = new int[height][width];

        int k = 4;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if(k < tokens.length) {
                    tiles[y][x] = Utils.parseInt(tokens[k++]);
                }
            }
        }
    }

    public void Draw(Graphics g) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int id = tiles[y][x];


                if (Tile.tiles[id] != null) {

                    Tile.tiles[id].Draw(g, x * Tile.TILE_WIDTH, y * Tile.TILE_HEIGHT);
                }
            }
        }
    }

    public Tile getTile(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height) {
            return Tile.Abis;
        }

        Tile t = Tile.tiles[tiles[y][x]];
        if (t == null) {
            return Tile.grassTile;
        }
        return t;
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
}