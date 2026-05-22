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

        PaooGame.State.State currentState = PaooGame.State.State.GetState();

        if (currentState instanceof PaooGame.State.PlayState) {
            PaooGame.State.PlayState playState = (PaooGame.State.PlayState) currentState;
            Entity player = playState.getPlayer();

            if (player != null) {
                int diffX = player.getX() - this.getX();
                int diffY = player.getY() - this.getY();
                double distanta = Math.sqrt(diffX * diffX + diffY * diffY);

                int tileuriDetectie = Math.max(2, this.agresivitate - 2);
                int razaDetectiePixeli = tileuriDetectie * 32;

                if (distanta <= razaDetectiePixeli) {
                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        this.setDirectie(diffX > 0 ? "right" : "left");
                    } else {
                        this.setDirectie(diffY > 0 ? "down" : "up");
                    }
                } else {
                    actionLockCounter++;
                    if (actionLockCounter >= 120) {
                        int i = random.nextInt(5);
                        switch (i) {
                            case 0: this.setDirectie("up"); break;
                            case 1: this.setDirectie("down"); break;
                            case 2: this.setDirectie("left"); break;
                            case 3: this.setDirectie("right"); break;
                            case 4: this.setDirectie("idle"); break;
                        }
                        actionLockCounter = 0;
                    }
                }
            }
        }

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

    @Override
    public void draw(Graphics g, int cameraX, int cameraY) {
        if (Assets.walkDown != null && Assets.walkDown[0] != null) {
            g.drawImage(Assets.walkDown[0],
                    this.getX() - cameraX,
                    this.getY() - cameraY,
                    48, 48, null);
        }
    }


    public int getAgresivitate() {
        return agresivitate;
    }

    public void setAgresivitate(int agresivitate) {
        this.agresivitate = agresivitate;
    }
}