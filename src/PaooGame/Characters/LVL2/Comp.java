package PaooGame.Characters.LVL2;

import PaooGame.Characters.Entity;
import PaooGame.Characters.Npc;
import PaooGame.Graphics.Assets;
import PaooGame.Physics.CollisionChecker;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Comp extends Npc {

    private Entity target; // Jucătorul pe care îl urmărește
    private int animIndex = 0;
    private int speedCounter = 0;
    private final int ANIM_SPEED = 12;
    private final int DISTANTA_STOP = 50; // Pixeli la care se oprește față de player

    public Comp(String NUME, int x, int y, Entity player) {
        // NUME, STR, INTE, AGI, THO, Limbs_n, Limbs_at, LVL, x, y, speed, friend, follow, roam, job, interact
        super(NUME, 50, 50, 50, 50, 4, 2, 1, x, y, 3, true, true, false, "Guardian", true);
        this.target = player;
        this.solidArea = new Rectangle(12, 24, 24, 20);
    }

    @Override
    public void update(CollisionChecker checker) {
        boolean isMoving = false;

        // Calculăm distanța folosind direct x și y din Entity (minuscule)
        float diffX = target.x - this.x;
        float diffY = target.y - this.y;
        double distanta = Math.sqrt(diffX * diffX + diffY * diffY);

        if (distanta > DISTANTA_STOP) {
            isMoving = true;

            if (Math.abs(diffX) > Math.abs(diffY)) {
                directie = (diffX > 0) ? "right" : "left";
            } else {
                directie = (diffY > 0) ? "down" : "up";
            }

            collisionOn = false;
            checker.checkTile(this);

            if (!collisionOn) {
                if (directie.equals("up")) y -= speed;
                if (directie.equals("down")) y += speed;
                if (directie.equals("left")) x -= speed;
                if (directie.equals("right")) x += speed;
            }
        }

        if (isMoving) {
            speedCounter++;
            if (speedCounter >= ANIM_SPEED) {
                animIndex++;
                speedCounter = 0;
                if (animIndex >= 3) animIndex = 0;
            }
        } else {
            animIndex = 0;
        }
    }

    @Override
    public void draw(Graphics g, int cameraX, int cameraY) {
        BufferedImage frame = null;

        if (directie.equals("up")) frame = Assets.walkUp[animIndex % 3];
        else if (directie.equals("down")) frame = Assets.walkDown[animIndex % 3];
        else if (directie.equals("left")) frame = Assets.walkLeft[animIndex % 3];
        else if (directie.equals("right")) frame = Assets.walkRight[animIndex % 3];

        if (frame != null) {
            g.drawImage(frame, x - cameraX, y - cameraY, 48, 48, null);
            g.setColor(Color.WHITE);
            g.drawString(this.nume, x - cameraX + 5, y - cameraY - 5);
        }
    }
}