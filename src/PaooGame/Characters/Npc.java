package PaooGame.Characters;

import PaooGame.Physics.CollisionChecker;
import java.awt.*;

public class Npc extends Entity {
    private boolean friend, fallow, roam, interact;
    private String job;

    public Npc(String nume, int str, int inte, int agi, int tho, int limbs_n, int limbs_at, int lvl, int x, int y, int speed,
               boolean friend, boolean fallow, boolean roam, String job, boolean interact) {
        super(nume, str, inte, agi, tho, limbs_n, limbs_at, lvl, x, y, speed);
        this.friend = friend;
        this.fallow = fallow;
        this.roam = roam;
        this.job = job;
        this.interact = interact;
    }

    @Override public void update(CollisionChecker checker) {}
    @Override public void draw(Graphics g, int cameraX, int cameraY) {}
    @Override

    public void performTurn(MC player) {

        Rectangle playerBounds = new Rectangle(
                (int)player.getX() + player.getSolidArea().x,
                (int)player.getY() + player.getSolidArea().y,
                player.getSolidArea().width,
                player.getSolidArea().height
        );


        if(this.getSolidArea().intersects(playerBounds)) {

        }
    }
}