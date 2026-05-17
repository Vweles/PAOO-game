package PaooGame.Map;

import PaooGame.Characters.LVL3.Bossfinal;
import PaooGame.Characters.LVL3.Minionlvl3;
import PaooGame.Tiles.Tile;
import PaooGame.Graphics.GameCamera;
import PaooGame.Characters.Entity;
import PaooGame.Characters.LVL1.Silvani;
import PaooGame.Characters.LVL1.Boss;
import PaooGame.Characters.LVL2.mn;
import PaooGame.Characters.LVL2.Bossss;
import java.util.ArrayList;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Map {
    private int width, height;
    private int[][][] layers;
    private ArrayList<Entity> enemies;

    public Map(String... paths) {
        enemies = new ArrayList<>();
        determineDimensions(paths[0]);
        layers = new int[paths.length][height][width];
        for(int i = 0; i < paths.length; i++) {
            loadLayer(paths[i], i);
        }
        Tile.InitCustomTiles();
    }

    private void determineDimensions(String path) {
        try {
            InputStream is = getClass().getResourceAsStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int h = 0, w = 0;
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.trim().split("\\s+");
                if(tokens.length > w) w = tokens.length;
                h++;
            }
            this.width = w;
            this.height = h;
            br.close();
        } catch (Exception e) {
            this.width = 75; this.height = 50;
        }
    }

    private void loadLayer(String path, int layerIdx) {
        try {
            InputStream is = getClass().getResourceAsStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            for (int y = 0; y < height; y++) {
                String line = br.readLine();
                if(line == null) break;
                String[] tokens = line.trim().split("\\s+");
                for (int x = 0; x < width; x++) {
                    if (x < tokens.length) {
                        int id = Integer.parseInt(tokens[x]);
                        if (layerIdx == 2 && id > 0) {
                            spawnEntity(id, x, y);
                            layers[layerIdx][y][x] = 0;
                        } else {
                            layers[layerIdx][y][x] = id;
                        }
                    }
                }
            }
            br.close();
        } catch (Exception e) { System.err.println("Eroare la: " + path); }
    }

    private void spawnEntity(int id, int x, int y) {
        int posX = x * Tile.TILE_WIDTH;
        int posY = y * Tile.TILE_HEIGHT;
        if (id == 241) enemies.add(new Silvani(posX, posY));
        else if (id == 244) enemies.add(new Boss(posX, posY));
        else if (id == 741) enemies.add(new mn(posX, posY));
        else if (id == 773) enemies.add(new Bossss(posX, posY));
        else if (id == 98) enemies.add(new Minionlvl3(posX, posY));
        else if (id == 267) enemies.add(new Bossfinal(posX, posY));

    }

    public int getTileID(int x, int y, int layer) {
        if (x < 0 || y < 0 || x >= width || y >= height) return 13;
        if (layer >= layers.length) return 0;
        return layers[layer][y][x];
    }

    public void Draw(Graphics g, GameCamera camera) {

        int xStart = (int) Math.max(0, camera.getxOffset() / Tile.TILE_WIDTH);
        int xEnd = (int) Math.min(width, (camera.getxOffset() + 800) / Tile.TILE_WIDTH + 1);
        int yStart = (int) Math.max(0, camera.getyOffset() / Tile.TILE_HEIGHT);
        int yEnd = (int) Math.min(height, (camera.getyOffset() + 600) / Tile.TILE_HEIGHT + 1);

        for (int l = 0; l < Math.min(layers.length, 2); l++) {
            for (int y = yStart; y < yEnd; y++) {
                for (int x = xStart; x < xEnd; x++) {
                    int id = layers[l][y][x];
                    if (id > 0 && Tile.tiles[id] != null) {
                        Tile.tiles[id].Draw(g,
                                (int)(x * Tile.TILE_WIDTH - camera.getxOffset()),
                                (int)(y * Tile.TILE_HEIGHT - camera.getyOffset()));
                    }
                }
            }
        }
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public ArrayList<Entity> getEnemies() { return enemies; }
}