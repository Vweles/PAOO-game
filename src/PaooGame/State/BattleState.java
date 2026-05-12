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
    private State backState;
    private boolean isBossBattle;

    private enum BattlePhase { PLAYER_TURN, ENEMY_TURN, WIN, LOSE }
    private enum BattleMenu { MAIN, ATTACK, ITEMS, SKILLS }

    private BattlePhase currentPhase = BattlePhase.PLAYER_TURN;
    private BattleMenu currentMenu = BattleMenu.MAIN;
    private ArrayList<UIImageButton> mainButtons;
    private String battleLog = "Incepe lupta...";
    private int enemyAttackTimer = 0;

    public BattleState(InputTaste keyInput, MouseManager mouseManager, MC player, Entity enemy, State backState) {
        this.keyInput = keyInput;
        this.mouseManager = mouseManager;
        this.player = player;
        this.enemy = enemy;
        this.backState = backState;

        // Verificam daca inamicul este instanta de Boss
        this.isBossBattle = (enemy instanceof PaooGame.Characters.LVL1.Boss);
        InitButtons();
    }

    private void InitButtons() {
        mainButtons = new ArrayList<>();
        mainButtons.add(new UIImageButton(30, 420, 140, 35, null, () -> { currentMenu = BattleMenu.ATTACK; }));
        mainButtons.add(new UIImageButton(30, 460, 140, 35, null, () -> { currentMenu = BattleMenu.SKILLS; }));
        mainButtons.add(new UIImageButton(30, 500, 140, 35, null, () -> { battleLog = "Te pregatesti de aparare..."; }));
        mainButtons.add(new UIImageButton(30, 540, 140, 35, null, () -> { currentMenu = BattleMenu.ITEMS; }));
    }

    @Override
    public void Update() {
        if (enemy.hp <= 0 && currentPhase != BattlePhase.WIN) {
            enemy.hp = 0;
            currentPhase = BattlePhase.WIN;
            battleLog = isBossBattle ? "Morbryx a fost infrant! Portalul s-a deschis. Click!" : "Inamic invins! Click!";
        }

        if (player.hp <= 0 && currentPhase != BattlePhase.LOSE) {
            player.hp = 0;
            currentPhase = BattlePhase.LOSE;
            battleLog = "Ai pierdut... Click pentru a iesi.";
        }

        if (currentPhase == BattlePhase.WIN || currentPhase == BattlePhase.LOSE) {
            if (mouseManager.leftPressed) {
                // REPARATIE TELEPORTARE: Daca e boss, apelam functia din PlayState
                if (currentPhase == BattlePhase.WIN && isBossBattle) {
                    if (backState instanceof PlayState) {
                        ((PlayState) backState).goToLevel2();
                    }
                }
                State.SetState(backState);
                mouseManager.leftPressed = false;
            }
            return;
        }

        if (currentPhase == BattlePhase.PLAYER_TURN) {
            handlePlayerInput();
        } else if (currentPhase == BattlePhase.ENEMY_TURN) {
            handleEnemyAI();
        }
    }

    private void handlePlayerInput() {
        if (currentMenu == BattleMenu.MAIN) {
            for (UIImageButton b : mainButtons) b.Update(mouseManager.mouseX, mouseManager.mouseY, mouseManager.leftPressed);
        } else if (currentMenu == BattleMenu.ATTACK) {
            if (mouseManager.leftPressed) {
                enemy.hp -= 500; // Pentru test, il omori dintr-o lovitura
                battleLog = "Ai lovit decisiv!";
                mouseManager.leftPressed = false;
                currentPhase = BattlePhase.ENEMY_TURN;
                currentMenu = BattleMenu.MAIN;
            }
        }
    }

    private void handleEnemyAI() {
        enemyAttackTimer++;
        if (enemyAttackTimer > 60) {
            player.hp -= 10;
            enemyAttackTimer = 0;
            currentPhase = BattlePhase.PLAYER_TURN;
        }
    }

    @Override
    public void Draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, 800, 600);

        // REPARATIE IMAGINE: Alegem ce desenam in functie de isBossBattle
        if (isBossBattle) {
            if (Assets.boss != null) {
                g2d.drawImage(Assets.boss, 200, 30, 400, 350, null);
            }
        } else {
            if (Assets.minion_full != null) {
                g2d.drawImage(Assets.minion_full, 280, 50, 240, 320, null);
            }
        }

        // UI de baza
        g2d.setColor(new Color(20, 20, 20));
        g2d.fillRect(10, 400, 780, 190);
        g2d.setColor(Color.WHITE);
        g2d.drawRect(10, 400, 780, 190);

        if (currentMenu == BattleMenu.MAIN) {
            String[] labels = {"Attack", "Skills", "Guard", "Items"};
            for (int i = 0; i < mainButtons.size(); i++) {
                g2d.setColor(mainButtons.get(i).isHovering() ? Color.RED : Color.WHITE);
                g2d.drawString(labels[i], 50, 445 + (i * 40));
            }
        }
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.drawString(battleLog, 220, 430);
        g2d.setColor(Color.WHITE);
        g2d.drawString("HP: " + player.hp, 550, 445);
        g2d.drawString("Enemy HP: " + enemy.hp, 550, 470);
    }
}