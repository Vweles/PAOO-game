package PaooGame.Map;

import PaooGame.CtHrt.Utils;
import PaooGame.Tiles.Tile;
import java.awt.Graphics;

public class Map {
    private int width, height;
    private int spawnX, spawnY;

    private int[][] tilesLayer1;
    private int[][] tilesLayer2;

    public Map(String path) {
        loadMap(path);
    }

    public int getTileID(int col, int row) {
        if (col < 0 || row < 0 || col >= width || row >= height) {
            return 7;
        }

        if (tilesLayer2[row][col] != -1) {
            return tilesLayer2[row][col];
        }
        return tilesLayer1[row][col];
    }


    private void loadMap(String path) {
        String file = Utils.loadFileAsString(path);
        String[] tokens = file.split("\\s+");

        width = Utils.parseInt(tokens[0]);
        height = Utils.parseInt(tokens[1]);
        spawnX = Utils.parseInt(tokens[2]);
        spawnY = Utils.parseInt(tokens[3]);

        tilesLayer1 = new int[height][width];
        tilesLayer2 = new int[height][width];

        int k = 4;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if(k < tokens.length) {
                    tilesLayer1[y][x] = Utils.parseInt(tokens[k++]);
                }
            }
        }


        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if(k < tokens.length) {
                    tilesLayer2[y][x] = Utils.parseInt(tokens[k++]);
                } else {
                    tilesLayer2[y][x] = -1;
                }
            }
        }
    }


    public void Draw(Graphics g) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                int id1 = tilesLayer1[y][x];
                if (Tile.tiles[id1] != null) {
                    Tile.tiles[id1].Draw(g, x * Tile.TILE_WIDTH, y * Tile.TILE_HEIGHT);
                }

                int id2 = tilesLayer2[y][x];
                if (id2 != -1 && Tile.tiles[id2] != null) {
                    Tile.tiles[id2].Draw(g, x * Tile.TILE_WIDTH, y * Tile.TILE_HEIGHT);
                }
            }
        }
    }

    public Tile getTile(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height) {
            return Tile.Abis;
        }

        int id;
        if (tilesLayer2[y][x] != -1) {
            id = tilesLayer2[y][x];
        } else {
            id = tilesLayer1[y][x];
        }

        Tile t = Tile.tiles[id];
        if (t == null) {
            return Tile.grassTile;
        }
        return t;
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getSpawnX() { return spawnX; }
    public int getSpawnY() { return spawnY; }
}