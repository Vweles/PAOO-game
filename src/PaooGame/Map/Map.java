package PaooGame.Map;

import PaooGame.Tiles.Tile;
import PaooGame.Graphics.GameCamera;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Map {
    private int width, height;
    private int[][][] layers; // [Strat][Y][X]

    public Map(String... paths) {
        this.width = 75;
        this.height = 50;
        layers = new int[paths.length][height][width];

        for(int i = 0; i < paths.length; i++) {
            loadLayer(paths[i], i);
        }
        Tile.InitCustomTiles();
    }

    private void loadLayer(String path, int layerIdx) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(path)));
            for (int y = 0; y < height; y++) {
                String line = br.readLine();
                if(line == null) break;
                String[] tokens = line.trim().split("\\s+");
                for (int x = 0; x < width; x++) {
                    layers[layerIdx][y][x] = Integer.parseInt(tokens[x]);
                }
            }
            br.close();
        } catch (Exception e) {
            System.err.println("Eroare incarcare strat " + layerIdx + ": " + e.getMessage());
        }
    }

    // ADĂUGAT: Metoda cerută de CollisionChecker pentru a returna ID-ul brut
    public int getTileID(int x, int y, int layer) {
        if (x < 0 || y < 0 || x >= width || y >= height || layer >= layers.length) return 1; // Returnează Abis implicit
        return layers[layer][y][x];
    }

    public void Draw(Graphics g, GameCamera camera) {
        int xStart = (int) Math.max(0, camera.getxOffset() / Tile.TILE_WIDTH);
        int xEnd = (int) Math.min(width, (camera.getxOffset() + 800) / Tile.TILE_WIDTH + 1);
        int yStart = (int) Math.max(0, camera.getyOffset() / Tile.TILE_HEIGHT);
        int yEnd = (int) Math.min(height, (camera.getyOffset() + 600) / Tile.TILE_HEIGHT + 1);

        for (int l = 0; l < layers.length; l++) {
            for (int y = yStart; y < yEnd; y++) {
                for (int x = xStart; x < xEnd; x++) {
                    int id = layers[l][y][x];
                    if (id > 0 && Tile.tiles[id] != null) {
                        Tile.tiles[id].Draw(g, (int)(x * Tile.TILE_WIDTH - camera.getxOffset()),
                                (int)(y * Tile.TILE_HEIGHT - camera.getyOffset()));
                    }
                }
            }
        }
    }
}