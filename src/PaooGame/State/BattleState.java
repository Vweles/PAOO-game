package PaooGame.State;

import PaooGame.Characters.Entity;
import PaooGame.Characters.MC;
import PaooGame.Characters.LVL1.Silvani;
import PaooGame.Characters.LVL1.Boss;
import PaooGame.Characters.LVL2.Bossss;
import PaooGame.Characters.LVL3.Bossfinal;
import PaooGame.KeyImput.InputTaste;
import PaooGame.KeyImput.MouseManager;
import PaooGame.KeyImput.UIImageButton;
import PaooGame.Graphics.Assets;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class BattleState extends State {
    private PaooGame.Graphics.HUD hud;
    private MC player;
    private Entity enemy;
    private InputTaste keyInput;
    private MouseManager mouseManager;
    private State backState;
    private Random rand = new Random();
    private boolean isBossLvl1, isBossLvl2, isBossLvl3;

    private enum BattlePhase { DETERMINING_INITIATIVE, PLAYER_TURN, COMPANION_TURN, ENEMY_TURN, WIN, LOSE }
    private enum BattleMenu { MAIN, ATTACK, SPELLS }

    private BattlePhase currentPhase = BattlePhase.DETERMINING_INITIATIVE;
    private BattleMenu currentMenu = BattleMenu.MAIN;
    private ArrayList<UIImageButton> mainButtons;
    private ArrayList<UIImageButton> spellButtons;
    private String battleLog = "Incepe lupta...";
    private int turnTimer = 0;
    private boolean isPlayerDefending = false;

    public BattleState(InputTaste keyInput, MouseManager mouseManager, MC player, Entity enemy, State backState) {
        this.keyInput = keyInput;
        this.mouseManager = mouseManager;
        this.player = player;
        this.enemy = enemy;
        this.backState = backState;
        this.isBossLvl1 = (enemy instanceof Boss);
        this.isBossLvl2 = (enemy instanceof Bossss);
        this.isBossLvl3 = (enemy instanceof Bossfinal);

        player.updateStatsAfterInjury();
        enemy.updateStatsAfterInjury();

        this.hud = new PaooGame.Graphics.HUD(player);

        InitButtons();
        determineFirstTurn();
    }

    private void determineFirstTurn() {
        if (player.getAgi() >= enemy.getAgi()) {
            currentPhase = BattlePhase.PLAYER_TURN;
            battleLog = player.getNume() + " are agilitate mai mare (" + player.getAgi() + ") si incepe primul!";
        } else {
            currentPhase = BattlePhase.ENEMY_TURN;
            battleLog = enemy.getNume() + " incepe primul datorita agilitatii sale (" + enemy.getAgi() + ")!";
        }
    }

    private void InitButtons() {
        mainButtons = new ArrayList<>();
        mainButtons.add(new UIImageButton(30, 420, 140, 35, null, () -> { executePlayerBasicAttack(); }));
        mainButtons.add(new UIImageButton(30, 460, 140, 35, null, () -> {
            isPlayerDefending = true;
            battleLog = player.getNume() + " se apara! Toughness-ul ii va reduce daunele primite.";
            endPlayerTurn();
        }));
        mainButtons.add(new UIImageButton(30, 500, 140, 35, null, () -> { currentMenu = BattleMenu.SPELLS; }));

        spellButtons = new ArrayList<>();
        spellButtons.add(new UIImageButton(30, 420, 200, 35, null, () -> { executeConsecutiveStrikes(); }));
        spellButtons.add(new UIImageButton(30, 460, 200, 35, null, () -> { executeArcaneBlast(); }));
        spellButtons.add(new UIImageButton(30, 500, 200, 35, null, () -> { executeBloodSacrifice(); }));
        spellButtons.add(new UIImageButton(30, 540, 200, 35, null, () -> { currentMenu = BattleMenu.MAIN; }));
    }

    @Override
    public void Update() {
        if (enemy.getHp() <= 0 && currentPhase != BattlePhase.WIN) {
            enemy.setHp(0);
            currentPhase = BattlePhase.WIN;
            if (isBossLvl3) battleLog = "Campionul lui Emilian a fost distrus! Joc Terminat! Click pentru Victorie!";
            else battleLog = (isBossLvl1 || isBossLvl2) ? "Boss infrant! Portalul s-a deschis. Click!" : "Inamic invins! Click pentru a continua.";
        }

        if (player.getHp() <= 0 && currentPhase != BattlePhase.LOSE) {
            player.setHp(0);
            currentPhase = BattlePhase.LOSE;
            battleLog = "Ai fost doborat... Jocul s-a terminat. Click pentru a reincerca.";
        }

        if (currentPhase == BattlePhase.WIN || currentPhase == BattlePhase.LOSE) {
            if (mouseManager.leftPressed) {
                mouseManager.leftPressed = false;
                if (currentPhase == BattlePhase.WIN) {
                    if (isBossLvl1) { ((PlayState) backState).goToLevel2(); State.SetState(backState); }
                    else if (isBossLvl2) { ((PlayState) backState).goToLevel3(); State.SetState(backState); }
                    else if (isBossLvl3) { ((PlayState) backState).triggerWinState(); }
                    else { State.SetState(backState); }
                } else { State.SetState(backState); }
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
        } else if (currentMenu == BattleMenu.SPELLS) {
            for (UIImageButton b : spellButtons) b.Update(mouseManager.mouseX, mouseManager.mouseY, mouseManager.leftPressed);
        }
        if (mouseManager.leftPressed) mouseManager.leftPressed = false;
    }

    private void executePlayerBasicAttack() {
        int damage = player.getAttDmgHit() - (enemy.getTho() / 3);
        if (damage < 1) damage = 1;
        enemy.setHp(enemy.getHp() - damage);
        battleLog = player.getNume() + " ataca fizic si provoaca " + damage + " DMG.";
        endPlayerTurn();
    }

    private void executeConsecutiveStrikes() {
        if (player.getMana() < 15) { battleLog = "Nu ai destula Mana! (Necesita 15 MP)"; return; }
        player.setMana(player.getMana() - 15);
        int activeArms = 0;
        if (player.getLimbsStatus()[Entity.R_ARM]) activeArms++;
        if (player.getLimbsStatus()[Entity.L_ARM]) activeArms++;
        int hits = activeArms + 1;
        int totalDamage = 0;
        for (int i = 0; i < hits; i++) {
            int netDmg = ((player.getStr() / 2) + 2) - (enemy.getTho() / 5);
            totalDamage += Math.max(1, netDmg);
        }
        enemy.setHp(enemy.getHp() - totalDamage);
        battleLog = player.getNume() + " foloseste Lovituri Consecutive! DMG: " + totalDamage;
        currentMenu = BattleMenu.MAIN;
        endPlayerTurn();
    }

    private void executeArcaneBlast() {
        if (player.getMana() < 30) { battleLog = "Nu ai destula Mana! (Necesita 30 MP)"; return; }
        player.setMana(player.getMana() - 30);
        int damage = player.getSpellDmg() + (player.getInte() * 2) - (enemy.getInte() / 4);
        damage = Math.max(5, damage);
        enemy.setHp(enemy.getHp() - damage);
        battleLog = player.getNume() + " Explozie Arcana: " + damage + " Magic DMG!";
        currentMenu = BattleMenu.MAIN;
        endPlayerTurn();
    }

    private void executeBloodSacrifice() {
        if (player.getHp() <= 20) { battleLog = "HP prea scazut pentru sacrificiu!"; return; }
        player.setHp(player.getHp() - 20);
        player.setMana(player.getMana() + 40);
        int maxMana = 150 + 15 * player.getInte();
        if (player.getMana() > maxMana) player.setMana(maxMana);
        battleLog = "Ai jertfit 20 HP pentru 40 MP!";
        currentMenu = BattleMenu.MAIN;
    }

    private void endPlayerTurn() {
        currentPhase = ((PlayState) backState).getCurrentLevelNo() == 2 ? BattlePhase.COMPANION_TURN : BattlePhase.ENEMY_TURN;
    }

    private void handleCompanionAI() {
        turnTimer++;
        if (turnTimer > 40) {
            int compDmg = Math.max(1, (25 + player.getLvl() * 2) - (enemy.getTho() / 4));
            enemy.setHp(enemy.getHp() - compDmg);
            battleLog = "Companionul a provocat " + compDmg + " DMG!";
            turnTimer = 0;
            currentPhase = BattlePhase.ENEMY_TURN;
        }
    }

    private void handleEnemyAI() {
        turnTimer++;
        if (turnTimer > 50) {
            int hpInainte = player.getHp();
            enemy.performTurn(player);
            int dmgPrimit = hpInainte - player.getHp();
            battleLog = (dmgPrimit > 0) ? enemy.getNume() + " te-a lovit: " + dmgPrimit + " HP." : enemy.getNume() + " a efectuat o acțiune.";
            turnTimer = 0;
            currentPhase = BattlePhase.PLAYER_TURN;
        }
    }

    @Override
    public void Draw(Graphics g) {
        g.setColor(Color.BLACK); g.fillRect(0, 0, 800, 600);
        BufferedImage img = (isBossLvl3) ? (Assets.lvl3bosss != null ? Assets.lvl3bosss : Assets.boss) :
                (isBossLvl2) ? Assets.boss_lvl2 :
                (isBossLvl1) ? Assets.boss :
                (enemy instanceof Silvani) ? Assets.minion_full : Assets.minion_lvl2;
        if (img == null) img = Assets.minion_full;
        g.drawImage(img, 280, 50, 240, 320, null);
        g.setColor(new Color(20, 20, 35)); g.fillRect(10, 400, 780, 190);
        g.setColor(Color.CYAN); g.drawRect(10, 400, 780, 190);
        g.setFont(new Font("Arial", Font.PLAIN, 14)); g.setColor(Color.WHITE);
        g.drawString(battleLog, 240, 435);
        g.setColor(Color.GREEN); g.drawString("Jucator: " + player.getHp() + " HP | " + player.getMana() + " MP", 550, 540);
        g.setColor(Color.RED); g.drawString(enemy.getNume() + ": " + enemy.getHp() + " HP | " + enemy.getMana() + " MP", 550, 565);

        if (currentPhase == BattlePhase.PLAYER_TURN) {
            g.setFont(new Font("Arial", Font.BOLD, 15));
            if (currentMenu == BattleMenu.MAIN) {
                String[] labels = {"[1] Atac Fizic", "[2] Pozitie Defensiva", "[3] Cartea de Magii"};
                for (int i = 0; i < mainButtons.size(); i++) {
                    g.setColor(mainButtons.get(i).isHovering() ? Color.RED : Color.WHITE);
                    g.drawString(labels[i], 40, 445 + (i * 40));
                }
            } else if (currentMenu == BattleMenu.SPELLS) {
                String[] spellLabels = {"- Lovituri Consecutive (15 MP)", "- Explozie Arcana (30 MP)", "- Sacrificiu de Sange (-20HP)", "<-- Inapoi"};
                for (int i = 0; i < spellButtons.size(); i++) {
                    g.setColor(spellButtons.get(i).isHovering() ? Color.YELLOW : Color.CYAN);
                    g.drawString(spellLabels[i], 40, 445 + (i * 40));
                }
            }
        } else {
            g.setColor(Color.LIGHT_GRAY);
            g.drawString("Inamicul isi pregateste mutarea...", 40, 445);
        }
        if (hud != null) hud.draw(g);
    }
}