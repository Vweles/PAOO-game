package PaooGame.State;

import PaooGame.Characters.Entity;
import PaooGame.Characters.LVL2.Comp;
import PaooGame.Characters.MC;
import PaooGame.Items.Iteme;
import PaooGame.Items.ItemGenerator;
import PaooGame.Map.Map;
import PaooGame.Characters.MC;
import PaooGame.KeyImput.InputTaste;
import PaooGame.KeyImput.MouseManager;
import PaooGame.Graphics.GameCamera;
import PaooGame.Physics.CollisionChecker;
import PaooGame.Graphics.HUD;
import java.awt.Graphics;
import PaooGame.Graphics.Assets;
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

    // 1. ADĂUGĂ aceastâ variabilâ membrâ pentru a păstra referința
    private InputTaste keyInput;
    private int width, height;
    private Random random = new Random();
    private int currentLevelNo = 1;

    public PlayState(InputTaste keyInput, int width, int height) {
        // 2. SALVEAZĂ referința primită în variabila clasei
        this.keyInput = keyInput;

        camera = new GameCamera(width, height);
    public PlayState(InputTaste keyInput, MouseManager mouseManager, int width, int height) {
        this.keyInput = keyInput;
        this.mouseManager = mouseManager;
        this.width = width;
        this.height = height;
        this.camera = new GameCamera(width, height);
        currentMap = new Map("/LVL1/layout1final.txt", "/LVL1/laye2final.txt", "/LVL1/layer3Lvl1.txt");
        this.checker = new CollisionChecker(currentMap);
        this.currentLevelNo = 1;
        player = new MC("Erou", 100, 100, keyInput);
        State.SetState(new ObiectivState(this, keyInput, mouseManager, currentLevelNo));
    }

    public InputTaste getKeyInput() {
        return keyInput;
    }

        checker = new CollisionChecker(currentMap);

        // Folosești parametrul sau variabila salvată, ambele funcționează aici
        player = new MC("Erou", 200, 200, keyInput);
        hud = new HUD(player);
    public MC getPlayer() {
        return player;
    }

    @Override
    public void Update() {
        // REPARAT: Am schimbat din Update(...) in update(...) cu 'u' mic
        player.update(checker);

        if (player.hp <= 0) {
            State.SetState(new LoseState(this, keyInput, mouseManager));
            return;
        }

        if(currentMap != null) {
            camera.centerOnEntity(player);
            camera.checkBlankSpace(currentMap.getWidth() * Tile.TILE_WIDTH, currentMap.getHeight() * Tile.TILE_HEIGHT);
        }

        if (companion != null) companion.update(checker);

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

        if (keyInput.o_tasta) {
            keyInput.up = false; keyInput.down = false; keyInput.left = false; keyInput.right = false;
            keyInput.o_tasta = false;
            State.SetState(new ObiectivState(this, keyInput, mouseManager, currentLevelNo));
            return;
        }

        handlePotions();

        if(currentMap != null) {
            ArrayList<Entity> enemies = currentMap.getEnemies();
            if (enemies != null) {
                Rectangle pRect = new Rectangle((int)player.x + player.solidArea.x, (int)player.y + player.solidArea.y, player.solidArea.width, player.solidArea.height);

                for (int i = 0; i < enemies.size(); i++) {
                    Entity e = enemies.get(i);
                    e.update(checker);
                    Rectangle eRect = new Rectangle((int)e.x + e.solidArea.x, (int)e.y + e.solidArea.y, e.solidArea.width, e.solidArea.height);

                    if (pRect.intersects(eRect)) {
                        generateLootDrop();
                        keyInput.up = false; keyInput.down = false; keyInput.left = false; keyInput.right = false;
                        enemies.remove(i);
                        State.SetState(new BattleState(keyInput, mouseManager, player, e, this));
                        return;
                    }
                }
            }
        }
    }

    private void handlePotions() {
        if (keyInput.h_pot) {
            Iteme potiune = findSpecificPotion(Iteme.ItemType.POTION_HP);
            if (potiune != null) {
                player.hp += potiune.bonusStr;
                int maxHP = 100 + 10 * player.tho + 5 * player.str;
                if (player.hp > maxHP) player.hp = maxHP;
                player.getInventory().bag.remove(potiune);
                System.out.println("Consumat: " + potiune.name + ". HP actual: " + player.hp);
            } else {
                System.out.println("Nu mai ai potiuni de HP!");
            }
            keyInput.h_pot = false;
        }
        if (keyInput.m_pot) {
            Iteme potiune = findSpecificPotion(Iteme.ItemType.POTION_MANA);
            if (potiune != null) {
                player.mana += potiune.bonusStr;
                int maxMana = 150 + 15 * player.inte;
                if (player.mana > maxMana) player.mana = maxMana;
                player.getInventory().bag.remove(potiune);
                System.out.println("Consumat: " + potiune.name + ". Mana actuala: " + player.mana);
            } else {
                System.out.println("Nu mai ai potiuni de Mana!");
            }
            keyInput.m_pot = false;
        }
    }

    private Iteme findSpecificPotion(Iteme.ItemType type) {
        for (Iteme i : player.getInventory().bag) {
            if (i.type == type && i.name.contains("Mica")) return i;
        }
        for (Iteme i : player.getInventory().bag) {
            if (i.type == type && i.name.contains("Medie")) return i;
        }
        for (Iteme i : player.getInventory().bag) {
            if (i.type == type && i.name.contains("Mare")) return i;
        }
        return null;
    }

    private Iteme findItemInBag(Iteme.ItemType type) {
        for (Iteme i : player.getInventory().bag) {
            if (i.type == type) return i;
        }
        return null;
    }

    private void generateLootDrop() {
        Iteme.ItemType[] dropTypes = {
                Iteme.ItemType.HEAD, Iteme.ItemType.CHEST, Iteme.ItemType.HANDS,
                Iteme.ItemType.BOOTS, Iteme.ItemType.CLOAK, Iteme.ItemType.RING
        };
        Iteme.ItemType selectedEquipType = dropTypes[random.nextInt(dropTypes.length)];
        Iteme dropEquip = ItemGenerator.generateRandomItem(selectedEquipType);
        player.getInventory().bag.add(dropEquip);
        System.out.println("[DROP] Ai obtinut echipament: " + dropEquip.name);
        Iteme.ItemType selectedPotionType = random.nextBoolean() ? Iteme.ItemType.POTION_HP : Iteme.ItemType.POTION_MANA;
        Iteme dropPotion = ItemGenerator.generateRandomItem(selectedPotionType);
        player.getInventory().bag.add(dropPotion);
        System.out.println("[DROP EXTRA] Inamicul a lasat si o potiune: " + dropPotion.name);
    }

    public MouseManager getMouseManager() {
        return mouseManager;
        camera.centerOnEntity(player);

        // 3. ACUM poți folosi keyInput aici pentru a schimba starea
        if (player.hp <= 0) {
            State.SetState(new GameOverState(keyInput));
        }
    }

    @Override
    public void Draw(Graphics g) {
        currentMap.Draw(g, camera);
        player.Draw(g, (int)camera.getxOffset(), (int)camera.getyOffset());
        hud.draw(g);
        if (currentMap == null) {
            drawPlaceholder(g);
        } else {
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
    }

    private void drawPlaceholder(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("ZONA NEINCARCATA", 250, 300);
        player.draw(g, 0, 0);
    }

    private void drawCircularFog(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int screenX = (int)(player.x + 16 - camera.getxOffset());
        int screenY = (int)(player.y + 16 - camera.getyOffset());
        RadialGradientPaint p = new RadialGradientPaint(
                Math.max(1, screenX), Math.max(1, screenY), 280f,
                new float[]{0.0f, 0.7f, 1.0f},
                new Color[]{ new Color(0,0,0,0), new Color(0,0,0,180), Color.BLACK }
        );
        g2d.setPaint(p);
        g2d.fillRect(0, 0, width, height);
    }

    public void goToLevel2() {
        Assets.InitLevel2();
        Tile.InitLevel2Tiles();
        this.currentMap = new Map("/LVL2/layer1_formatat.txt", "/LVL2/layer2_formatat.txt", "/LVL2/layer3_formatat.txt");
        this.checker = new CollisionChecker(currentMap);
        this.player.x = 200;
        this.player.y = 200;
        this.companion = new Comp("Guardian", 250, 250, player);
        this.currentLevelNo = 2;
        State.SetState(new ObiectivState(this, keyInput, mouseManager, currentLevelNo));
    }

    public void goToLevel3() {
        Assets.InitLevel3();
        Tile.InitLevel3Tiles();
        this.currentMap = new Map("/LVL3/lvl3final_layer1.txt", "/LVL3/lvl3final_layer2.txt", "/LVL3/lvl3final_layer3.txt");
        this.checker = new CollisionChecker(currentMap);
        this.player.x = 100;
        this.player.y = 100;
        this.companion = null;
        this.currentLevelNo = 3;
        State.SetState(new ObiectivState(this, keyInput, mouseManager, currentLevelNo));
    }

    public void triggerWinState() {
        State.SetState(new WinState(this, mouseManager));
    }

    public int getCurrentLevelNo() {
        return currentLevelNo;
    }

    public void loadGameData(int hp, float x, float y, int level) {
        this.currentLevelNo = level;
        if(level == 1) {
            this.currentMap = new Map("/LVL1/layout1final.txt", "/LVL1/laye2final.txt", "/LVL1/layer3Lvl1.txt");
            this.companion = null;
        }
        if(level == 2) goToLevel2();
        if(level == 3) goToLevel3();
        this.checker = new CollisionChecker(currentMap);
        this.player.hp = hp;
        this.player.setX((int)x);
        this.player.setY((int)y);
        State.SetState(new ObiectivState(this, keyInput, mouseManager, currentLevelNo));
    }

    public Map getCurrentMap() { return currentMap; }
}