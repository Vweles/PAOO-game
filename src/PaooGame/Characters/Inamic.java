package PaooGame.Characters;

import PaooGame.Physics.CollisionChecker;
import java.awt.*;

public class Inamic extends Npc {
    public int agresivitate;

    public Inamic(String NUME, int STR, int INTE, int AGI, int THO,
                  int LIMBS_n, int LIMBS_at, int LVL, int x, int y, int speed,
                  boolean friend, boolean fallow, boolean roam, String job, boolean interact, int agresivitate) {
        super(NUME, STR, INTE, AGI, THO, LIMBS_n, LIMBS_at, LVL, x, y, speed, friend, fallow, roam, job, interact);
        this.agresivitate = agresivitate;
    }

    public void attack(Entity target) {
        target.hp -= this.att_dmg_hit;
    }

    public void attack(Entity target, int targetedLimb) {
        if (Math.random() > 0.8) target.loseLimb(targetedLimb);
        target.hp -= this.att_dmg_hit;
    }

    @Override
    public void update(CollisionChecker checker) {
        // din nou habarnam cum fac uc agresivitatea asta cum sal urmareasca gen da ma rog
    }
}