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
        // Logica de update pentru NPC (momentan goala)
    }

    // ##########################################
    // SEMN: REPARAT SEMNATURA PENTRU A CORESPUNDE CU ENTITY
    @Override
    public void draw(Graphics g, int cameraX, int cameraY) {
        // NPC-urile neutre pot ramane invizibile sau poti pune un placeholder aici
    }
    // ##########################################
}