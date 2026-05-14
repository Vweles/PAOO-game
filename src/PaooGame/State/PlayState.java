package PaooGame.State;

import PaooGame.Characters.Entity;
import PaooGame.Characters.LVL2.Comp;
import PaooGame.Characters.MC;
import PaooGame.Map.Map;
import PaooGame.KeyImput.InputTaste;
import PaooGame.KeyImput.MouseManager;
import PaooGame.Graphics.GameCamera;
import PaooGame.Physics.CollisionChecker;
import PaooGame.Graphics.Assets;
import PaooGame.Tiles.Tile;
import java.awt.*;
import java.util.ArrayList;

public class PlayState extends State {
    private MC player;
    private Map currentMap;
    private Comp companion;
    private InputTaste keyInput;
    private MouseManager mouseManager;
    private GameCamera camera;
    private CollisionChecker checker;
    private int width, height;

    public PlayState(InputTaste keyInput, MouseManager mouseManager, int width, int height) {
        this.keyInput = keyInput;
        this.mouseManager = mouseManager;
        this.width = width;
        this.height = height;
        this.camera = new GameCamera(width, height);

        currentMap = new Map("/LVL1/layout1final.txt", "/LVL1/laye2final.txt", "/LVL1/layer3Lvl1.txt");
        this.checker = new CollisionChecker(currentMap);
        player = new MC("Erou", 100, 100, keyInput);
    }

    @Override
    public void Update() {
        player.update(checker);
        camera.centerOnEntity(player);
        camera.checkBlankSpace(currentMap.getWidth() * Tile.TILE_WIDTH, currentMap.getHeight() * Tile.TILE_HEIGHT);

        if (companion != null) companion.update(checker);

        ArrayList<Entity> enemies = currentMap.getEnemies();
        if (enemies != null) {
            Rectangle pRect = new Rectangle((int)player.x + player.solidArea.x, (int)player.y + player.solidArea.y, player.solidArea.width, player.solidArea.height);
            for (int i = 0; i < enemies.size(); i++) {
                Entity e = enemies.get(i);
                e.update(checker);
                Rectangle eRect = new Rectangle((int)e.x + e.solidArea.x, (int)e.y + e.solidArea.y, e.solidArea.width, e.solidArea.height);
                if (pRect.intersects(eRect)) {
                    enemies.remove(i);
                    keyInput.up = false; keyInput.down = false; keyInput.left = false; keyInput.right = false;
                    State.SetState(new BattleState(keyInput, mouseManager, player, e, this));
                    return;
                }
            }
        }
    }

    @Override
    public void Draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);

        int xOff = (int)camera.getxOffset();
        int yOff = (int)camera.getyOffset();

        currentMap.Draw(g, camera);
        if (companion != null) companion.draw(g, xOff, yOff);

        ArrayList<Entity> enemies = currentMap.getEnemies();
        if (enemies != null) {
            for (Entity e : enemies) e.draw(g, xOff, yOff);
        }
        player.draw(g, xOff, yOff);
        drawCircularFog(g);
    }

    private void drawCircularFog(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int screenX = (int)(player.x + 16 - camera.getxOffset());
        int screenY = (int)(player.y + 16 - camera.getyOffset());

        int fx = Math.max(0, Math.min(width, screenX));
        int fy = Math.max(0, Math.min(height, screenY));

        RadialGradientPaint p = new RadialGradientPaint(fx, fy, 280f, new float[]{0.0f, 0.7f, 1.0f},
                new Color[]{ new Color(0,0,0,0), new Color(0,0,0,180), Color.BLACK });
        g2d.setPaint(p);
        g2d.fillRect(0, 0, width, height);
    }

    public void goToLevel2() {
        System.out.println("Incarcare Nivel 2...");
        // ORDINEA CORECTA:
        Assets.InitLevel2();      // 1. Incarca imaginea mare
        Tile.InitLevel2Tiles();   // 2. Taie dalele si pune-le in array-ul tiles[]

        // 3. Incarca harta (care acum va gasi dalele in array)
        this.currentMap = new Map("/LVL2/layer1_formatat.txt", "/LVL2/layer2_formatat.txt", "/LVL2/layer3_formatat.txt");
        this.checker = new CollisionChecker(currentMap);

        this.player.x = 200;
        this.player.y = 200;
        this.companion = new Comp("Guardian", 250, 250, player);
    }

    public Map getCurrentMap() { return currentMap; }
}