package PaooGame.Characters;

import PaooGame.Physics.CollisionChecker;
import java.awt.*;

public abstract class Entity {
    public String nume;
    public int str, inte, agi, tho;
    public int hp, mana;
    public double att_spd;
    public int att_dmg_hit, spell_dmg;
    public int attacks, limbs_at, lvl, Limbs_normal;
    public int x, y;
    public int speed;

   // vezi ca am bulit aici da cred ca sa reparat
    // urasc coliziunile astea nesimtite
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48); // Zona de coliziune (ajustabilă)
    public boolean collisionOn = false;
    public String directie = "down";


    public static final int R_ARM = 0, L_ARM = 1, R_LEG = 2, L_LEG = 3, HEAD = 4;
    public boolean[] limbsStatus = {true, true, true, true, true};

    public Entity(String NUME, int STR, int INTE, int AGI, int THO, int LIMBS_n, int LIMBS_at, int LVL, int x, int y, int speed) {
        this.nume = NUME;
        this.str = STR;
        this.inte = INTE;
        this.agi = AGI;
        this.tho = THO;
        this.Limbs_normal = LIMBS_n;
        this.lvl = LVL;
        this.limbs_at = LIMBS_at;
        this.x = x;
        this.y = y;
        this.speed = speed;
        updateStatsAfterInjury();
    }

    public void loseLimb(int limbIndex) {
        if (limbIndex >= 0 && limbIndex < limbsStatus.length) {
            limbsStatus[limbIndex] = false;
            updateStatsAfterInjury();
        }
    }

    public void updateStatsAfterInjury() {
        this.hp = 100 + 10 * this.tho + 5 * this.str;
        this.mana = 150 + 15 * this.inte;
        if (!limbsStatus[R_LEG] || !limbsStatus[L_LEG]) this.speed = Math.max(1, (1 + agi) / 5);
        if (!limbsStatus[R_LEG] && !limbsStatus[L_LEG]) this.speed = 1;
        if (!limbsStatus[HEAD]) this.hp = 0;
        int activeArms = 0;
        if(limbsStatus[R_ARM]) activeArms++;
        if(limbsStatus[L_ARM]) activeArms++;
        this.att_dmg_hit = this.str + activeArms;
        this.spell_dmg = this.inte * 5;
        this.att_spd = this.agi * 5 + this.Limbs_normal;
    }

    public abstract void update(CollisionChecker checker);
    public abstract void draw(Graphics g);
}