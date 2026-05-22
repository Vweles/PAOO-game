package PaooGame.Characters;

import PaooGame.Physics.CollisionChecker;
import java.awt.*;

public abstract class Entity {
    private String nume;
    private int str, inte, agi, tho;
    private int hp, mana;
    private int maxHp, maxMana;
    private double att_spd;
    private int att_dmg_hit, spell_dmg;
    private int attacks, limbs_at, lvl, limbs_normal;
    private int x, y;
    private int speed;

    private Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    private boolean collisionOn = false;
    private String directie = "down";

    public static final int R_ARM = 0, L_ARM = 1, R_LEG = 2, L_LEG = 3, HEAD = 4;
    private boolean[] limbsStatus = {true, true, true, true, true};

    public Entity(String nume, int str, int inte, int agi, int tho, int limbs_n, int limbs_at, int lvl, int x, int y, int speed) {
        this.nume = nume;
        this.str = str;
        this.inte = inte;
        this.agi = agi;
        this.tho = tho;
        this.limbs_normal = limbs_n;
        this.limbs_at = limbs_at;
        this.lvl = lvl;
        this.x = x;
        this.y = y;
        this.speed = speed;
        calculateBaseStats();
        this.hp = this.maxHp;
        this.mana = this.maxMana;
        updateStatsAfterInjury();
    }

    private void calculateBaseStats() {
        this.maxHp = 100 + 10 * this.tho + 5 * this.str;
        this.maxMana = 150 + 15 * this.inte;
    }

    public void loseLimb(int limbIndex) {
        if (limbIndex >= 0 && limbIndex < limbsStatus.length) {
            limbsStatus[limbIndex] = false;
            updateStatsAfterInjury();
        }
    }

    public void updateStatsAfterInjury() {
        calculateBaseStats();
        if (this.hp > this.maxHp) this.hp = this.maxHp;
        if (this.mana > this.maxMana) this.mana = this.maxMana;

        if (!limbsStatus[R_LEG] || !limbsStatus[L_LEG]) this.speed = Math.max(1, (1 + agi) / 5);
        if (!limbsStatus[R_LEG] && !limbsStatus[L_LEG]) this.speed = 1;
        if (!limbsStatus[HEAD]) this.hp = 0;

        int activeArms = (limbsStatus[R_ARM] ? 1 : 0) + (limbsStatus[L_ARM] ? 1 : 0);
        this.att_dmg_hit = this.str + activeArms;
        this.spell_dmg = this.inte * 5;
        this.att_spd = this.agi * 5 + this.limbs_normal;
    }


    public String getNume() { return nume; }
    public void setNume(String nume) { this.nume = nume; }

    public int getStr() { return str; }
    public void setStr(int str) { this.str = str; }

    public int getInte() { return inte; }
    public void setInte(int inte) { this.inte = inte; }

    public int getAgi() { return agi; }
    public void setAgi(int agi) { this.agi = agi; }

    public int getTho() { return tho; }
    public void setTho(int tho) { this.tho = tho; }


    public int getHp() { return hp; }
    public void setHp(int hp) { this.hp = hp; }

    public int getMana() { return mana; }
    public void setMana(int mana) { this.mana = mana; }

    public int getMaxHp() { return maxHp; }
    public int getMaxMana() { return maxMana; }


    public int getAttDmgHit() { return att_dmg_hit; }
    public void setAttDmgHit(int att_dmg_hit) { this.att_dmg_hit = att_dmg_hit; }

    public int getSpellDmg() { return spell_dmg; }
    public void setSpellDmg(int spell_dmg) { this.spell_dmg = spell_dmg; }

    public int getSpeed() { return speed; }
    public void setSpeed(int speed) { this.speed = speed; }


    public int getX() { return x; }
    public void setX(int x) { this.x = x; }

    public int getY() { return y; }
    public void setY(int y) { this.y = y; }

    public String getDirectie() { return directie; }
    public void setDirectie(String directie) { this.directie = directie; }


    public boolean isCollisionOn() { return collisionOn; }
    public void setCollisionOn(boolean collisionOn) { this.collisionOn = collisionOn; }

    public Rectangle getSolidArea() { return solidArea; }
    public void setSolidArea(Rectangle solidArea) { this.solidArea = solidArea; }
    public abstract void update(CollisionChecker checker);
    public abstract void draw(Graphics g, int cameraX, int cameraY);
    public abstract void performTurn(MC player);
    public boolean[] getLimbsStatus() {
        return this.limbsStatus;
    }
    public int getLvl() {
        return this.lvl;
    }


}