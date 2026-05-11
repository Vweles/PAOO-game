package PaooGame.State;

import PaooGame.Characters.Entity;
import PaooGame.Characters.MC;
import PaooGame.KeyImput.InputTaste;
import PaooGame.KeyImput.MouseManager;
import PaooGame.KeyImput.UIImageButton;
import PaooGame.Graphics.Assets;
import java.awt.*;
import java.util.ArrayList;

public class BattleState extends State {
    private MC player;
    private Entity enemy;
    private InputTaste keyInput;
    private MouseManager mouseManager;

    private enum BattleMenu { MAIN, ATTACK, ITEMS }
    private BattleMenu currentMenu = BattleMenu.MAIN;

    private ArrayList<UIImageButton> mainButtons;
    private ArrayList<UIImageButton> itemButtons;

    private boolean identifyActive = false;
    private boolean armSevered = false; // LOGICĂ PENTRU PNG-UL TĂU

    private Rectangle[] limbHitboxes;
    private String[] limbNames = {"CAP", "CORP", "MANA STÂNGA", "MANA DREAPTĂ"};

    public BattleState(InputTaste keyInput, MouseManager mouseManager, MC player, Entity enemy) {
        this.keyInput = keyInput;
        this.mouseManager = mouseManager;
        this.player = player;
        this.enemy = enemy;

        // Hitbox-uri ajustate aproximativ pentru un PNG inamic central
        limbHitboxes = new Rectangle[4];
        limbHitboxes[0] = new Rectangle(370, 150, 60, 60);  // Cap
        limbHitboxes[1] = new Rectangle(360, 220, 80, 120); // Corp
        limbHitboxes[2] = new Rectangle(310, 220, 40, 100); // Mana Stanga
        limbHitboxes[3] = new Rectangle(450, 220, 40, 100); // Mana Dreapta

        InitButtons();
    }

    private void InitButtons() {
        mainButtons = new ArrayList<>();
        itemButtons = new ArrayList<>();

        mainButtons.add(new UIImageButton(50, 420, 150, 40, Assets.btn_attack, () -> { currentMenu = BattleMenu.ATTACK; }));
        mainButtons.add(new UIImageButton(50, 470, 150, 40, Assets.btn_items, () -> { currentMenu = BattleMenu.ITEMS; }));
        mainButtons.add(new UIImageButton(50, 520, 150, 40, Assets.btn_identify, () -> { identifyActive = true; }));

        itemButtons.add(new UIImageButton(250, 450, 150, 50, Assets.btn_heal_hp, () -> {
            player.hp += 50;
            currentMenu = BattleMenu.MAIN;
        }));
    }

    @Override
    public void Update() {
        int mX = mouseManager.mouseX;
        int mY = mouseManager.mouseY;
        boolean click = mouseManager.leftPressed;

        if (currentMenu == BattleMenu.MAIN) {
            for (UIImageButton b : mainButtons) b.Update(mX, mY, click);
        } else if (currentMenu == BattleMenu.ATTACK) {
            if (click) {
                for (int i = 0; i < limbHitboxes.length; i++) {
                    if (limbHitboxes[i].contains(mX, mY)) {
                        attackLimb(i);
                        currentMenu = BattleMenu.MAIN;
                        mouseManager.leftPressed = false;
                        break;
                    }
                }
            }
        }

        if (enemy.hp <= 0) {
            // REPARAT: Trimitem 4 argumente către PlayState
            State.SetState(new PlayState(keyInput, mouseManager, 800, 600));
        }
    }

    private void attackLimb(int limbIndex) {
        int damage = 25;
        // Dacă atacăm mâna stângă (index 2), o "tăiem"
        if (limbIndex == 2) {
            armSevered = true;
            damage += 40;
            System.out.println("I-ai taiat mana stanga!");
        }
        enemy.hp -= damage;
    }

    @Override
    public void Draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 800, 600);

        // --- SCHIMBARE PNG ÎN FUNCȚIE DE STARE ---
        if (armSevered) {
            g.drawImage(Assets.minion_no_arm, 300, 150, 200, 250, null);
        } else {
            g.drawImage(Assets.minion_full, 300, 150, 200, 250, null);
        }

        // Vizualizare Identify
        if (identifyActive && currentMenu == BattleMenu.ATTACK) {
            for (int i = 0; i < limbHitboxes.length; i++) {
                g.setColor(new Color(255, 0, 0, 100));
                g.fillRect(limbHitboxes[i].x, limbHitboxes[i].y, limbHitboxes[i].width, limbHitboxes[i].height);
                g.setColor(Color.WHITE);
                g.drawRect(limbHitboxes[i].x, limbHitboxes[i].y, limbHitboxes[i].width, limbHitboxes[i].height);
                g.drawString(limbNames[i], limbHitboxes[i].x, limbHitboxes[i].y - 5);
            }
        }

        // UI
        g.setColor(Color.WHITE);
        g.drawRect(20, 400, 760, 180);
        if (currentMenu == BattleMenu.MAIN) {
            for (UIImageButton b : mainButtons) b.Draw(g);
        } else {
            g.drawString("CLICK PE CORP PENTRU ATAC", 300, 450);
        }

        g.drawString("ENEMY HP: " + enemy.hp, 500, 50);
    }
}