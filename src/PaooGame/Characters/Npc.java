package PaooGame.Characters;

import PaooGame.Physics.CollisionChecker;
import java.awt.*;

public class Npc extends Entity {
    public boolean Friend, Fallow, Roam, interact;
    public String Job;

    public Npc(String NUME, int STR, int INTE, int AGI, int THO,
               int LIMBS_n, int LIMBS_at, int LVL, int x, int y, int speed,
               boolean friend, boolean fallow, boolean roam, String job, boolean interact) {
        super(NUME, STR, INTE, AGI, THO, LIMBS_n, LIMBS_at, LVL, x, y, speed);
        this.Friend = friend;
        this.Fallow = fallow;
        this.Roam = roam;
        this.Job = job;
        this.interact = interact;
    }

    @Override
    public void update(CollisionChecker checker) {
        // nush cum fac aici nush cum sa fac de astea de miscare etc
    }

    @Override
    public void draw(Graphics g) {
        // la final de weekend promit ca termin macar inamicii pt lvl1 promit
    }
}