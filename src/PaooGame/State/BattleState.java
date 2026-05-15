package PaooGame.State;

import PaooGame.Characters.Entity;
import PaooGame.Characters.MC;
import PaooGame.Characters.LVL2.Bossss;
import PaooGame.Characters.LVL2.mn;
import PaooGame.Characters.LVL3.Bossfinal; // Am importat clasa Boss-ului tau Final
import PaooGame.KeyImput.InputTaste;
import PaooGame.KeyImput.MouseManager;
import PaooGame.KeyImput.UIImageButton;
import PaooGame.Graphics.Assets;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class BattleState extends State {
    private MC player;
    private Entity enemy;
    private InputTaste keyInput;
    private MouseManager mouseManager;
    private State backState;
    private boolean isBossLvl1, isBossLvl2, isBossLvl3; // Am adaugat isBossLvl3

    private enum BattlePhase { PLAYER_TURN, COMPANION_TURN, ENEMY_TURN, WIN, LOSE }
    private enum BattleMenu { MAIN, ATTACK, ITEMS }

    private BattlePhase currentPhase = BattlePhase.PLAYER_TURN;
    private BattleMenu currentMenu = BattleMenu.MAIN;
    private ArrayList<UIImageButton> mainButtons;
    private String battleLog = "Incepe lupta...";
    private int turnTimer = 0;

    public BattleState(InputTaste keyInput, MouseManager mouseManager, MC player, Entity enemy, State backState) {
        this.keyInput = keyInput;
        this.mouseManager = mouseManager;
        this.player = player;
        this.enemy = enemy;
        this.backState = backState;

        this.isBossLvl1 = (enemy instanceof PaooGame.Characters.LVL1.Boss);
        this.isBossLvl2 = (enemy instanceof Bossss);
        this.isBossLvl3 = (enemy instanceof Bossfinal); // Verificam direct daca inamicul este Bossfinal!
        InitButtons();
    }

    private void InitButtons() {
        mainButtons = new ArrayList<>();
        mainButtons.add(new UIImageButton(30, 420, 140, 35, null, () -> { currentMenu = BattleMenu.ATTACK; }));
        mainButtons.add(new UIImageButton(30, 460, 140, 35, null, () -> { battleLog = "Te pregatesti..."; }));
        mainButtons.add(new UIImageButton(30, 500, 140, 35, null, () -> { currentMenu = BattleMenu.ITEMS; }));
    }

    @Override
    public void Update() {
        if (enemy.hp <= 0 && currentPhase != BattlePhase.WIN) {
            enemy.hp = 0;
            currentPhase = BattlePhase.WIN;


            if (isBossLvl3) {
                battleLog = "Campionul lui Emilian a fost nimicit! Joc Terminat! Click pentru Victorie!";
            } else {
                battleLog = (isBossLvl1 || isBossLvl2) ? "Boss infrant! Portalul s-a deschis. Click!" : "Inamic invins! Click!";
            }
        }

        if (currentPhase == BattlePhase.WIN || currentPhase == BattlePhase.LOSE) {
            if (mouseManager.leftPressed) {
                if (currentPhase == BattlePhase.WIN) {
                    if (isBossLvl1) {
                        ((PlayState) backState).goToLevel2();
                        State.SetState(backState);
                    } else if (isBossLvl2) {
                        ((PlayState) backState).goToLevel3();
                        State.SetState(backState);
                    } else if (isBossLvl3) {

                        ((PlayState) backState).triggerWinState();
                    } else {

                        State.SetState(backState);
                    }
                } else {
                    State.SetState(backState);
                }
                mouseManager.leftPressed = false;
                return;
            }
            return;
        }

        if (currentPhase == BattlePhase.PLAYER_TURN) handlePlayerInput();
        else if (currentPhase == BattlePhase.COMPANION_TURN) handleCompanionAI();
        else if (currentPhase == BattlePhase.ENEMY_TURN) handleEnemyAI();
    }

    private void handlePlayerInput() {
        if (currentMenu == BattleMenu.MAIN) {
            for (UIImageButton b : mainButtons) b.Update(mouseManager.mouseX, mouseManager.mouseY, mouseManager.leftPressed);
        } else if (currentMenu == BattleMenu.ATTACK) {
            if (mouseManager.leftPressed) {
                enemy.hp -= 10000; // DMG Jucator
                battleLog = "Ai atacat inamicul!";
                mouseManager.leftPressed = false;
                currentMenu = BattleMenu.MAIN;
                currentPhase = (enemy instanceof mn || isBossLvl2) ? BattlePhase.COMPANION_TURN : BattlePhase.ENEMY_TURN;
            }
        }
    }

    private void handleCompanionAI() {
        turnTimer++;
        if (turnTimer > 40) {
            enemy.hp -= 100; // DMG Companion
            battleLog = "Companionul tau a atacat si el!";
            turnTimer = 0;
            currentPhase = BattlePhase.ENEMY_TURN;
        }
    }

    private void handleEnemyAI() {
        turnTimer++;
        if (turnTimer > 40) {
            player.hp -= 15;
            battleLog = "Inamicul riposteaza!";
            turnTimer = 0;
            currentPhase = BattlePhase.PLAYER_TURN;
        }
    }

    @Override
    public void Draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 800, 600);


        BufferedImage img;
        if (isBossLvl3) {
            img = (Assets.lvl3bosss != null) ? Assets.lvl3bosss : Assets.boss;
        } else if (isBossLvl2) {
            img = Assets.boss_lvl2;
        } else if (isBossLvl1) {
            img = Assets.boss;
        } else {
            img = Assets.minion_lvl2;
        }

        if (img == null) img = Assets.minion_full;
        g.drawImage(img, 280, 50, 240, 320, null);


        g.setColor(new Color(30, 30, 30));
        g.fillRect(10, 400, 780, 190);
        g.setColor(Color.WHITE);
        g.drawRect(10, 400, 780, 190);

        g.drawString(battleLog, 220, 435);
        g.drawString("HP: " + player.hp, 600, 440);
        g.drawString("Enemy HP: " + enemy.hp, 600, 465);

        if (currentPhase == BattlePhase.PLAYER_TURN && currentMenu == BattleMenu.MAIN) {
            String[] labels = {"Ataca", "Apara", "Obiecte"};
            for (int i = 0; i < mainButtons.size(); i++) {
                g.setColor(mainButtons.get(i).isHovering() ? Color.RED : Color.WHITE);
                g.drawString(labels[i], 50, 445 + (i * 40));
            }
        }
    }
}