package PaooGame.Map;

import PaooGame.Tiles.Tile;
import PaooGame.Graphics.GameCamera;
// ##########################################
// SEMN: IMPORTURI NOI PENTRU INAMICI SI ENTITATI
import PaooGame.Characters.Entity;
import PaooGame.Characters.LVL1.Silvani;
import PaooGame.Characters.LVL1.Boss;
import java.util.ArrayList;
// ##########################################
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Map {
    private int width, height;
    private int[][][] layers; // [Strat][Y][X]

    // ##########################################
    // SEMN: LISTA PENTRU STOCAREA INAMICILOR PE HARTĂ
    private ArrayList<Entity> enemies;
    // ##########################################

    public Map(String... paths) {
        this.width = 75;
        this.height = 50;

        // ##########################################
        // SEMN: INITIALIZARE LISTA INAMICI
        enemies = new ArrayList<>();
        // ##########################################

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
                    int id = Integer.parseInt(tokens[x]);

                    // ##########################################
                    // SEMN: LOGICA PENTRU LAYER 3 (INDEX 2) - SPAWNARE INAMICI
                    if (layerIdx == 2 && id > 0) {
                        spawnEntity(id, x, y);
                        // Punem 0 in matrice pe Layer 3 pentru a nu desena un Tile static peste inamic
                        layers[layerIdx][y][x] = 0;
                    } else {
                        layers[layerIdx][y][x] = id;
                    }
                    // ##########################################
                }
            }
            br.close();
        } catch (Exception e) {
            System.err.println("Eroare incarcare strat " + layerIdx + ": " + e.getMessage());
        }
    }

    // ##########################################
    // SEMN: METODA NOUA PENTRU CREAREA OBIECTELOR INAMIC
    private void spawnEntity(int id, int x, int y) {
        int posX = x * Tile.TILE_WIDTH;
        int posY = y * Tile.TILE_HEIGHT;

        if (id == 241) {
            // Spawn Minion Silvani
            enemies.add(new Silvani(posX, posY));
        } else if (id == 244) {
            // Spawn Boss Morbryx
            enemies.add(new Boss(posX, posY));
        }
    }

    // GETTER pentru a putea accesa inamicii din PlayState pentru Update si Draw
    public ArrayList<Entity> getEnemies() {
        return enemies;
    }
    // ##########################################

    public int getTileID(int x, int y, int layer) {
        if (x < 0 || y < 0 || x >= width || y >= height || layer >= layers.length) return 1;
        return layers[layer][y][x];
    }

    public void Draw(Graphics g, GameCamera camera) {
        int xStart = (int) Math.max(0, camera.getxOffset() / Tile.TILE_WIDTH);
        int xEnd = (int) Math.min(width, (camera.getxOffset() + 800) / Tile.TILE_WIDTH + 1);
        int yStart = (int) Math.max(0, camera.getyOffset() / Tile.TILE_HEIGHT);
        int yEnd = (int) Math.min(height, (camera.getyOffset() + 600) / Tile.TILE_HEIGHT + 1);

        // Desenam doar straturile 0 si 1 (Sol si Obiecte statice)
        // Stratul 2 (inamicii) se deseneaza separat prin lista de entitati
        for (int l = 0; l < Math.min(layers.length, 2); l++) {
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