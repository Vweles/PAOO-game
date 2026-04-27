package PaooGame.Map;

import PaooGame.Tiles.Tile;
import PaooGame.Graphics.GameCamera;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Map {
    private int width = 75;
    private int height = 50;
    private int[][] tiles;

    public Map(String path) {
        tiles = new int[height][width];
        loadMap(path);
    }

    private void loadMap(String path) {
        try {
            InputStream is = getClass().getResourceAsStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            for (int r = 0; r < height; r++) {
                String line = br.readLine();
                if (line == null) break;
                String[] tokens = line.trim().split("\\s+");
                for (int c = 0; c < width; c++) {
                    if (c < tokens.length) {
                        tiles[r][c] = Integer.parseInt(tokens[c]);
                    }
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Draw(Graphics g, GameCamera camera) {
        // Culling bazat pe camera
        int xStart = (int) Math.max(0, camera.getxOffset() / Tile.TILE_WIDTH);
        int xEnd = (int) Math.min(width, (camera.getxOffset() + 800) / Tile.TILE_WIDTH + 1);
        int yStart = (int) Math.max(0, camera.getyOffset() / Tile.TILE_HEIGHT);
        int yEnd = (int) Math.min(height, (camera.getyOffset() + 600) / Tile.TILE_HEIGHT + 1);

        for (int y = yStart; y < yEnd; y++) {
            for (int x = xStart; x < xEnd; x++) {
                int id = tiles[y][x];
                if (Tile.tiles[id] != null) {
                    // NU scadem offset-ul aici pentru ca PlayState-ul tau face deja g2d.translate()
                    Tile.tiles[id].Draw(g, x * Tile.TILE_WIDTH, y * Tile.TILE_HEIGHT);
                }
            }
        }
    }

    public int getTileID(int col, int row) {
        if (col < 0 || row < 0 || col >= width || row >= height) return 1;
        return tiles[row][col];
    }

    // METODELE DE CARE ARE NEVOIE PLAYSTATE:
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}