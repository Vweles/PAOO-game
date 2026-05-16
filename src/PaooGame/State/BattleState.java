package PaooGame.State;

import PaooGame.Characters.Entity;
import PaooGame.Characters.MC;
import PaooGame.Characters.Inamic;
import PaooGame.Characters.LVL1.Silvani;
import PaooGame.Characters.LVL1.Boss;
import PaooGame.Characters.LVL2.Bossss;
import PaooGame.Characters.LVL2.mn;
import PaooGame.Characters.LVL3.Bossfinal;
import PaooGame.Characters.LVL3.Minionlvl3;
import PaooGame.KeyImput.InputTaste;
import PaooGame.KeyImput.MouseManager;
import PaooGame.KeyImput.UIImageButton;
import PaooGame.Graphics.Assets;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class BattleState extends State {
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

        InitButtons();
        determineFirstTurn();
    }

    private void determineFirstTurn() {

        if (player.agi >= enemy.agi) {
            currentPhase = BattlePhase.PLAYER_TURN;
            battleLog = player.nume + " are agilitate mai mare (" + player.agi + ") si incepe primul!";
        } else {
            currentPhase = BattlePhase.ENEMY_TURN;
            battleLog = enemy.nume + " incepe primul datorita agilitatii sale (" + enemy.agi + ")!";
        }
    }

    private void InitButtons() {
        mainButtons = new ArrayList<>();
        // Atac Fizic de bază
        mainButtons.add(new UIImageButton(30, 420, 140, 35, null, () -> { executePlayerBasicAttack(); }));
        // Apărare (crește THO runda asta)
        mainButtons.add(new UIImageButton(30, 460, 140, 35, null, () -> {
            isPlayerDefending = true;
            battleLog = player.nume + " se apara! Toughness-ul ii va reduce daunele primite.";
            endPlayerTurn();
        }));
        // Meniu Abilități
        mainButtons.add(new UIImageButton(30, 500, 140, 35, null, () -> { currentMenu = BattleMenu.SPELLS; }));

        spellButtons = new ArrayList<>();
        // Spell 1: Lovituri Consecutive (STR / Arme active)
        spellButtons.add(new UIImageButton(30, 420, 200, 35, null, () -> { executeConsecutiveStrikes(); }));
        // Spell 2: Explozie Arcana (INTE)
        spellButtons.add(new UIImageButton(30, 460, 200, 35, null, () -> { executeArcaneBlast(); }));
        // Spell 3: Sacrificiu de Sânge
        spellButtons.add(new UIImageButton(30, 500, 200, 35, null, () -> { executeBloodSacrifice(); }));
        // Înapoi la Principal
        spellButtons.add(new UIImageButton(30, 540, 200, 35, null, () -> { currentMenu = BattleMenu.MAIN; }));
    }

    @Override
    public void Update() {
        if (enemy.hp <= 0 && currentPhase != BattlePhase.WIN) {
            enemy.hp = 0;
            currentPhase = BattlePhase.WIN;
            if (isBossLvl3) battleLog = "Campionul lui Emilian a fost distrus! Joc Terminat! Click pentru Victorie!";
            else battleLog = (isBossLvl1 || isBossLvl2) ? "Boss infrant! Portalul s-a deschis. Click!" : "Inamic invins! Click pentru a continua.";
        }

        if (player.hp <= 0 && currentPhase != BattlePhase.LOSE) {
            player.hp = 0;
            currentPhase = BattlePhase.LOSE;
            battleLog = "Ai fost doborat... Jocul s-a terminat. Click pentru a reincerca.";
        }

        if (currentPhase == BattlePhase.WIN || currentPhase == BattlePhase.LOSE) {
            if (mouseManager.leftPressed) {
                mouseManager.leftPressed = false;
                if (currentPhase == BattlePhase.WIN) {
                    if (isBossLvl1) ((PlayState) backState).goToLevel2();
                    else if (isBossLvl2) ((PlayState) backState).goToLevel3();
                    else if (isBossLvl3) ((PlayState) backState).triggerWinState();
                    State.SetState(backState);
                } else {
                    State.SetState(backState);
                }
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
        int damage = player.att_dmg_hit - (enemy.tho / 3);
        if (damage < 1) damage = 1;
        enemy.hp -= damage;
        battleLog = player.nume + " ataca fizic si provoaca " + damage + " DMG.";
        endPlayerTurn();
    }

    private void executeConsecutiveStrikes() {
        if (player.mana < 15) { battleLog = "Nu ai destula Mana! (Necesita 15 MP)"; return; }
        player.mana -= 15;
        int activeArms = 0;
        if (player.limbsStatus[Entity.R_ARM]) activeArms++;
        if (player.limbsStatus[Entity.L_ARM]) activeArms++;

        int hits = activeArms + 1;
        int totalDamage = 0;
        for (int i = 0; i < hits; i++) {
            int netDmg = ((player.str / 2) + 2) - (enemy.tho / 5);
            totalDamage += Math.max(1, netDmg);
        }
        enemy.hp -= totalDamage;
        battleLog = player.nume + " foloseste Lovituri Consecutive! Loveste de " + hits + " ori pentru " + totalDamage + " DMG!";
        currentMenu = BattleMenu.MAIN;
        endPlayerTurn();
    }

    private void executeArcaneBlast() {
        if (player.mana < 30) { battleLog = "Nu ai destula Mana! (Necesita 30 MP)"; return; }
        player.mana -= 30;
        int damage = player.spell_dmg + (player.inte * 2) - (enemy.inte / 4);
        damage = Math.max(5, damage);
        enemy.hp -= damage;
        battleLog = player.nume + " trasfereaza energie pura: Explozie Arcana de " + damage + " Magic DMG!";
        currentMenu = BattleMenu.MAIN;
        endPlayerTurn();
    }

    private void executeBloodSacrifice() {
        if (player.hp <= 20) { battleLog = "HP prea scazut pentru sacrificiu!"; return; }
        player.hp -= 20;
        player.mana += 40;
        int maxMana = 150 + 15 * player.inte;
        if (player.mana > maxMana) player.mana = maxMana;
        battleLog = "Ai jertfit 20 HP pentru a genera 40 MP!";
        currentMenu = BattleMenu.MAIN;
    }

    private void endPlayerTurn() {
        currentPhase = ((PlayState) backState).getCurrentLevelNo() == 2 ? BattlePhase.COMPANION_TURN : BattlePhase.ENEMY_TURN;
    }

    private void handleCompanionAI() {
        turnTimer++;
        if (turnTimer > 40) {
            int compDmg = Math.max(1, (25 + player.lvl * 2) - (enemy.tho / 4));
            enemy.hp -= compDmg;
            battleLog = "Companionul tau intervine rapid si provoaca " + compDmg + " DMG!";
            turnTimer = 0;
            currentPhase = BattlePhase.ENEMY_TURN;
        }
    }



    private void handleEnemyAI() {
        turnTimer++;
        if (turnTimer > 50) {
            int hpInainte = player.hp;
            int enemyHpInainte = enemy.hp;
            int enemyManaInainte = enemy.mana;


            int temporaryThoSave = player.tho;
            if (isPlayerDefending) player.tho *= 2;


            if (enemy instanceof Silvani) {
                Silvani s = (Silvani) enemy;
                int r = rand.nextInt(4);
                if (r == 0 && s.mana >= 40) { s.wrathOfTheForest(player); battleLog = s.nume + " dezlantuie WRATH OF THE FOREST!"; }
                else if (r == 1 && s.mana >= 20) { s.rootSnare(player); battleLog = s.nume + " incearca sa te prinda in radacini cu Root Snare!"; }
                else if (r == 2 && s.mana >= 15 && s.hp < 100) { s.forestBlessing(); battleLog = s.nume + " absoarbe natura si foloseste Forest Blessing (Vindecare)!"; }
                else { s.thornBurst(player); battleLog = s.nume + " arunca spini ascutiti prin Thorn Burst!"; }
            }

            else if (enemy instanceof Boss) {
                Boss b = (Boss) enemy;

                b.bossTurn(player);
                battleLog = b.nume + " isi schimba tactica si ataca imprevizibil!";
            }

            else if (enemy instanceof mn) {
                mn minion2 = (mn) enemy;
                if (rand.nextBoolean()) {
                    minion2.stealGlory(player);
                    battleLog = minion2.nume + " a folosit Steal Glory si ti-a absorbit puterea!";
                } else {
                    minion2.castSpell(player);
                    battleLog = minion2.nume + " tranzactioneaza daune prin Cast Spell!";
                }
                minion2.humbleReset();
            }

            else if (enemy instanceof Bossss) {
                Bossss b2 = (Bossss) enemy;
                int r = rand.nextInt(4);
                if (r == 0) { b2.infernoBlast(player); battleLog = b2.nume + " arunca o minge de foc devastatoare cu Inferno Blast!"; }
                else if (r == 1) { b2.activateMagmaAura(); battleLog = b2.nume + " a activat Magma Aura! Viteza lui creste."; }
                else if (r == 2 && b2.hp < 2000) { b2.soulSacrifice(); battleLog = b2.nume + " ofera Soul Sacrifice! Se vindeca si aduna furie."; }
                else { b2.specialAttack(player); battleLog = b2.nume + " executa atacul sau SPECIAL catastrofal!"; }
            }

            else if (enemy instanceof Minionlvl3) {
                Minionlvl3 m3 = (Minionlvl3) enemy;
                int r = rand.nextInt(2);
                if (r == 0) { m3.distragereMincinoasa(player); battleLog = m3.nume + " creeaza o Distragere Mincinoasa!"; }
                else { m3.castSpell(player); battleLog = m3.nume + " canalizeaza un blestem prin Cast Spell!"; }
                m3.lingusireZeiasca();
            }

            else if (enemy instanceof Bossfinal) {
                Bossfinal bf = (Bossfinal) enemy;
                int r = rand.nextInt(4);
                if (r == 0) { bf.furaMeritele(player); battleLog = bf.nume + " a folosit Fura Meritele! Daunele fizice cresc."; }
                else if (r == 1) { bf.binecuvantareaLuiEmilian(); battleLog = bf.nume + " primeste Binecuvantarea lui Emilian si intra in Camuflaj!"; }
                else if (r == 2) { bf.specialAttack(player); battleLog = bf.nume + " loveste decisiv cu atacul SPECIAL!"; }
                else { bf.platestePentruVictorie(); battleLog = bf.nume + " Plateste pentru Victorie! Se vindeca si devine furios."; }
            }

            else {
                if (enemy instanceof Inamic) {
                    Inamic generalEnemy = (Inamic) enemy;
                    if (rand.nextBoolean()) {
                        int limb = rand.nextInt(5);
                        generalEnemy.attack(player, limb);
                        battleLog = enemy.nume + " incearca o lovitura mutilanta la un membru!";
                    } else {
                        generalEnemy.attack(player);
                        battleLog = enemy.nume + " te loveste cu un atac de baza.";
                    }
                }
            }


            if (isPlayerDefending) player.tho = temporaryThoSave;
            isPlayerDefending = false;


            int dmgPrimit = hpInainte - player.hp;
            if (dmgPrimit > 0) {
                battleLog += " (Ai pierdut " + dmgPrimit + " HP)";
            }

            turnTimer = 0;
            currentPhase = BattlePhase.PLAYER_TURN;
        }
    }

    @Override
    public void Draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 800, 600);

        BufferedImage img;
        if (isBossLvl3) img = (Assets.lvl3bosss != null) ? Assets.lvl3bosss : Assets.boss;
        else if (isBossLvl2) img = Assets.boss_lvl2;
        else if (isBossLvl1) img = Assets.boss;
        else if (enemy instanceof Silvani) img = Assets.minion_full;
        else img = Assets.minion_lvl2;

        if (img == null) img = Assets.minion_full;
        g.drawImage(img, 280, 50, 240, 320, null);


        g.setColor(new Color(20, 20, 35));
        g.fillRect(10, 400, 780, 190);
        g.setColor(Color.CYAN);
        g.drawRect(10, 400, 780, 190);


        g.setFont(new Font("Arial", Font.PLAIN, 14));
        g.setColor(Color.WHITE);
        g.drawString(battleLog, 240, 435);


        g.setColor(Color.GREEN);
        g.drawString("Jucator: " + player.hp + " HP | " + player.mana + " MP", 550, 540);
        g.setColor(Color.RED);
        g.drawString(enemy.nume + ": " + enemy.hp + " HP | " + enemy.mana + " MP", 550, 565);


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
    }
}