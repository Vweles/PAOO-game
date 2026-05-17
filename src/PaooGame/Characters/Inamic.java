package PaooGame.Characters;

import PaooGame.Physics.CollisionChecker;
import PaooGame.Graphics.Assets;
import PaooGame.Tiles.Tile;
import java.awt.*;
import java.util.Random;

public class Inamic extends Npc {
    public int agresivitate;


    private int actionLockCounter = 0;
    private Random random = new Random();

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

        if (PaooGame.State.State.GetState() instanceof PaooGame.State.PlayState) {
            PaooGame.State.PlayState playState = (PaooGame.State.PlayState) PaooGame.State.State.GetState();
            Entity player = playState.getPlayer();

            if (player != null) {

                int diffX = player.x - this.x;
                int diffY = player.y - this.y;
                double distanta = Math.sqrt(diffX * diffX + diffY * diffY);


                int tileuriDetectie = Math.max(2, agresivitate - 2);
                int razaDetectiePixeli = tileuriDetectie * 32;

                if (distanta <= razaDetectiePixeli) {

                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        if (diffX > 0) directie = "right";
                        else directie = "left";
                    } else {
                        if (diffY > 0) directie = "down";
                        else directie = "up";
                    }
                } else {

                    actionLockCounter++;


                    if (actionLockCounter >= 120) {
                        int i = random.nextInt(5); // 0, 1, 2, 3 sau 4
                        if (i == 0) directie = "up";
                        if (i == 1) directie = "down";
                        if (i == 2) directie = "left";
                        if (i == 3) directie = "right";
                        if (i == 4) directie = "idle"; // Stă pe loc

                        actionLockCounter = 0;
                    }
                }
            }
        }


        collisionOn = false;
        checker.checkTile(this);


        if (!collisionOn) {
            switch (directie) {
                case "up":    this.y -= this.speed; break;
                case "down":  this.y += this.speed; break;
                case "left":  this.x -= this.speed; break;
                case "right": this.x += this.speed; break;
            }
        }
    }

    @Override
    public void draw(Graphics g, int cameraX, int cameraY) {
        if (Assets.walkDown != null && Assets.walkDown[0] != null) {
            g.drawImage(Assets.walkDown[0],
                    this.x - cameraX,
                    this.y - cameraY,
                    48, 48, null);
        }
    }
}