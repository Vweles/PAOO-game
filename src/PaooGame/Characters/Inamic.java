package PaooGame.Characters;

import PaooGame.Physics.CollisionChecker;
import PaooGame.Graphics.Assets;
import java.awt.*;
import java.util.Random;

public class Inamic extends Npc {
    private int agresivitate;
    private int actionLockCounter = 0;
    private final Random random = new Random();

    public Inamic(String nume, int str, int inte, int agi, int tho,
                  int limbs_n, int limbs_at, int lvl, int x, int y, int speed,
                  boolean friend, boolean fallow, boolean roam, String job, boolean interact,
                  int agresivitate) {
        super(nume, str, inte, agi, tho, limbs_n, limbs_at, lvl, x, y, speed, friend, fallow, roam, job, interact);
        this.agresivitate = agresivitate;
    }


    public void attack(Entity target) {
        target.setHp(target.getHp() - this.getAttDmgHit());
    }


    public void attack(Entity target, int targetedLimb) {
        if (Math.random() > 0.8) {
            target.loseLimb(targetedLimb);
        }
        target.setHp(target.getHp() - this.getAttDmgHit());
    }


        @Override
        public void update(CollisionChecker checker) {
            System.out.println("DEBUG: Update inamic apelat pentru " + this.getNume()); // <--- VEZI DACĂ APARE ÎN CONSOLĂ
            PaooGame.State.State currentState = PaooGame.State.State.GetState();
        if (currentState instanceof PaooGame.State.PlayState) {
            Entity player = ((PaooGame.State.PlayState) currentState).getPlayer();
            if (player != null) {

                applyMovementLogic(player, checker);
            }
        }
    }
    @Override
    public void draw(Graphics g, int cameraX, int cameraY) {
        if (Assets.walkDown != null && Assets.walkDown[0] != null) {
            g.drawImage(Assets.walkDown[0],
                    this.getX() - cameraX,
                    this.getY() - cameraY,
                    48, 48, null);
        }
    }
    public void processAI(Entity player, CollisionChecker checker) {
        int diffX = player.getX() - this.getX();
        int diffY = player.getY() - this.getY();
        double distanta = Math.sqrt(diffX * diffX + diffY * diffY);


        if (distanta <= (this.agresivitate * 32)) {

            if (Math.abs(diffX) > Math.abs(diffY)) {
                this.setDirectie(diffX > 0 ? "right" : "left");
            } else {
                this.setDirectie(diffY > 0 ? "down" : "up");
            }
        } else {

            actionLockCounter++;
            if (actionLockCounter >= 120) {
                int i = random.nextInt(5);
                String[] directii = {"up", "down", "left", "right", "idle"};
                this.setDirectie(directii[i]);
                actionLockCounter = 0;
            }
        }
    }
    protected void applyMovementLogic(Entity player, CollisionChecker checker) {
        processAI(player, checker);

        this.setCollisionOn(false);
        checker.checkTile(this);

        if (!this.isCollisionOn()) {
            switch (this.getDirectie()) {
                case "up":    this.setY(this.getY() - this.getSpeed()); break;
                case "down":  this.setY(this.getY() + this.getSpeed()); break;
                case "left":  this.setX(this.getX() - this.getSpeed()); break;
                case "right": this.setX(this.getX() + this.getSpeed()); break;
            }
        }
    }


    public int getAgresivitate() {
        return agresivitate;
    }

    public void setAgresivitate(int agresivitate) {
        this.agresivitate = agresivitate;
    }
}