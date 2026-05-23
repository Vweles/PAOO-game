package PaooGame.State;

import PaooGame.Characters.Entity;
import PaooGame.Characters.LVL2.Comp;
import PaooGame.Characters.MC;
import PaooGame.Database.DatabaseManager;
import PaooGame.Items.Iteme;
import PaooGame.Managers.CombatManager;
import PaooGame.Managers.LevelManager;
import PaooGame.Map.Map;
import PaooGame.KeyImput.InputTaste;
import PaooGame.KeyImput.MouseManager;
import PaooGame.Graphics.GameCamera;
import PaooGame.Physics.CollisionChecker;
import PaooGame.Graphics.HUD;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import PaooGame.Tiles.Tile;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class PlayState extends State {
    private MC player;
    private Map currentMap;
    private Comp companion;
    private InputTaste keyInput;
    private MouseManager mouseManager;
    private GameCamera camera;
    private CollisionChecker checker;
    private HUD hud;
    private CombatManager combatManager;
    private LevelManager levelManager;

    private int width, height;
    private Random random = new Random();
    private int currentLevelNo = 1;

    public PlayState(InputTaste keyInput, MouseManager mouseManager, int width, int height) {
        this.keyInput = keyInput;
        this.mouseManager = mouseManager;
        this.width = width;
        this.height = height;

        this.camera = new GameCamera(width, height);
        this.levelManager = new LevelManager(this);

        this.player = new MC("Erou", 200, 200, keyInput);
        this.hud = new HUD(player);
        this.combatManager = new CombatManager(this);

        this.currentLevelNo = 1;
        levelManager.loadLevel(currentLevelNo);

        State.SetState(new ObiectivState(this, keyInput, mouseManager, currentLevelNo));
    }


    public InputTaste getKeyInput() { return keyInput; }
    public MC getPlayer() { return player; }
    public Map getCurrentMap() { return currentMap; }
    public void setCurrentMap(Map map) { this.currentMap = map; this.checker = new CollisionChecker(map); }
    public void setCompanion(Comp comp) { this.companion = comp; }

    @Override
    public void Update() {
        if (currentMap != null) {
            currentMap.updateEnemies(checker);
        }

        player.update(checker);
        if (companion != null) {
            companion.update(checker);
        }

        if (currentMap != null) {
            camera.centerOnEntity(player);
            camera.checkBlankSpace(currentMap.getWidth() * Tile.TILE_WIDTH,
                    currentMap.getHeight() * Tile.TILE_HEIGHT);
        }

        if (player.getHp() <= 0) {
            State.SetState(new LoseState(this, keyInput, mouseManager));
            return;
        }

        if (keyInput.esc) {
            State.SetState(new PauseState(this, keyInput, mouseManager));
            keyInput.esc = false;
            return;
        }

        if (keyInput.inv) {
            State.SetState(new InventoryState(this));
            keyInput.inv = false;
            return;
        }

        handlePotions();

        if (currentMap != null) {
            combatManager.checkAndTriggerCombat(player, currentMap.getEnemies());
        }
    }

    @Override
    public void Draw(Graphics g) {

        if (currentMap != null) {
            currentMap.draw(g, camera);
        }

        if (player != null) {
            player.draw(g, (int)camera.getxOffset(), (int)camera.getyOffset());
        }

        if (companion != null) {
            companion.draw(g, (int)camera.getxOffset(), (int)camera.getyOffset());
        }


        drawCircularFog(g);

        if (hud != null) {
            hud.draw(g);
        }
    }

    private void handlePotions() {

        if (keyInput.h_pot) {
            Iteme potiune = findSpecificPotion(Iteme.ItemType.POTION_HP);
            if (potiune != null) {
                player.setHp(player.getHp() + potiune.bonusStr);
                int maxHP = 100 + 10 * player.getTho() + 5 * player.getStr();
                if (player.getHp() > maxHP) player.setHp(maxHP);
                player.getInventory().bag.remove(potiune);
            }
            keyInput.h_pot = false;
        }


        if (keyInput.m_pot) {
            Iteme potiune = findSpecificPotion(Iteme.ItemType.POTION_MANA);
            if (potiune != null) {

                player.setMana(player.getMana() + potiune.bonusStr);

                int maxMana = 150 + 15 * player.getInte();

                if (player.getMana() > maxMana) {
                    player.setMana(maxMana);
                }

                player.getInventory().bag.remove(potiune);
                System.out.println("Consumat: " + potiune.name + ". Mana actuala: " + player.getMana());
            } else {
                System.out.println("Nu mai ai potiuni de Mana!");
            }
            keyInput.m_pot = false;
        }
    }


    public void goToLevel2() {
        this.currentLevelNo = 2;
        levelManager.loadLevel(currentLevelNo);
        this.player.setX(200);
        this.player.setY(200);
        State.SetState(new ObiectivState(this, keyInput, mouseManager, currentLevelNo));
    }

    public void goToLevel3() {
        this.currentLevelNo = 3;
        levelManager.loadLevel(currentLevelNo);
        this.player.setX(100);
        this.player.setY(100);
        State.SetState(new ObiectivState(this, keyInput, mouseManager, currentLevelNo));
    }

    public void loadGameData(int hp, int mana, float x, float y, int level) {
        this.currentLevelNo = level;
        levelManager.loadLevel(level);
        this.player.setHp(hp);
        this.player.setMana(mana);
        this.player.setX((int)x);
        this.player.setY((int)y);
        State.SetState(new ObiectivState(this, keyInput, mouseManager, currentLevelNo));
    }

    public MouseManager getMouseManager() {
        return mouseManager;
    }

    public int getCurrentLevelNo() { return currentLevelNo; }

    public void triggerWinState() { State.SetState(new WinState(this, mouseManager)); }

    private Iteme findSpecificPotion(Iteme.ItemType type) {

        return null;
    }


    private void drawCircularFog(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;


        int screenX = (int)(player.getX() + 16 - camera.getxOffset());
        int screenY = (int)(player.getY() + 16 - camera.getyOffset());


        RadialGradientPaint p = new RadialGradientPaint(
                Math.max(1, screenX), Math.max(1, screenY), 280f,
                new float[]{0.0f, 0.7f, 1.0f},
                new Color[]{ new Color(0,0,0,0), new Color(0,0,0,180), Color.BLACK }
        );

        g2d.setPaint(p);
        g2d.fillRect(0, 0, width, height);
    }
}