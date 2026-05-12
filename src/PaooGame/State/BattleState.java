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

    // REPARATIE: Referinta catre starea de explorare pentru a nu reseta jocul
    private State backState;

    private enum BattlePhase { PLAYER_TURN, ENEMY_TURN, WIN, LOSE }
    private enum BattleMenu { MAIN, ATTACK, ITEMS, SKILLS }

    private BattlePhase currentPhase = BattlePhase.PLAYER_TURN;
    private BattleMenu currentMenu = BattleMenu.MAIN;

    private ArrayList<UIImageButton> mainButtons;
    private String battleLog = "Incepe lupta...";
    private int enemyAttackTimer = 0;

    // REPARATIE: Constructorul primeste acum si 'backState'
    public BattleState(InputTaste keyInput, MouseManager mouseManager, MC player, Entity enemy, State backState) {
        this.keyInput = keyInput;
        this.mouseManager = mouseManager;
        this.player = player;
        this.enemy = enemy;
        this.backState = backState;
        InitButtons();
    }

    private void InitButtons() {
        mainButtons = new ArrayList<>();
        // Butoanele pentru meniul principal de lupta
        mainButtons.add(new UIImageButton(30, 420, 140, 35, null, () -> { currentMenu = BattleMenu.ATTACK; }));
        mainButtons.add(new UIImageButton(30, 460, 140, 35, null, () -> { currentMenu = BattleMenu.SKILLS; }));
        mainButtons.add(new UIImageButton(30, 500, 140, 35, null, () -> { battleLog = "Te pregatesti de aparare..."; }));
        mainButtons.add(new UIImageButton(30, 540, 140, 35, null, () -> { currentMenu = BattleMenu.ITEMS; }));
    }

    @Override
    public void Update() {
        // --- 1. VERIFICARE CONDITII DE FINAL ---
        // Verificam daca inamicul a murit
        if (enemy.hp <= 0 && currentPhase != BattlePhase.WIN) {
            enemy.hp = 0; // Siguranta pentru afisaj
            currentPhase = BattlePhase.WIN;
            battleLog = "Inamicul a fost rapus! Click pentru a continua.";
        }
        // Verificam daca jucatorul a murit
        if (player.hp <= 0 && currentPhase != BattlePhase.LOSE) {
            player.hp = 0;
            currentPhase = BattlePhase.LOSE;
            battleLog = "Ti-ai pierdut viata in intuneric. Click pentru a iesi.";
        }

        // --- 2. LOGICA DE IESIRE DIN LUPTA ---
        if (currentPhase == BattlePhase.WIN || currentPhase == BattlePhase.LOSE) {
            if (mouseManager.leftPressed) {
                // REPARATIE: Ne intoarcem la PlayState-ul existent, nu cream unul nou!
                State.SetState(backState);
                mouseManager.leftPressed = false;
            }
            return;
        }

        // --- 3. LOGICA TURELOR ---
        if (currentPhase == BattlePhase.PLAYER_TURN) {
            handlePlayerInput();
        } else if (currentPhase == BattlePhase.ENEMY_TURN) {
            handleEnemyAI();
        }
    }

    private void handlePlayerInput() {
        int mX = mouseManager.mouseX;
        int mY = mouseManager.mouseY;
        boolean click = mouseManager.leftPressed;

        if (currentMenu == BattleMenu.MAIN) {
            for (UIImageButton b : mainButtons) b.Update(mX, mY, click);
        } else if (currentMenu == BattleMenu.ATTACK) {
            // Placeholder: Click oriunde pe ecran in meniul de atac pentru a lovi
            if (click) {
                enemy.hp -= 25;
                battleLog = "Ai lovit cu disperare! (-25 HP)";

                mouseManager.leftPressed = false; // Reset click obligatoriu
                currentPhase = BattlePhase.ENEMY_TURN;
                currentMenu = BattleMenu.MAIN;
            }
        }
    }

    private void handleEnemyAI() {
        enemyAttackTimer++;
        if (enemyAttackTimer > 90) { // Pauza de 1.5 secunde
            player.hp -= 20;
            battleLog = "Inamicul te-a sfartecat! (-20 HP)";

            enemyAttackTimer = 0;
            currentPhase = BattlePhase.PLAYER_TURN;
        }
    }

    @Override
    public void Draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, 800, 600);

        // 1. Inamic
        if(Assets.minion_full != null) {
            g2d.drawImage(Assets.minion_full, 280, 50, 240, 320, null);
        }

        // 2. Placeholder MC (Patrat alb)
        g2d.setColor(Color.WHITE);
        g2d.fillRect(100, 250, 80, 150);

        // 3. Caseta UI de jos
        g2d.setColor(new Color(20, 20, 20));
        g2d.fillRect(10, 400, 780, 190);
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(10, 400, 780, 190);

        // 4. Meniu Text
        if (currentMenu == BattleMenu.MAIN) {
            String[] labels = {"Attack", "Skills", "Guard", "Items"};
            for (int i = 0; i < mainButtons.size(); i++) {
                boolean isHover = mainButtons.get(i).isHovering();
                g2d.setColor(isHover ? Color.RED : Color.WHITE);
                g2d.setFont(new Font("Arial", Font.BOLD, 20));
                g2d.drawString(labels[i], 50, 445 + (i * 40));
            }
        }

        // 5. Battle Log (Mesaje despre ce se intampla)
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.setFont(new Font("Monospaced", Font.PLAIN, 14));
        g2d.drawString(battleLog, 220, 430);

        // 6. Stats MC (HP-ul tau)
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString("MC HP: " + player.hp, 550, 445);
        g2d.drawString("MC Mind: 100", 550, 475);
    }
}